package de.unikoblenz.west.lkastler.distributedsail;

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

import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.RetrievalRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.RetrievalResponse;

/**
 * Connection for the Repository API with the middleware.
 * 
 * @author lkastler
 */
public class DistributedRepositoryConnection extends RepositoryConnectionBase {

	protected MiddlewareServiceClient<InsertionRequest,InsertionResponse> insertion;
	protected MiddlewareServiceClient<RetrievalRequest,RetrievalResponse> retrieval;
	
	protected DistributedRepositoryConnection(Repository repository) {
		super(repository);
		// TODO implement DistributedRepositoryConnection constructor
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
		// TODO implement RepositoryConnection.isActive
		throw new UnsupportedOperationException("implement RepositoryConnection.isActive !");
	}

	public void begin() throws RepositoryException {
		// TODO implement RepositoryConnection.begin
		throw new UnsupportedOperationException("implement RepositoryConnection.begin !");
	}

	public void commit() throws RepositoryException {
		// TODO implement RepositoryConnection.commit
		throw new UnsupportedOperationException("implement RepositoryConnection.commit !");
	}

	public void rollback() throws RepositoryException {
		// TODO implement RepositoryConnection.rollback
		throw new UnsupportedOperationException("implement RepositoryConnection.rollback !");
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
		// TODO implement RepositoryConnectionBase.addWithoutCommit
		throw new UnsupportedOperationException("implement RepositoryConnectionBase.addWithoutCommit !");
	}

	@Override
	protected void removeWithoutCommit(Resource subject, URI predicate,
			Value object, Resource... contexts) throws RepositoryException {
		// TODO implement RepositoryConnectionBase.removeWithoutCommit
		throw new UnsupportedOperationException("implement RepositoryConnectionBase.removeWithoutCommit !");
	}
}
