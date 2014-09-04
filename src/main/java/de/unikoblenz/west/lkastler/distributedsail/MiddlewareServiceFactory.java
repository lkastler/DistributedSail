package de.unikoblenz.west.lkastler.distributedsail;

import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.Handler;

/**
 * creates MiddlewareServices.
 * 
 * @author lkastler
 */
public interface MiddlewareServiceFactory {

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public <R extends Request, S extends Response>MiddlewareServiceClient<R, S> getMiddlewareServiceClient(
			Class<R> request, Class<S> response) throws MiddlewareServiceException;

	/**
	 *  RetrievalRequest.class
	 * @param request
	 * @param response
	 * @return
	 */
	public <R extends Request, S extends Response> MiddlewareServiceProvider<R,S> getMiddlewareServiceProvider(
			Handler<R,S> handler) throws MiddlewareServiceException;
}
