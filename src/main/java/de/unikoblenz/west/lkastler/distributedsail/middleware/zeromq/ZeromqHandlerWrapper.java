package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;
import net.hh.request_dispatcher.RequestHandler;

/**
 * wraps a Handler object to a RequestHandler object.
 * 
 * @author lkastler
 * @param <RequestType> - Request implementation for RequestHandler.
 * @param <ResponseType> - Response implementation for RequestHandler.
 * 
 * @see RequestHandler
 */
public class ZeromqHandlerWrapper<RequestType extends Request, ResponseType extends Response>  implements
		RequestHandler<RequestType, ResponseType> {

	/** */
	private static final long serialVersionUID = 1L;
	
	private ServiceHandler<RequestType, ResponseType> handler;
	
	/**
	 * creates this wrapper with the given Handler. 
	 * @param handler - Handler for handling requests and corresponding responses 
	 */
	public ZeromqHandlerWrapper(ServiceHandler<RequestType, ResponseType> handler) {
		this.handler = handler;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.hh.request_dispatcher.RequestHandler#handleRequest(java.io.Serializable)
	 */
	public ResponseType handleRequest(RequestType request) throws Exception {
		try {
			return handler.handleRequest(request);
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}

}
