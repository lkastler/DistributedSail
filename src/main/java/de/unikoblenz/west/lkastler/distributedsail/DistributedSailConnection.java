package de.unikoblenz.west.lkastler.distributedsail;

import info.aduna.iteration.CloseableIteration;

import org.openrdf.model.Namespace;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.Dataset;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.algebra.TupleExpr;
import org.openrdf.sail.SailException;
import org.openrdf.sail.helpers.NotifyingSailConnectionBase;
import org.openrdf.sail.helpers.SailBase;

import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider;

/**
 * connection of a SAIL api instance with the middleware.
 * 
 * @author lkastler
 */
public class DistributedSailConnection extends NotifyingSailConnectionBase {

	protected MiddlewareServiceProvider provider;
	
	public DistributedSailConnection(SailBase sailBase) {
		super(sailBase);
		// TODO implement DistributedRepositoryConnection constructor
	}

	@Override
	protected void addStatementInternal(Resource arg0, URI arg1, Value arg2,
			Resource... arg3) throws SailException {
		// TODO implement SailConnectionBase.addStatementInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.addStatementInternal !");
	}

	@Override
	protected void clearInternal(Resource... arg0) throws SailException {
		// TODO implement SailConnectionBase.clearInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.clearInternal !");
	}

	@Override
	protected void clearNamespacesInternal() throws SailException {
		// TODO implement SailConnectionBase.clearNamespacesInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.clearNamespacesInternal !");
	}

	@Override
	protected void closeInternal() throws SailException {
		// TODO implement SailConnectionBase.closeInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.closeInternal !");
	}

	@Override
	protected void commitInternal() throws SailException {
		// TODO implement SailConnectionBase.commitInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.commitInternal !");
	}

	@Override
	protected CloseableIteration<? extends BindingSet, QueryEvaluationException> evaluateInternal(
			TupleExpr arg0, Dataset arg1, BindingSet arg2, boolean arg3)
			throws SailException {
		// TODO implement SailConnectionBase.evaluateInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.evaluateInternal !");
	}

	@Override
	protected CloseableIteration<? extends Resource, SailException> getContextIDsInternal()
			throws SailException {
		// TODO implement SailConnectionBase.getContextIDsInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.getContextIDsInternal !");
	}

	@Override
	protected String getNamespaceInternal(String arg0) throws SailException {
		// TODO implement SailConnectionBase.getNamespaceInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.getNamespaceInternal !");
	}

	@Override
	protected CloseableIteration<? extends Namespace, SailException> getNamespacesInternal()
			throws SailException {
		// TODO implement SailConnectionBase.getNamespacesInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.getNamespacesInternal !");
	}

	@Override
	protected CloseableIteration<? extends Statement, SailException> getStatementsInternal(
			Resource arg0, URI arg1, Value arg2, boolean arg3, Resource... arg4)
			throws SailException {
		// TODO implement SailConnectionBase.getStatementsInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.getStatementsInternal !");
	}

	@Override
	protected void removeNamespaceInternal(String arg0) throws SailException {
		// TODO implement SailConnectionBase.removeNamespaceInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.removeNamespaceInternal !");
	}

	@Override
	protected void removeStatementsInternal(Resource arg0, URI arg1,
			Value arg2, Resource... arg3) throws SailException {
		// TODO implement SailConnectionBase.removeStatementsInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.removeStatementsInternal !");
	}

	@Override
	protected void rollbackInternal() throws SailException {
		// TODO implement SailConnectionBase.rollbackInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.rollbackInternal !");
	}

	@Override
	protected void setNamespaceInternal(String arg0, String arg1)
			throws SailException {
		// TODO implement SailConnectionBase.setNamespaceInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.setNamespaceInternal !");
	}

	@Override
	protected long sizeInternal(Resource... arg0) throws SailException {
		// TODO implement SailConnectionBase.sizeInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.sizeInternal !");
	}

	@Override
	protected void startTransactionInternal() throws SailException {
		// TODO implement SailConnectionBase.startTransactionInternal
		throw new UnsupportedOperationException("implement SailConnectionBase.startTransactionInternal !");
	}
}
