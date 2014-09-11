package de.unikoblenz.west.lkastler.distributedsail.test;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
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
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.DefaultResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailInsertionRequestBase;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SimpleInsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handlers.LoggingServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handlers.LoggingNotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handlers.SailLoggingHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationReceiver;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;
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
	@Test
	public void testDRandDSC() throws Throwable {
		log.info("testing DP and DSC");

		DistributedSailConnector sailConnect = setUpDistributedSailConnector(SimpleInsertionRequest.class);
		// ... and start it
		sailConnect.start();

		// create PR
		Repository repo = setUpDistributedRepository();
		// ... and start it
		repo.initialize();

		// get DRConnection
		DistributedRepositoryConnection con = (DistributedRepositoryConnection) repo
				.getConnection();

		// add data
		ValueFactory fac = repo.getValueFactory();

		URI subject = fac.createURI("http://example.com/", "S");
		URI predicate = fac.createURI("http://example.com/", "P");
		URI object = fac.createURI("http://example.com/", "O");

		con.add(subject, predicate, object);

		// shut it down
		sailConnect.stop();
		con.close();
		repo.shutDown();

		log.info("done");
	}

	/**
	 * testing Transformer connection.
	 */
	@Test
	public void testTransformer() throws Throwable {
		log.info("testing transformation");

		Transformer<InsertionRequest, InsertionResponse> t = setUpInsertionTransformer();
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
		Transformer<InsertionRequest, InsertionResponse> t = setUpInsertionTransformer();
		DistributedSailConnector dsc = setUpDistributedSailConnector(SailInsertionRequestBase.class);

		// starting
		t.start();
		dsc.start();
		repo.initialize();

		/*
		 * // DRConnection DistributedRepositoryConnection con; con =
		 * (DistributedRepositoryConnection) repo.getConnection();
		 * 
		 * // add data ValueFactory fac = repo.getValueFactory();
		 * 
		 * URI subject = fac.createURI("http://example.com/", "S"); URI
		 * predicate = fac.createURI("http://example.com/", "P"); URI object =
		 * fac.createURI("http://example.com/", "O");
		 * 
		 * con.add(subject, predicate, object);
		 */

		// stopping
		dsc.stop();
		t.stop();
		repo.shutDown();
	}

	// ------------------- SUPPORT -------------------

	// TODO add doc
	private Transformer<InsertionRequest, InsertionResponse> setUpInsertionTransformer()
			throws Throwable {
		log.info("set up IT");

		// creating insertion handler
		ServiceHandler<InsertionRequest, InsertionResponse> handler;
		handler = new LoggingServiceHandler<InsertionRequest, InsertionResponse>(
				new DefaultResponse());

		// create MSP
		ServiceProvider<InsertionRequest, InsertionResponse> msp;
		msp = ZeromqFactory.getInstance().createServiceProvider(handler);

		// creating notification handler
		NotificationHandler<Notification> nHandler;
		nHandler = new LoggingNotificationHandler<Notification>();

		// create NR
		NotificationReceiver<Notification, NotificationHandler<Notification>> nr;
		nr = ZeromqFactory.getInstance().createNotificationReceiver(nHandler);

		// create IT
		Transformer<InsertionRequest, InsertionResponse> t;
		t = new InsertionTransformer(msp, nr) {

			@Override
			protected void startInternally() {
				log.debug("start internally");
			}

			@Override
			protected void stopInternally() {
				log.debug("stop internally");
			}

		};

		log.info("done");

		return t;
	}

	// TODO add doc
	private <T extends SailRequest> DistributedSailConnector setUpDistributedSailConnector(
			Class<T> clazz) throws Throwable {
		log.info("set up DSC");

		// creating insertion handler
		ServiceHandler<T, DefaultResponse> handler;
		handler = new SailLoggingHandler<T, DefaultResponse>(
				new DefaultResponse());

		// creating MSP
		ServiceProvider<T, DefaultResponse> provider;
		provider = (ServiceProvider<T, DefaultResponse>) ZeromqFactory
				.getInstance().createServiceProvider(handler);

		// create DSC
		DistributedSailConnector dsc = new DistributedSailConnector(
				new MemoryStore(), provider);

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
