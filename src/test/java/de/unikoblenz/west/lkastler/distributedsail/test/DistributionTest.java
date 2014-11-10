package de.unikoblenz.west.lkastler.distributedsail.test;

import info.aduna.iteration.Iterations;

import java.util.LinkedList;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.sail.memory.MemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.Configurator;
import de.unikoblenz.west.lkastler.distributedsail.DistributedRepository;
import de.unikoblenz.west.lkastler.distributedsail.DistributedRepositoryConnection;
import de.unikoblenz.west.lkastler.distributedsail.DistributedSailConnector;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.InsertionTransformer;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.RetrievalTransformer;
import de.unikoblenz.west.rdf.distributedsail.middleware.transform.Transformer;
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
	 * test insertion with multiple stores
	 * 
	 * @throws Throwable
	 */
	@Ignore
	@Test
	public void testFullWithMultipleStores() throws Throwable {
		log.info("testing distributed setting");

		// DSCs
		LinkedList<DistributedSailConnector> sails = new LinkedList<DistributedSailConnector>();

		for (int i = 0; i < Configurator.MAX_STORES; i++) {
			DistributedSailConnector dsc = setUpDistributedSailConnector(Integer.toString(i));
			
			sails.add(dsc);

			dsc.start();
		}

		// create InsertionTransformer
		Transformer t = setUpInsertionTransformer();
		t.start();

		// create PR
		Repository repo = setUpDistributedRepository();
		// ... and start it
		repo.initialize();

		// get DRConnection
		DistributedRepositoryConnection con = (DistributedRepositoryConnection) repo
				.getConnection();

		// add data)
		ValueFactory fac = repo.getValueFactory();

		for (int i = 0; i < 1000; i++) {
			URI subject = fac.createURI("http://example.com/", "S_" + Integer.toString(i));
			URI predicate = fac.createURI("http://example.com/", "P_" + Integer.toString(i));
			URI object = fac.createURI("http://example.com/", "O_" + Integer.toString(i));
			
			con.add(subject, predicate, object);
		}

		// shut it down
		for (DistributedSailConnector dsc : sails) {
			
			log.debug("print stored infos");
			
			log.debug(Integer.toString(dsc.getStoredTriples().size()));
			
			
			dsc.stop();
		}

		con.close();
		t.stop();
		repo.shutDown();

		log.info("done");
	}
	
	/**
	 * test insertion and retrieval in multiple stores.
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testRetrievalWithMultipleStores() throws Throwable {
		log.info("testing retrieval setting");

		// DSCs
		LinkedList<DistributedSailConnector> sails = new LinkedList<DistributedSailConnector>();

		for (int i = 0; i < Configurator.MAX_STORES; i++) {
			DistributedSailConnector dsc = setUpDistributedSailConnector(Integer.toString(i));
			
			sails.add(dsc);

			dsc.start();
		}
		
		// create InsertionTransformer
		Transformer t = setUpInsertionTransformer();
		t.start();
		
		// create RetrievalTransformer
		Transformer r = setUpRetrievalTransformer();
		r.start();

		// create PR
		Repository repo = setUpDistributedRepository();
		// ... and start it
		repo.initialize();

		log.info("ADD DATA");
		
		// get DRConnection
		DistributedRepositoryConnection con = (DistributedRepositoryConnection) repo
				.getConnection();
		
		// add data
		ValueFactory fac = repo.getValueFactory();

		
		
		for (int i = 0; i < 10; i++) {
			URI subject = fac.createURI("http://example.com/", "S_" + Integer.toString(i));
			URI predicate = fac.createURI("http://example.com/", "P_" + Integer.toString(i));
			URI object = fac.createURI("http://example.com/", "O_" + Integer.toString(i));
			
			con.add(subject, predicate, object);
		}

		log.info("RETRIEVE");
		
		// retrieve data
		RepositoryResult<Statement> result = con.getStatements(fac.createURI("http://example.com/","S_1"), null, null, false, new Resource[0]);
		
		log.info("RETRIEVED: " + Iterations.asList(result));
			
		result = con.getStatements(null, null, null, false, new Resource[0]);
		log.info("RETRIEVED: " + Iterations.asList(result));
		
		// shut it down
		for (DistributedSailConnector dsc : sails) {
			
			log.debug("print stored infos");
			
			log.debug(Integer.toString(dsc.getStoredTriples().size()));
			
			dsc.stop();
		}

		con.close();
		
		t.stop();
		r.stop();
		
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
	@Ignore
	@Test
	public void testFullCycle() throws Throwable {

		// set up
		DistributedRepository repo = setUpDistributedRepository();
		Transformer t = setUpInsertionTransformer();
		DistributedSailConnector dsc = setUpDistributedSailConnector();

		// starting
		t.start();
		dsc.start();
		repo.initialize();

		DistributedRepositoryConnection con;
		con = (DistributedRepositoryConnection) repo.getConnection();

		ValueFactory fac = repo.getValueFactory();

		URI subject = fac.createURI("http://example.com/", "S");
		URI predicate = fac.createURI("http://example.com/", "P");
		URI object = fac.createURI("http://example.com/", "O");

		log.info("send insertion");

		for (int i = 0; i < 1000; i++) {
			con.add(subject, predicate, object);
		}

		// stopping
		dsc.stop();
		t.stop();
		repo.shutDown();
	}

	// ------------------- SUPPORT -------------------

	// TODO add doc
	private Transformer setUpInsertionTransformer() throws Throwable {
		log.info("set up IT");

		// create IT
		Transformer t;
		t = new InsertionTransformer(ZeromqFactory.getInstance());
		log.info("done");

		return t;
	}

	private Transformer setUpRetrievalTransformer() throws Throwable {
		log.info("set up RT");
		
		Transformer t = new RetrievalTransformer(ZeromqFactory.getInstance());
		
		return t;
	}
	
	// TODO add doc
	private DistributedSailConnector setUpDistributedSailConnector()
			throws Throwable {
		return setUpDistributedSailConnector("");
	}

	// TODO add doc
	private DistributedSailConnector setUpDistributedSailConnector(String i)
			throws Throwable {
		log.info("set up DSC");

		// create DSC
		DistributedSailConnector dsc = new DistributedSailConnector(i,
				new MemoryStore(), ZeromqFactory.getInstance(),
				ZeromqFactory.getInstance());

		log.info("done");

		return dsc;
	}

	/**
	 * creates a DistributedRepository
	 * @return a DistributedRepository
	 * @throws Throwable - thrown if something went wrong.
	 */
	private DistributedRepository setUpDistributedRepository() throws Throwable {
		log.info("set up DR");

		DistributedRepository dr = new DistributedRepository(
				ZeromqFactory.getInstance());

		log.info("done");

		return dr;
	}
}
