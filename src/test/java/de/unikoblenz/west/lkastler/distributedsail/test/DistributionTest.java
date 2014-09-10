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
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SimpleInsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.Handler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.SailLoggingHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.LoggingNotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationReceiver;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.InsertionTransformer;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.Transformer;
import de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq.ZeromqFactory;

/**
 * testing simple distribution of
 * 
 * @author lkastler
 */
public class DistributionTest {

	private static final Logger log = LoggerFactory.getLogger(DistributionTest.class);
	
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
	 * tests connection between DistributedRepository and DistributedSailConnector.
	 * @throws Throwable
	 */
	@Test
	public void testDistribution() throws Throwable {
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
		
		Transformer<InsertionRequest,InsertionResponse> t = setUpInsertionTransformer();
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
	 * @see DistributedRepository
	 * @see DistributedSailConnector
	 * @see InsertionTransformer 
	 * 
	 */
	@Test
	public void testFullCycle() throws Throwable {
		Transformer<InsertionRequest, InsertionResponse> t = setUpInsertionTransformer();
		// starting
		t.start();
		
		// stopping
		t.stop();
	}
	
	// TODO add doc
	private Transformer<InsertionRequest,InsertionResponse> setUpInsertionTransformer() throws Throwable {
		log.info("testing transformation");
		// creating insertion handler
		Handler<InsertionRequest, InsertionResponse> handler = new SailLoggingHandler<InsertionRequest, InsertionResponse>(new DefaultResponse());
		
		// create MSP
		MiddlewareServiceProvider<InsertionRequest, InsertionResponse> msp = ZeromqFactory.getInstance().getMiddlewareServiceProvider(handler);

		// creating notification handler
		NotificationHandler<Notification> nHandler = new LoggingNotificationHandler<Notification>();
		
		// create NR
		NotificationReceiver<Notification, NotificationHandler<Notification>> nr = ZeromqFactory.getInstance().getNotificationReceiver(nHandler);
		
		// create IT
		Transformer<InsertionRequest, InsertionResponse> t = new InsertionTransformer(msp, nr) {

			@Override
			protected void startInternally() {
				log.debug("start internally");
			}

			@Override
			protected void stopInternally() {
				log.debug("stop internally");
			}
			
		};

		return t;
	}
	
	// TODO add doc
	private DistributedRepository setUpDistributedRepository() throws Throwable {
		return new DistributedRepository(ZeromqFactory.getInstance());
	}
	
	// TODO add doc
	private <T extends SailRequest>DistributedSailConnector setUpDistributedSailConnector(Class<T> clazz) throws Throwable {
		// creating insertion handler
		Handler<T, DefaultResponse> handler; 
		handler = new SailLoggingHandler<T, DefaultResponse>(new DefaultResponse());

		// creating MSP
		MiddlewareServiceProvider<T, DefaultResponse> provider;
		provider= (MiddlewareServiceProvider<T, DefaultResponse>) ZeromqFactory
						.getInstance().getMiddlewareServiceProvider(handler);

		// create DSC
		return new DistributedSailConnector(new MemoryStore(), provider);
	}
}
