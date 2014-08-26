package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import de.unikoblenz.west.lkastler.distributedsail.middleware.Handler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;
import net.hh.request_dispatcher.RequestHandler;

/**
 * TODO add Comment
 * @author lkastler
 * @param <RequestType>
 * @param <ResponseType>
 */
public class ZeromqHandlerWrapper<RequestType extends Request, ResponseType extends Response>  implements
		RequestHandler<RequestType, ResponseType>{

	private Handler<RequestType, ResponseType> handler;
	
	/** */
	private static final long serialVersionUID = 1L;

	/**
	 *  TODO add comment
	 * @param handler
	 */
	public ZeromqHandlerWrapper(Handler<RequestType, ResponseType> handler) {
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
