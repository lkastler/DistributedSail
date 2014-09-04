package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import de.unikoblenz.west.lkastler.distributedsail.MiddlewareServiceFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.Handler;

/**
 * implementation of the MiddlewareServiceFactory interface for ZeroMQ
 * 
 * @author lkastler
 */
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

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.MiddlewareServiceFactory#getMiddlewareServiceClient(java.lang.Class, java.lang.Class)
	 */
	public <R extends Request, S extends Response> MiddlewareServiceClient<R, S> getMiddlewareServiceClient(
			Class<R> request, Class<S> response)
			throws MiddlewareServiceException {
		
		return new ZeromqServiceClient<R, S>(request, CHANNEL);
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.MiddlewareServiceFactory#getMiddlewareServiceProvider(java.lang.Class)
	 */
	public <R extends Request, S extends Response>MiddlewareServiceProvider<R, S> getMiddlewareServiceProvider(Handler<R, S> handler) throws MiddlewareServiceException {
			return new ZeromqServiceProvider<R,S>(CHANNEL, handler);
	}
}
