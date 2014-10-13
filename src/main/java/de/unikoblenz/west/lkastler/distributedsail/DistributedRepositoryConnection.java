package de.unikoblenz.west.lkastler.distributedsail;

import net.hh.request_dispatcher.Callback;

import org.openrdf.model.Namespace;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.query.BooleanQuery;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.Query;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.Update;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.UnknownTransactionStateException;
import org.openrdf.repository.base.RepositoryConnectionBase;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.IntermediateResult;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.InsertionResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.RepositoryInsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.RepositoryRetrievalRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.RepositoryRetrievalResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.RetrievalResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceFactory;

/**
 * Connection for the Repository API with the middleware.
 * 
 * @author lkastler
 */
public class DistributedRepositoryConnection extends RepositoryConnectionBase {

	final Logger log = LoggerFactory.getLogger(DistributedRepositoryConnection.class);
	
	protected final ServiceClient<RepositoryInsertionRequest, InsertionResponse> insertion;
	protected final ServiceClient<RepositoryRetrievalRequest, RetrievalResponse> retrieval;
	
	private IntermediateResult<Statement,RepositoryException> result;
	
	private boolean transactionActive = false;
	
	public DistributedRepositoryConnection(Repository repository, MiddlewareServiceFactory factory) throws RepositoryException {
		super(repository);
		try {
			insertion = factory.createServiceClient(Configurator.CHANNEL_INSERTION, RepositoryInsertionRequest.class, InsertionResponse.class);
			retrieval = factory.createServiceClient(Configurator.CHANNEL_RETRIEVAL, RepositoryRetrievalRequest.class, RetrievalResponse.class);
			
			insertion.start();
			retrieval.start();
		} catch (MiddlewareServiceException e) {
			log.error("could not initialize connection", e);
			throw new RepositoryException(e); 
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.RepositoryConnection#getStatements(org.openrdf.model.Resource, org.openrdf.model.URI, org.openrdf.model.Value, boolean, org.openrdf.model.Resource[])
	 */
	public RepositoryResult<Statement> getStatements(Resource subj, URI pred,
			Value obj, boolean includeInferred, Resource... contexts)
			throws RepositoryException {
		
		log.debug("retrieve: " + subj + " " + pred + " " + obj);
		
		RepositoryRetrievalRequest req = new RepositoryRetrievalRequest(subj, pred, obj);
		
		synchronized(retrieval) {
			result = null;
					
			retrieval.execute(req, new Callback<RetrievalResponse>() {

				@SuppressWarnings("unchecked")
				@Override
				public void onSuccess(RetrievalResponse reply) {
					log.info("SUCCESS!");
					log.info(reply.toString());
					
					if(reply instanceof RepositoryRetrievalResponse) {
						RepositoryRetrievalResponse rrr = (RepositoryRetrievalResponse) reply;
						
						try {					
							result = (IntermediateResult<Statement, RepositoryException>) IntermediateResult.create(rrr.getResult());
						} catch (Exception e) {
							log.error("exception!", e);
						}
					}
				}
			});
			while(result == null) {
				//FIXME NOT GOOD
			}
			
			log.debug("received result: " + result);
			
			return new RepositoryResult<Statement>(result);
			
		}	
	}

	// FIXME does something differed than specification
	public boolean isActive() throws UnknownTransactionStateException,
			RepositoryException {
		return transactionActive;
	}

	// FIXME  does something different than interface specification
	public void begin() throws RepositoryException {
		transactionActive = true;
	}

	//  FIXME does something different than interface specification
	public void commit() throws RepositoryException {
		transactionActive = false;
		// TODO maybe add more here
	}

	//  FIXME does something different than interface specification
	public void rollback() throws RepositoryException {
		transactionActive = false;
		// TODO maybe add more here
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.base.RepositoryConnectionBase#addWithoutCommit(org.openrdf.model.Resource, org.openrdf.model.URI, org.openrdf.model.Value, org.openrdf.model.Resource[])
	 */
	@Override
	protected void addWithoutCommit(Resource subject, URI predicate,
			Value object, Resource... contexts) throws RepositoryException {

		log.debug("add: " + subject + " " + predicate + " " + object);
		
		RepositoryInsertionRequest req = new RepositoryInsertionRequest(subject, predicate, object);
		
		synchronized(insertion) {
			insertion.execute(req, new Callback<InsertionResponse>(){
	
				@Override
				public void onSuccess(InsertionResponse reply) {
					log.info("SUCCESS!");
					log.info(reply.toString());
				}
				
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.base.RepositoryConnectionBase#close()
	 */
	@Override
	public void close() throws RepositoryException {
		synchronized(insertion) {
			insertion.stop();
		}
		super.close();
	}
	
	// -------------------- NOT IMPLEMENTED ------------------------
	
	public Query prepareQuery(QueryLanguage ql, String query, String baseURI)
			throws RepositoryException, MalformedQueryException {
		// TODO implement RepositoryConnection.prepareQuery
		throw new UnsupportedOperationException("implement RepositoryConnection.prepareQuery !");
	}

	public TupleQuery prepareTupleQuery(QueryLanguage ql, String query,
			String baseURI) throws RepositoryException, MalformedQueryException {
		// TODO implement RepositoryConnection.prepareTupleQuery
		throw new UnsupportedOperationException("implement RepositoryConnection.prepareTupleQuery !");
	}

	public GraphQuery prepareGraphQuery(QueryLanguage ql, String query,
			String baseURI) throws RepositoryException, MalformedQueryException {
		// TODO implement RepositoryConnection.prepareGraphQuery
		throw new UnsupportedOperationException("implement RepositoryConnection.prepareGraphQuery !");
	}

	public BooleanQuery prepareBooleanQuery(QueryLanguage ql, String query,
			String baseURI) throws RepositoryException, MalformedQueryException {
		// TODO implement RepositoryConnection.prepareBooleanQuery
		throw new UnsupportedOperationException("implement RepositoryConnection.prepareBooleanQuery !");
	}

	public Update prepareUpdate(QueryLanguage ql, String update, String baseURI)
			throws RepositoryException, MalformedQueryException {
		// TODO implement RepositoryConnection.prepareUpdate
		throw new UnsupportedOperationException("implement RepositoryConnection.prepareUpdate !");
	}

	public RepositoryResult<Resource> getContextIDs()
			throws RepositoryException {
		// TODO implement RepositoryConnection.getContextIDs
		throw new UnsupportedOperationException("implement RepositoryConnection.getContextIDs !");
	}
	
	@Override
	protected void removeWithoutCommit(Resource subject, URI predicate,
			Value object, Resource... contexts) throws RepositoryException {
		// TODO implement RepositoryConnectionBase.removeWithoutCommit
		throw new UnsupportedOperationException("implement RepositoryConnectionBase.removeWithoutCommit !");
	}	

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.RepositoryConnection#getNamespaces()
	 */
	public RepositoryResult<Namespace> getNamespaces()
			throws RepositoryException {
		// TODO implement RepositoryConnection.getNamespaces
		throw new UnsupportedOperationException("implement RepositoryConnection.getNamespaces !");
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.RepositoryConnection#getNamespace(java.lang.String)
	 */
	public String getNamespace(String prefix) throws RepositoryException {
		// TODO implement RepositoryConnection.getNamespace
		throw new UnsupportedOperationException("implement RepositoryConnection.getNamespace !");
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.RepositoryConnection#setNamespace(java.lang.String, java.lang.String)
	 */
	public void setNamespace(String prefix, String name)
			throws RepositoryException {
		// TODO implement RepositoryConnection.setNamespace
		throw new UnsupportedOperationException("implement RepositoryConnection.setNamespace !");
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.RepositoryConnection#removeNamespace(java.lang.String)
	 */
	public void removeNamespace(String prefix) throws RepositoryException {
		// TODO implement RepositoryConnection.removeNamespace
		throw new UnsupportedOperationException("implement RepositoryConnection.removeNamespace !");
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.RepositoryConnection#clearNamespaces()
	 */
	public void clearNamespaces() throws RepositoryException {
		// TODO implement RepositoryConnection.clearNamespaces
		throw new UnsupportedOperationException("implement RepositoryConnection.clearNamespaces !");
	}
	
	public void exportStatements(Resource subj, URI pred, Value obj,
			boolean includeInferred, RDFHandler handler, Resource... contexts)
			throws RepositoryException, RDFHandlerException {
		// TODO implement RepositoryConnection.exportStatements
		throw new UnsupportedOperationException("implement RepositoryConnection.exportStatements !");
	}

	public long size(Resource... contexts) throws RepositoryException {
		// TODO implement RepositoryConnection.size
		throw new UnsupportedOperationException("implement RepositoryConnection.size !");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DistributedRepositoryConnection [result=" + result + "]";
	}
}
