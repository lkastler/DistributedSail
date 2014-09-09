package de.unikoblenz.west.lkastler.distributedsail.middleware.handler;

import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;

/**
 * provides methods to handle incoming requests and produce corresponding responses.
 * 
 * @author lkastler
 * 
 * @param <RequestType> - implementation of the Request interface to identify incoming requests.
 * @param <ResponseType> - implementation of the Response interface to identify outgoing responses.
 */
public interface Handler<RequestType extends Request, ResponseType extends Response> {
	
	/**
	 * handles an given request and produces a response.
	 * @param request - request that should be handled.
	 * @return a response corresponding to the given request
	 * @throws Throwable thrown if something went wrong.
	 */
	public ResponseType handleRequest(RequestType request) throws Throwable;
}
