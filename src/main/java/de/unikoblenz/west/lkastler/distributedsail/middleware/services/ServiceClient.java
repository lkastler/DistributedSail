package de.unikoblenz.west.lkastler.distributedsail.middleware.services;

import net.hh.request_dispatcher.Callback;

/**
 * uses a service via middleware.
 * @author lkastler
 */
public interface ServiceClient<RequestType extends Request, ResponseType extends Response> {

	/**
	 * connects and starts this MiddlewareServiceClient.
	 */
	public void start();
	
	/**
	 * stops and disconnects this MiddlewareServiceClient.
	 */
	public void stop();
	
	/**
	 * execute a request via the middleware.
	 * @param request request to dispatch.
	 * @param callback handles the asynchronous callback.
	 */
	public void execute(RequestType request, Callback<ResponseType> callback);
}
