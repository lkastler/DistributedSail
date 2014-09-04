package de.unikoblenz.west.lkastler.distributedsail.middleware;

import net.hh.request_dispatcher.Callback;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;

/**
 * uses a service via middleware.
 * @author lkastler
 */
public interface MiddlewareServiceClient<RequestType extends Request, ResponseType extends Response> {

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
