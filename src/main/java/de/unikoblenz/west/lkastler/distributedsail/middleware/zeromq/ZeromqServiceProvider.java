package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import net.hh.request_dispatcher.ZmqWorker;
import net.hh.request_dispatcher.ZmqWorkerProxy;
import de.unikoblenz.west.lkastler.distributedsail.middleware.Handler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;

/**
 * implementation of the MiddlewareServiceProvider for ZeroMQ.
 * @author lkastler
 */
public class ZeromqServiceProvider<RequestType extends Request, ResponseType extends Response> implements MiddlewareServiceProvider<RequestType, ResponseType> {

	protected ZmqWorkerProxy proxy;

	public ZeromqServiceProvider(final String inputChannel, Handler<RequestType, ResponseType> handler) {
		
		proxy = new ZmqWorkerProxy(inputChannel);
		
		proxy.add(new ZmqWorker<RequestType, ResponseType>(new ZeromqHandlerWrapper<RequestType, ResponseType>(handler)));
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider#start()
	 */
	public void start() {
		proxy.startWorkers();
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider#stop()
	 */
	public void stop() {
		proxy.shutdown();
	}
	
}
