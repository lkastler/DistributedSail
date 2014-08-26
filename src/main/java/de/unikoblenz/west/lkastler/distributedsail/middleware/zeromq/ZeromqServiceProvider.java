package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import net.hh.request_dispatcher.ZmqWorker;
import de.unikoblenz.west.lkastler.distributedsail.middleware.Handler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;

/**
 * implementation of the MiddlewareServiceProvider for ZeroMQ.
 * @author lkastler
 */
public class ZeromqServiceProvider<RequestType extends Request, ResponseType extends Response> implements MiddlewareServiceProvider<RequestType, ResponseType> {

	protected ZmqWorker<RequestType, ResponseType> worker;

	public ZeromqServiceProvider(Handler<RequestType, ResponseType> handler) {
		worker = new ZmqWorker<RequestType, ResponseType>(new ZeromqHandlerWrapper<RequestType, ResponseType>(handler));
	}
	
	public void start() {
		worker.start();
	}

	public void stop() {
		throw new UnsupportedOperationException("implement ZMQServiceProvider.stop");
	}
	
}
