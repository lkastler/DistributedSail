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

import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SimpleInsertionRequest;

/**
 * Connection for the Repository API with the middleware.
 * 
 * @author lkastler
 */
public class DistributedRepositoryConnection extends RepositoryConnectionBase {

	final Logger log = LoggerFactory.getLogger(DistributedRepositoryConnection.class);
	
	protected final MiddlewareServiceClient<InsertionRequest, InsertionResponse> insertion;
	//protected final MiddlewareServiceClient<RetrievalRequest, RetrievalResponse> retrieval;
	
	private boolean transactionActive = false;
	
	public DistributedRepositoryConnection(Repository repository, MiddlewareServiceFactory factory) throws RepositoryException {
		super(repository);
		try {
			insertion = factory.getMiddlewareServiceClient(InsertionRequest.class, InsertionResponse.class);
			//retrieval = factory.getMiddlewareServiceClient(RetrievalRequest.class, RetrievalResponse.class);
			
			insertion.start();
		} catch (MiddlewareServiceException e) {
			log.error("could not initialize connection", e);
			throw new RepositoryException(e); 
		}
	}

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

	public RepositoryResult<Statement> getStatements(Resource subj, URI pred,
			Value obj, boolean includeInferred, Resource... contexts)
			throws RepositoryException {
		// TODO implement RepositoryConnection.getStatements
		throw new UnsupportedOperationException("implement RepositoryConnection.getStatements !");
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

	public boolean isActive() throws UnknownTransactionStateException,
			RepositoryException {
		return transactionActive;
	}

	public void begin() throws RepositoryException {
		transactionActive = true;
	}

	public void commit() throws RepositoryException {
		transactionActive = false;
		// TODO maybe add more here
	}

	public void rollback() throws RepositoryException {
		transactionActive = false;
		// TODO maybe add more here
	}

	public RepositoryResult<Namespace> getNamespaces()
			throws RepositoryException {
		// TODO implement RepositoryConnection.getNamespaces
		throw new UnsupportedOperationException("implement RepositoryConnection.getNamespaces !");
	}

	public String getNamespace(String prefix) throws RepositoryException {
		// TODO implement RepositoryConnection.getNamespace
		throw new UnsupportedOperationException("implement RepositoryConnection.getNamespace !");
	}

	public void setNamespace(String prefix, String name)
			throws RepositoryException {
		// TODO implement RepositoryConnection.setNamespace
		throw new UnsupportedOperationException("implement RepositoryConnection.setNamespace !");
	}

	public void removeNamespace(String prefix) throws RepositoryException {
		// TODO implement RepositoryConnection.removeNamespace
		throw new UnsupportedOperationException("implement RepositoryConnection.removeNamespace !");
	}

	public void clearNamespaces() throws RepositoryException {
		// TODO implement RepositoryConnection.clearNamespaces
		throw new UnsupportedOperationException("implement RepositoryConnection.clearNamespaces !");
	}

	@Override
	protected void addWithoutCommit(Resource subject, URI predicate,
			Value object, Resource... contexts) throws RepositoryException {

		log.debug("add: " + subject + " " + predicate + " " + object);
		
		InsertionRequest req = new SimpleInsertionRequest(subject, predicate, object);
		
		insertion.execute(req, new Callback<InsertionResponse>(){

			@Override
			public void onSuccess(InsertionResponse reply) {
				log.info(reply.toString());
			}
			
		});
	}

	@Override
	protected void removeWithoutCommit(Resource subject, URI predicate,
			Value object, Resource... contexts) throws RepositoryException {
		// TODO implement RepositoryConnectionBase.removeWithoutCommit
		throw new UnsupportedOperationException("implement RepositoryConnectionBase.removeWithoutCommit !");
	}

	@Override
	public void close() throws RepositoryException {
		insertion.stop();
		
		super.close();
	}
	
	
}
