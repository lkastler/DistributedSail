package de.unikoblenz.west.lkastler.distributedsail.test;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openrdf.sail.memory.MemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.DistributedSailConnector;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq.ZeromqFactory;

/**
 * this test suite is dedicated to test things related to the DistributedSail class.
 * @author lkastler
 */
public class DistributedSailTest {

	private final static Logger log = LoggerFactory.getLogger(DistributedSailTest.class);
	
	public class TestHandler implements ServiceHandler<SailRequest, SailResponse> {

		public SailResponse handleRequest(SailRequest request) throws Throwable {
			// TODO implement Handler<SailRequest,SailResponse>.handleRequest
			throw new UnsupportedOperationException("implement Handler<SailRequest,SailResponse>.handleRequest !");
		}
	}
	
	@Before
	public void setUp() {
		BasicConfigurator.configure();
	}
	
	@After
	public void tearDown() {
		BasicConfigurator.resetConfiguration();
	}
	
	/**
	 * tests if the DistributedSail can connect to the middleware.
	 * @throws Throwable - if something went wrong
	 */
	@Test
	public void connectDistributedSail() throws Throwable {
		log.info("starting connection test");
		DistributedSailConnector dsail = new DistributedSailConnector(new MemoryStore(), ZeromqFactory.getInstance(),ZeromqFactory.getInstance());
		
		dsail.start();
				
		dsail.stop();
		
		log.info("ending connection test");
	}
}
