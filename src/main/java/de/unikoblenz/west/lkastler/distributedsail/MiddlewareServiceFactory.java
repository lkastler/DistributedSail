package de.unikoblenz.west.lkastler.distributedsail;

import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;

/**
 * creates MiddlewareServices.
 * 
 * @author lkastler
 */
public interface MiddlewareServiceFactory {

	/**
	 * TODO add comment
	 * @param request
	 * @param response
	 * @return
	 */
	public <R extends Request, S extends Response>MiddlewareServiceClient<R, S> getMiddlewareServiceClient(
			Class<R> request, Class<S> response) throws MiddlewareServiceException;

	/**
	 * TODO add comment
	 * @param request
	 * @param response
	 * @return
	 */
	public <R extends Request, S extends Response> MiddlewareServiceProvider<R,S> getMiddlewareServiceProvider(
			ServiceHandler<R,S> handler) throws MiddlewareServiceException;
}
