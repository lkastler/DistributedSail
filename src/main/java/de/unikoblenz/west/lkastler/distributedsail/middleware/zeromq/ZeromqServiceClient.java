package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import de.unikoblenz.west.lkastler.distributedsail.middleware.Handler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;

/**
 * implementation of the MiddlewareServiceClient for ZeroMQ.
 * 
 * @author lkastler
 */
public class ZeromqServiceClient<X extends Request, Y extends Response> implements MiddlewareServiceClient<X, Y> {
	
	public void start() {
		// TODO implement MiddlewareServiceClient.start
		throw new UnsupportedOperationException("implement MiddlewareServiceClient.start !");
	}

	public void stop() {
		// TODO implement MiddlewareServiceClient.stop
		throw new UnsupportedOperationException("implement MiddlewareServiceClient.stop !");
	}

	public void execute(X request, Handler<X, Y> handler) {
		// TODO implement MiddlewareServiceClient<X,Y>.execute
		throw new UnsupportedOperationException("implement MiddlewareServiceClient<X,Y>.execute !");
	}
}
