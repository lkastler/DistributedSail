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
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SimpleInsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.Handler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.SailLoggingHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.LoggingNotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationReceiver;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.InsertionTransformator;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.Transformator;
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

		// creating insertion handler
		Handler<SimpleInsertionRequest, DefaultResponse> handler = new SailLoggingHandler<SimpleInsertionRequest, DefaultResponse>(
				new DefaultResponse());

		// creating MSP
		MiddlewareServiceProvider<SimpleInsertionRequest, DefaultResponse> provider = (MiddlewareServiceProvider<SimpleInsertionRequest, DefaultResponse>) ZeromqFactory
				.getInstance().getMiddlewareServiceProvider(handler);

		// create DSC
		DistributedSailConnector sailConnect;
		sailConnect = new DistributedSailConnector(new MemoryStore(), provider);
		// ... and start it
		sailConnect.start();

		// create PR
		Repository repo = new DistributedRepository(
				ZeromqFactory.getInstance());
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
		// creating insertion handler
		Handler<InsertionRequest, InsertionResponse> handler = new SailLoggingHandler<InsertionRequest, InsertionResponse>(new DefaultResponse());
		
		// create MSP
		MiddlewareServiceProvider<InsertionRequest, InsertionResponse> msp = ZeromqFactory.getInstance().getMiddlewareServiceProvider(handler);

		// creating notification handler
		NotificationHandler<Notification> nHandler = new LoggingNotificationHandler<Notification>();
		
		// create NR
		NotificationReceiver<Notification, NotificationHandler<Notification>> nr = ZeromqFactory.getInstance().getNotificationReceiver(nHandler);
		
		// create IT
		Transformator<InsertionRequest, InsertionResponse> t = new InsertionTransformator(msp, nr) {

			@Override
			protected void startInternally() {
				log.debug("start internally");
			}

			@Override
			protected void stopInternally() {
				log.debug("stop internally");
			}
			
		};
		// ... and start it
		t.start();
		
		// ... and stopping it
		t.stop();
		
		log.info("done");
	}
}
