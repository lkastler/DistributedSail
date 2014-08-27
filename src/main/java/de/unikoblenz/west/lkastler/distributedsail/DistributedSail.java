package de.unikoblenz.west.lkastler.distributedsail;

import org.openrdf.model.ValueFactory;
import org.openrdf.sail.NotifyingSailConnection;
import org.openrdf.sail.Sail;
import org.openrdf.sail.SailException;
import org.openrdf.sail.helpers.NotifyingSailBase;

/**
 * connects a SAIL storage with the middleware
 * @author lkastler
 */
public class DistributedSail extends NotifyingSailBase {

	protected Sail sail;
	
	public DistributedSail(Sail sail) {
		this.sail = sail;
	}
	
	public ValueFactory getValueFactory() {
		return sail.getValueFactory();
	}

	public boolean isWritable() throws SailException {
		return sail.isWritable();
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.sail.helpers.NotifyingSailBase#getConnectionInternal()
	 */
	@Override
	protected NotifyingSailConnection getConnectionInternal()
			throws SailException {
		return new DistributedSailConnection(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.sail.helpers.SailBase#shutDownInternal()
	 */
	@Override
	protected void shutDownInternal() throws SailException {
		// TODO implement SailBase.shutDownInternal
		throw new UnsupportedOperationException("implement SailBase.shutDownInternal !");
	}

}

