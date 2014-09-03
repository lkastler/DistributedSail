package de.unikoblenz.west.lkastler.distributedsail.middleware.handler;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;

/**
 * TODO add comment
 * @author lkastler
 */
public interface Handler<RequestType extends Request, ResponseType extends Response> {
	
	/**
	 * TODO add comment
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	public ResponseType handleRequest(Request request) throws Throwable;
}
