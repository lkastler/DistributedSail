package de.unikoblenz.west.lkastler.distributedsail.middleware.handler;

import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;

/**
 * simple logger on sail side
 * @author lkastler
 */
public class SailLoggingHandler<R extends Request, S extends Response> extends
		LoggingHandler<R, S> {

	/**
	 * creates logging handler with default Response.
	 * @param defaultResponse - default response sent to everyone.
	 */
	public SailLoggingHandler(S defaultResponse) {
		super(defaultResponse);
	}

}
