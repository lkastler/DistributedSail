package de.unikoblenz.west.lkastler.distributedsail.middleware.services;


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
