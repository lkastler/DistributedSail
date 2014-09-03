package de.unikoblenz.west.lkastler.distributedsail.middleware.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;

public class LoggingHandler<RequestType extends Request, ResponseType extends Response> implements Handler<RequestType, ResponseType> {

	private final Logger log = LoggerFactory.getLogger(LoggingHandler.class);
	private final ResponseType defaultResponse;
	
	public LoggingHandler(ResponseType defaultResponse) {
		this.defaultResponse = defaultResponse;
	}
	
	public ResponseType handleRequest(Request request) throws Throwable {
		log.debug("received: " + request);
		
		return defaultResponse;
	}
}
