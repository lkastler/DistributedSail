package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import net.hh.request_dispatcher.Callback;
import net.hh.request_dispatcher.Dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;

/**
 * implementation of the MiddlewareServiceClient for ZeroMQ.
 * 
 * @author lkastler
 * 
 * @param <X> - type of requests for this client.
 * @param <Y> -  type of responses for this client.
 */
public class ZeromqServiceClient<X extends Request, Y extends Response>
		implements MiddlewareServiceClient<X, Y> {

	private static final Logger log = LoggerFactory
			.getLogger(ZeromqServiceClient.class);

	protected Dispatcher dispatcher = new Dispatcher();
	protected String endpoint;
	protected Class<X> requestClass;

	/**
	 * creates a ZeroQM Service Client
	 * 
	 * @param endpoint
	 * @param requestClass
	 */
	public ZeromqServiceClient(String endpoint, Class<X> requestClass) {
		this.endpoint = endpoint;
		this.requestClass = requestClass;

		log.debug("created");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.
	 * MiddlewareServiceClient#start()
	 */
	public void start() {
		dispatcher.registerService(requestClass, endpoint);

		log.debug("started");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.
	 * MiddlewareServiceClient#stop()
	 */
	public void stop() {
		dispatcher.gatherResults();
		dispatcher.shutdown();

		log.debug("stopped");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.
	 * MiddlewareServiceClient
	 * #execute(de.unikoblenz.west.lkastler.distributedsail
	 * .middleware.commands.Request, net.hh.request_dispatcher.Callback)
	 */
	public void execute(X request, Callback<Y> handler) {
		dispatcher.execute(request, handler);
	}
}
