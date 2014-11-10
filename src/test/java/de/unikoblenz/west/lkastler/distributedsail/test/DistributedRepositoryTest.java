package de.unikoblenz.west.lkastler.distributedsail.test;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openrdf.repository.RepositoryException;

import de.unikoblenz.west.rdf.distributedsail.DistributedRepository;
import de.unikoblenz.west.rdf.distributedsail.middleware.zeromq.ZeromqFactory;

public class DistributedRepositoryTest {

	@Before
	public void setUp() {
		BasicConfigurator.configure();
	}
	
	@After
	public void tearDown() {
		BasicConfigurator.resetConfiguration();
	}
	
	@Test
	public void connectDistributedRepository() throws RepositoryException {
		DistributedRepository repo = new DistributedRepository(ZeromqFactory.getInstance());
		
		repo.initialize();
		
		repo.shutDown();
	}
}
