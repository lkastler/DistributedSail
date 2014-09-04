package de.unikoblenz.west.lkastler.distributedsail.middleware;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;

/**
 * offeres a service via middleware to others.
 * @author lkastler
 */
public interface MiddlewareServiceProvider<RequestType extends Request, ResponseType extends Response> {

	/**
	 * connects and starts this MiddlewareServiceProvider
	 */
	public void start();
	
	/**
	 * stops and disconnects this MiddlewareServiceProvider
	 */
	public void stop();
}
