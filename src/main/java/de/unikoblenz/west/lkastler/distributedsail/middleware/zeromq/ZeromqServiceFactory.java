package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import de.unikoblenz.west.lkastler.distributedsail.MiddlewareServiceFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.Handler;

public class ZeromqServiceFactory implements MiddlewareServiceFactory {

	public static final String CHANNEL = "ipc://test";
	
	private static ZeromqServiceFactory instance = null;
	
	/**
	 * returns the instance of ZeromqServiceFactory.
	 * @return the instance of ZeromqServiceFactory.
	 */
	public static ZeromqServiceFactory getInstance() {
		if(instance == null)
			instance = new ZeromqServiceFactory();
		return instance;
	}
	
	public <R extends Request, S extends Response> MiddlewareServiceClient<R, S> getMiddlewareServiceClient(
			Class<R> request, Class<S> response) {
		
		return new ZeromqServiceClient<R,S>();
	}

	public <R extends Request, S extends Response> MiddlewareServiceProvider<R, S> getMiddlewareServiceProvider(
			Class<Handler<R, S>> handler) throws ZeromqServiceException {
		try {
			return new ZeromqServiceProvider<R,S>(CHANNEL, handler.newInstance());
		} catch (InstantiationException e) {
			throw new ZeromqServiceException(e);
		} catch (IllegalAccessException e) {
			throw new ZeromqServiceException(e);
		}
	}


}
