package de.unikoblenz.west.lkastler.distributedsail;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.repository.Repository;
import org.openrdf.sail.memory.MemoryStore;

import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.DefaultResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SimpleInsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.Handler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.SailLoggingHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq.ZeromqServiceFactory;

/**
 * testing simple distribution of
 * 
 * @author lkastler
 */
public class DistributionTest {

	@Before
	public void setUp() {
		BasicConfigurator.configure();
	}

	@Test
	public void testDistribution() throws Throwable {

		Handler<SimpleInsertionRequest, DefaultResponse> handler = new SailLoggingHandler<SimpleInsertionRequest, DefaultResponse>(
				new DefaultResponse());

		MiddlewareServiceProvider<SimpleInsertionRequest, DefaultResponse> provider = (MiddlewareServiceProvider<SimpleInsertionRequest, DefaultResponse>) ZeromqServiceFactory
				.getInstance().getMiddlewareServiceProvider(handler);

		DistributedSailConnector sailConnect;
		sailConnect = new DistributedSailConnector(new MemoryStore(), provider);
		sailConnect.start();

		Repository repo = new DistributedRepository(
				ZeromqServiceFactory.getInstance());
		repo.initialize();

		DistributedRepositoryConnection con = (DistributedRepositoryConnection) repo
				.getConnection();

		ValueFactory fac = repo.getValueFactory();

		URI subject = fac.createURI("http://example.com/", "S");
		URI predicate = fac.createURI("http://example.com/", "P");
		URI object = fac.createURI("http://example.com/", "O");

		con.add(subject, predicate, object);
		
		sailConnect.stop();
		con.close();
		repo.shutDown();
	}
}