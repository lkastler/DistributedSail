package de.unikoblenz.west.lkastler.distributedsail;

import org.openrdf.model.ValueFactory;
import org.openrdf.sail.NotifyingSailConnection;
import org.openrdf.sail.SailException;
import org.openrdf.sail.helpers.NotifyingSailBase;

/**
 * connects a SAIL storage with the middleware
 * @author lkastler
 */
public class DistributedSail extends NotifyingSailBase {

	public ValueFactory getValueFactory() {
		// TODO implement Sail.getValueFactory
		throw new UnsupportedOperationException("implement Sail.getValueFactory !");
	}

	public boolean isWritable() throws SailException {
		// TODO implement Sail.isWritable
		throw new UnsupportedOperationException("implement Sail.isWritable !");
	}

	@Override
	protected NotifyingSailConnection getConnectionInternal()
			throws SailException {
		// TODO implement NotifyingSailBase.getConnectionInternal
		throw new UnsupportedOperationException("implement NotifyingSailBase.getConnectionInternal !");
	}

	@Override
	protected void shutDownInternal() throws SailException {
		// TODO implement SailBase.shutDownInternal
		throw new UnsupportedOperationException("implement SailBase.shutDownInternal !");
	}

}
