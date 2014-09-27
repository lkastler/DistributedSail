package de.unikoblenz.west.lkastler.distributedsail.test;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.repository.Repository;
import org.openrdf.sail.memory.MemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.DistributedRepository;
import de.unikoblenz.west.lkastler.distributedsail.DistributedRepositoryConnection;
import de.unikoblenz.west.lkastler.distributedsail.DistributedSailConnector;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.InsertionTransformer;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.Transformer;
import de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq.ZeromqFactory;

/**
 * testing simple distribution of
 * 
 * @author lkastler
 */
public class DistributionTest {

	private static final Logger log = LoggerFactory
			.getLogger(DistributionTest.class);

	/**
	 * sets logging up.
	 */
	@Before
	public void setUp() {
		BasicConfigurator.configure();
	}

	/**
	 * tears down logging, otherwise leads to strange output.
	 */
	@After
	public void tearDown() {
		BasicConfigurator.resetConfiguration();
	}

	/**
	 * tests connection between DistributedRepository and
	 * DistributedSailConnector.
	 * 
	 * @throws Throwable
	 */
	@Ignore
	@Test
	public void testDRandDSC() throws Throwable {
		log.info("testing DP and DSC");
		
		Object o = new Object();

		DistributedSailConnector sailConnect = setUpDistributedSailConnector();
		// ... and start it
		sailConnect.start();

		// create PR
		Repository repo = setUpDistributedRepository();
		// ... and start it
		repo.initialize();

		// get DRConnection
		DistributedRepositoryConnection con = (DistributedRepositoryConnection) repo
				.getConnection();

		
		// add datanr)
		ValueFactory fac = repo.getValueFactory();

		URI subject = fac.createURI("http://example.com/", "S");
		URI predicate = fac.createURI("http://example.com/", "P");
		URI object = fac.createURI("http://example.com/", "O");

		con.add(subject, predicate, object);

		synchronized(o) {
			o.wait(10000);
		}
		
		// shut it down
		sailConnect.stop();
		con.close();
		repo.shutDown();

		log.info("done");
	}

	/**
	 * testing Transformer connection.
	 */
	@Ignore
	@Test
	public void testTransformer() throws Throwable {
		log.info("testing transformation");

		Transformer t = setUpInsertionTransformer();
		// ... and start it
		t.start();

		// ... and stopping it
		t.stop();

		log.info("done");
	}

	/**
	 * Testing the whole process with DR, DSC, and IT.
	 * 
	 * @throws Throwable
	 *             - if something went wrong.
	 * 
	 * @see DistributedRepository
	 * @see DistributedSailConnector
	 * @see InsertionTransformer
	 * 
	 */
	@Test
	public void testFullCycle() throws Throwable {
		
		// set up
		DistributedRepository repo = setUpDistributedRepository();
		Transformer t = setUpInsertionTransformer();
		Transformer t2 = setUpInsertionTransformer();
		DistributedSailConnector dsc = setUpDistributedSailConnector();

		// starting
		t.start();
		t2.start();
		dsc.start();
		repo.initialize();
		
		DistributedRepositoryConnection con;
		con = (DistributedRepositoryConnection) repo.getConnection();
		
		ValueFactory fac = repo.getValueFactory();

		URI subject = fac.createURI("http://example.com/", "S");
		URI predicate = fac.createURI("http://example.com/", "P");
		URI object = fac.createURI("http://example.com/", "O");

		log.info("send insertion");
		
		for(int i = 0; i < 1000; i++) {
			con.add(subject, predicate, object);
		}
		
		// stopping
		dsc.stop();
		t.stop();
		t2.stop();
		repo.shutDown();
	}

	// ------------------- SUPPORT -------------------

	// TODO add doc
	private Transformer setUpInsertionTransformer()
			throws Throwable {
		log.info("set up IT");

		// create IT
		Transformer t;
		t = new InsertionTransformer(ZeromqFactory.getInstance());
		log.info("done");

		return t;
	}

	// TODO add doc
	private DistributedSailConnector setUpDistributedSailConnector() throws Throwable {
		log.info("set up DSC");
		
		// create DSC
		DistributedSailConnector dsc = new DistributedSailConnector(
				new MemoryStore(), ZeromqFactory.getInstance(), ZeromqFactory.getInstance());

		log.info("done");

		return dsc;
	}

	// TODO add doc
	private DistributedRepository setUpDistributedRepository() throws Throwable {
		log.info("set up DR");

		DistributedRepository dr = new DistributedRepository(
				ZeromqFactory.getInstance());

		log.info("done");

		return dr;
	}
}
