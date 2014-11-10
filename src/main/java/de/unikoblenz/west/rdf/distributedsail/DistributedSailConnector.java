package de.unikoblenz.west.rdf.distributedsail;

import info.aduna.iteration.CloseableIteration;

import java.util.LinkedList;
import java.util.List;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.sail.Sail;
import org.openrdf.sail.SailConnection;
import org.openrdf.sail.SailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.rdf.distributedsail.middleware.commands.sail.SailInsertionRequest;
import de.unikoblenz.west.rdf.distributedsail.middleware.commands.sail.SailInsertionResponse;
import de.unikoblenz.west.rdf.distributedsail.middleware.commands.sail.SailRetrievalRequest;
import de.unikoblenz.west.rdf.distributedsail.middleware.commands.sail.SailRetrievalResponse;
import de.unikoblenz.west.rdf.distributedsail.middleware.handlers.SailInsertionHandler;
import de.unikoblenz.west.rdf.distributedsail.middleware.handlers.SailRetrievalHandler;
import de.unikoblenz.west.rdf.distributedsail.middleware.notifications.MiddlewareNotificationFactory;
import de.unikoblenz.west.rdf.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.rdf.distributedsail.middleware.services.MiddlewareServiceFactory;
import de.unikoblenz.west.rdf.distributedsail.middleware.services.ServiceProvider;

/**
 * connects a SAIL storage with the middleware
 * 
 * @author lkastler
 */
public class DistributedSailConnector {

	private static final Logger log = LoggerFactory
			.getLogger(DistributedSailConnector.class);

	protected final Sail sail;
	protected final MiddlewareServiceFactory services;
	
	protected SailConnection sailConnect;
	protected String id;
	
	protected ServiceProvider<SailInsertionRequest, SailInsertionResponse> insertion;
	protected ServiceProvider<SailRetrievalRequest, SailRetrievalResponse> retrieval;
	
	// TODO implement notification system.

	public DistributedSailConnector(String id, Sail sail,
			MiddlewareServiceFactory services,
			MiddlewareNotificationFactory notifications) {
		
		this.id = id;
		this.sail = sail;
		this.services = services;

		log.debug("created");
	}

	/**
	 * starts this DistributedSailConnector, initializing the SAIL
	 * implementation and connecting it to the middleware.
	 * 
	 * @throws SailException
	 *             - thrown if SAIL implementation could not be started.
	 */
	public void start() throws SailException {
		// FIXME avoid multiple starts
		
		log.debug("starting");

		sail.initialize();

		sailConnect = sail.getConnection();
		
		try {
			insertion = services.createServiceProvider(Configurator.CHANNEL_SAIL_INSERTION
					+ id, new SailInsertionHandler(sailConnect));
			
			retrieval = services.createServiceProvider(Configurator.CHANNEL_SAIL_RETRIEVAL
					+ id, new SailRetrievalHandler(id, sailConnect));
		} catch (MiddlewareServiceException e) {
			throw new SailException(e);
		}

		insertion.start();
		retrieval.start();

		log.debug("started");
	}

	/**
	 * stops this DistributedSailConnection and disconnects it from the
	 * middleware.
	 * 
	 * @throws SailException
	 *             - thrown if SAIL implementation could not be shut down.
	 */
	public void stop() throws SailException {
		log.debug("stopping");

		insertion.stop();
		retrieval.stop();
		sailConnect.close();
		sail.shutDown();

		log.debug("stopped");
	}

	public List<String> getStoredTriples() throws SailException {
		LinkedList<String> result = new LinkedList<String>();
		
		log.debug("getting info from store");
		
		CloseableIteration<? extends Statement, SailException> statements = sailConnect.getStatements(null, null, null, false, new Resource[0]);
		
		while(statements.hasNext()) {
			Statement st = statements.next();
			result.add(st.toString());
		}
		
		statements.close();
		
		return result;
	}
}
