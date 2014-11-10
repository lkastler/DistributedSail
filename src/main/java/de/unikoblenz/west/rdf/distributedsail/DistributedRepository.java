package de.unikoblenz.west.rdf.distributedsail;

import java.io.File;
import java.util.LinkedList;

import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.base.RepositoryBase;
import org.openrdf.repository.event.NotifyingRepository;
import org.openrdf.repository.event.RepositoryConnectionListener;
import org.openrdf.repository.event.RepositoryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.rdf.distributedsail.middleware.services.MiddlewareServiceFactory;

/**
 * implementation of the OpenRDF Repository API.
 * 
 * @author lkastler
 * 
 */
public class DistributedRepository extends RepositoryBase implements NotifyingRepository  {

	protected final Logger log = LoggerFactory.getLogger(DistributedRepository.class);
	
	protected DistributedRepositoryConnection connection = null;
		
	protected final MiddlewareServiceFactory factory;
	
	protected LinkedList<RepositoryListener> listeners = new LinkedList<RepositoryListener>();
	
	public DistributedRepository(final MiddlewareServiceFactory factory) {
		super();
		this.factory = factory;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.Repository#setDataDir(java.io.File)
	 */
	public void setDataDir(File dataDir) {
		// TODO implement Repository.setDataDir
		throw new UnsupportedOperationException("implement Repository.setDataDir !");
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.Repository#getDataDir()
	 */
	public File getDataDir() {
		// TODO implement Repository.getDataDir
		throw new UnsupportedOperationException("implement Repository.getDataDir !");
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.Repository#isWritable()
	 */
	public boolean isWritable() throws RepositoryException {
		// TODO implement Repository.isWritable
		throw new UnsupportedOperationException("implement Repository.isWritable !");
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.Repository#getConnection()
	 */
	public RepositoryConnection getConnection() throws RepositoryException {
		if(connection == null) {
			connection = new DistributedRepositoryConnection(this, factory);
		}
		
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.Repository#getValueFactory()
	 */
	public ValueFactory getValueFactory() {
		
		return ValueFactoryImpl.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.event.NotifyingRepository#addRepositoryListener(org.openrdf.repository.event.RepositoryListener)
	 */
	public void addRepositoryListener(RepositoryListener listener) {
		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.event.NotifyingRepository#removeRepositoryListener(org.openrdf.repository.event.RepositoryListener)
	 */
	public void removeRepositoryListener(RepositoryListener listener) {
		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.openrdf.repository.event.NotifyingRepository#addRepositoryConnectionListener(org.openrdf.repository.event.RepositoryConnectionListener)
	 */
	public void addRepositoryConnectionListener(
			RepositoryConnectionListener listener) {
		// TODO implement NotifyingRepository.addRepositoryConnectionListener
		throw new UnsupportedOperationException("implement NotifyingRepository.addRepositoryConnectionListener !");
	}

	public void removeRepositoryConnectionListener(
			RepositoryConnectionListener listener) {
		// TODO implement NotifyingRepository.removeRepositoryConnectionListener
		throw new UnsupportedOperationException("implement NotifyingRepository.removeRepositoryConnectionListener !");
	}

	@Override
	protected void initializeInternal() throws RepositoryException {
		log.debug("initialized");
	}

	@Override
	protected void shutDownInternal() throws RepositoryException {
		log.debug("shut down");
	}

}
