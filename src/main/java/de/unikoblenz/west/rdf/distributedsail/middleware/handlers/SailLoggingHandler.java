package de.unikoblenz.west.rdf.distributedsail.middleware.handlers;

import de.unikoblenz.west.rdf.distributedsail.middleware.commands.sail.SailRequest;
import de.unikoblenz.west.rdf.distributedsail.middleware.commands.sail.SailResponse;

/**
 * simple logger on sail side
 * @author lkastler
 */
public class SailLoggingHandler<R extends SailRequest, S extends SailResponse> extends
		LoggingServiceHandler<R, S> {

	/**
	 * creates logging handler with default Response.
	 * @param defaultResponse - default response sent to everyone.
	 */
	public SailLoggingHandler(S defaultResponse) {
		super(defaultResponse);
	}

}
