package de.unikoblenz.west.lkastler.distributedsail.middleware;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.Handler;

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
	 */
	public void execute(RequestType request, Handler<RequestType, ResponseType> handler);
}
