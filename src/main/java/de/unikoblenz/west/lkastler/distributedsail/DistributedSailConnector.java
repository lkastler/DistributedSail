package de.unikoblenz.west.lkastler.distributedsail;

import org.openrdf.model.Resource;
import org.openrdf.sail.Sail;
import org.openrdf.sail.SailConnection;
import org.openrdf.sail.SailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.DefaultSailResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailInsertionRequestBase;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.MiddlewareNotificationFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;

/**
 * connects a SAIL storage with the middleware
 * 
 * @author lkastler
 */
public class DistributedSailConnector implements
		ServiceHandler<SailInsertionRequestBase, DefaultSailResponse> {

	private static final Logger log = LoggerFactory
			.getLogger(DistributedSailConnector.class);

	protected final Sail sail;

	protected ServiceProvider<SailInsertionRequestBase, DefaultSailResponse> provider;

	// TODO implement notification system.

	/**
	 * creates a DistributedSailConnector that connects given Sail
	 * implementation to the middleware by given MiddlewareServiceProvider.
	 * 
	 * @param sail
	 *            - implementation of the SAIL API to connect to the middleware.
	 * @param provider
	 *            - provides the connection to the middleware.
	 * @throws SailException
	 *             - thrown if needed services could not be created
	 */
	public DistributedSailConnector(Sail sail,
			MiddlewareServiceFactory services,
			MiddlewareNotificationFactory notifications) throws SailException {
		this.sail = sail;
		try {
			provider = services.createServiceProvider(
					Configurator.CHANNEL_SAIL, this);
		} catch (MiddlewareServiceException e) {
			throw new SailException(e);
		}
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
		log.debug("starting");

		sail.initialize();

		provider.start();

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
		provider.stop();
		sail.shutDown();
	}

	public DefaultSailResponse handleRequest(SailInsertionRequestBase request)
			throws Throwable {
		log.debug("handle request: " + request);

		SailConnection s = sail.getConnection();

		if (s.isActive()) {
			s.begin();
			s.addStatement(request.getSubject(), request.getPredicate(),
					request.getObject(), new Resource[0]);
			s.commit();
		}
		s.close();
		
		return new DefaultSailResponse();
	}
}
