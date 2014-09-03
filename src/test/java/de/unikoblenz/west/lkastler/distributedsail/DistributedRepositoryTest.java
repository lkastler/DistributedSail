package de.unikoblenz.west.lkastler.distributedsail;

import org.junit.Test;
import org.openrdf.repository.RepositoryException;

import de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq.ZeromqServiceFactory;

public class DistributedRepositoryTest {

	@Test
	public void connectDistributedRepository() throws RepositoryException {
		DistributedRepository repo = new DistributedRepository(ZeromqServiceFactory.getInstance());
		
		repo.initialize();
	}
}
