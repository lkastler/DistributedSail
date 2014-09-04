package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import net.hh.request_dispatcher.Callback;
import net.hh.request_dispatcher.Dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;

/**
 * implementation of the MiddlewareServiceClient for ZeroMQ.
 * 
 * @author lkastler
 */
public class ZeromqServiceClient<X extends Request, Y extends Response> implements MiddlewareServiceClient<X, Y> {
	
	private static final Logger log = LoggerFactory.getLogger(ZeromqServiceClient.class);
	
	protected Dispatcher dispatcher = new Dispatcher();
	protected String endpoint;
	protected Class<X> requestClass;
	
	public ZeromqServiceClient(Class<X> requestClass, String endpoint) {
		log.debug("created");
		
		this.endpoint = endpoint;
		this.requestClass = requestClass;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceClient#start()
	 */
	public void start() {
		log.debug("start");
		dispatcher.registerService(requestClass, endpoint);
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceClient#stop()
	 */
	public void stop() {
		log.debug("stop");
		dispatcher.gatherResults();
		dispatcher.shutdown();
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceClient#execute(de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request, net.hh.request_dispatcher.Callback)
	 */
	public void execute(X request, Callback<Y> handler) {
		dispatcher.execute(request, handler);
	}
}
