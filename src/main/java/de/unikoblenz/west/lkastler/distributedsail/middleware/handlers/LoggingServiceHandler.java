package de.unikoblenz.west.lkastler.distributedsail.middleware.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;

/**
 * simple logging handler
 * 
 * @author lkastler
 * @param <RequestType>
 * @param <ResponseType>
 */
public class LoggingServiceHandler<RequestType extends Request, ResponseType extends Response> implements ServiceHandler<RequestType, ResponseType> {

	private final Logger log = LoggerFactory.getLogger(LoggingServiceHandler.class);
	private final ResponseType defaultResponse;
	
	/**
	 * creates a LoggingHandler with given default response.
	 * @param defaultResponse
	 */
	public LoggingServiceHandler(ResponseType defaultResponse) {
		this.defaultResponse = defaultResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.handler.Handler#handleRequest(de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request)
	 */
	public ResponseType handleRequest(RequestType request) throws Throwable {
		log.debug("received: " + request);
		
		return defaultResponse;
	}
}
