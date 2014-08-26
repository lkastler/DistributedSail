package de.unikoblenz.west.lkastler.distributedsail;

import java.io.File;

import org.openrdf.model.ValueFactory;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.base.RepositoryBase;
import org.openrdf.repository.event.NotifyingRepository;
import org.openrdf.repository.event.RepositoryConnectionListener;
import org.openrdf.repository.event.RepositoryListener;

/**
 * implementation of the OpenRDF Repository API.
 * 
 * @author lkastler
 * 
 */
public class DistributedRepository extends RepositoryBase implements NotifyingRepository  {

	public void setDataDir(File dataDir) {
		// TODO implement Repository.setDataDir
		throw new UnsupportedOperationException("implement Repository.setDataDir !");
	}

	public File getDataDir() {
		// TODO implement Repository.getDataDir
		throw new UnsupportedOperationException("implement Repository.getDataDir !");
	}

	public boolean isWritable() throws RepositoryException {
		// TODO implement Repository.isWritable
		throw new UnsupportedOperationException("implement Repository.isWritable !");
	}

	public RepositoryConnection getConnection() throws RepositoryException {
		// TODO implement Repository.getConnection
		throw new UnsupportedOperationException("implement Repository.getConnection !");
	}

	public ValueFactory getValueFactory() {
		// TODO implement Repository.getValueFactory
		throw new UnsupportedOperationException("implement Repository.getValueFactory !");
	}

	public void addRepositoryListener(RepositoryListener listener) {
		// TODO implement NotifyingRepository.addRepositoryListener
		throw new UnsupportedOperationException("implement NotifyingRepository.addRepositoryListener !");
	}

	public void removeRepositoryListener(RepositoryListener listener) {
		// TODO implement NotifyingRepository.removeRepositoryListener
		throw new UnsupportedOperationException("implement NotifyingRepository.removeRepositoryListener !");
	}

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
		// TODO implement RepositoryBase.initializeInternal
		throw new UnsupportedOperationException("implement RepositoryBase.initializeInternal !");
	}

	@Override
	protected void shutDownInternal() throws RepositoryException {
		// TODO implement RepositoryBase.shutDownInternal
		throw new UnsupportedOperationException("implement RepositoryBase.shutDownInternal !");
	}

}
