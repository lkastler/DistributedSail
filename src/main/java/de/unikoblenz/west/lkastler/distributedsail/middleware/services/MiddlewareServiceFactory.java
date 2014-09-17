package de.unikoblenz.west.lkastler.distributedsail.middleware.services;


/**
 * creates service connections to a middleware.
 * 
 * @author lkastler
 */
public interface MiddlewareServiceFactory {

	/**
	 * creates a client service connection to a middleware.
	 * @param endpoint TODO
	 * @param request - type of Request the client requests.
	 * @param response - type of Response the client accepts.
	 * @return a new client service connection to the middleware.
	 * @throws MiddlewareServiceException triggered if client service connection could not be created.
	 */
	public <R extends Request, S extends Response>ServiceClient<R, S> createServiceClient(
			String endpoint, Class<R> request, Class<S> response) throws MiddlewareServiceException;

	/**
	 * creates a provider service connection to a middleware.
	 * @param endpoint TODO
	 * @param handler - a ServiceHandler for handling incoming requests.
	 * @return a new provider service connection to the middleware.
	 * @throws MiddlewareServiceException triggered if provider service connection could not be created.
	 */
	public <R extends Request, S extends Response> ServiceProvider<R,S> createServiceProvider(
			String endpoint, ServiceHandler<R,S> handler) throws MiddlewareServiceException;
}
