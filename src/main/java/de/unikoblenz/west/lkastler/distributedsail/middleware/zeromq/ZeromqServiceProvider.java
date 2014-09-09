package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import net.hh.request_dispatcher.ZmqWorker;
import net.hh.request_dispatcher.ZmqWorkerProxy;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.Handler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;

/**
 * implementation of the MiddlewareServiceProvider for ZeroMQ.
 * @author lkastler
 */
public class ZeromqServiceProvider<RequestType extends Request, ResponseType extends Response> implements MiddlewareServiceProvider<RequestType, ResponseType> {

	protected ZmqWorkerProxy proxy;

	/**
	 * creates a ZeroMQ ServiceProvider with given input channel and request handler.
	 * @param inputChannel - String representation of the input channel.
	 * @param handler - handler for incoming messages.
	 */
	public ZeromqServiceProvider(final String inputChannel, Handler<RequestType, ResponseType> handler) {
		proxy = new ZmqWorkerProxy(inputChannel);
		
		proxy.add(new ZmqWorker<RequestType, ResponseType>(new ZeromqHandlerWrapper<RequestType, ResponseType>(handler)));
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider#start()
	 */
	public void start() {
		synchronized(proxy) {
			proxy.startWorkers();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider#stop()
	 */
	public void stop() {
		synchronized(proxy) {
			proxy.shutdown();
		}
	}
	
}
