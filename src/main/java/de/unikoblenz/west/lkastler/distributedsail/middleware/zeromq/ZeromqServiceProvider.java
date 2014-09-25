package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.hh.request_dispatcher.ZmqWorker;
import net.hh.request_dispatcher.ZmqWorkerProxy;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;

/**
 * implementation of the MiddlewareServiceProvider for ZeroMQ.
 * @author lkastler
 */
public class ZeromqServiceProvider<R extends Request, S extends Response> implements ServiceProvider<R, S> {

	private static final Logger log = LoggerFactory.getLogger(ZeromqServiceProvider.class);
	
	protected ZmqWorkerProxy proxy;
	
	/**
	 * creates a ZeroMQ ServiceProvider with given input channel and request handler.
	 * @param inputChannel - String representation of the input channel.
	 * @param handler - handler for incoming messages.
	 */
	public ZeromqServiceProvider(final String inputChannel, ServiceHandler<R, S> handler) {
		proxy = new ZmqWorkerProxy(ZeromqFactory.getInstance().getContext(), inputChannel);
		
		proxy.add(new ZmqWorker<R, S>(new ZeromqHandlerWrapper<R, S>(handler)));
		
		log.debug("created");
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider#start()
	 */
	public void start() {
		log.debug("starting");
		synchronized(proxy) {
			proxy.startWorkers();
		}
		
		log.debug("started");
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider#stop()
	 */
	public void stop() {
		log.debug("stopping");
		synchronized(proxy) {
			proxy.shutdown();
		}
		
		log.debug("stopped");
	}
}
