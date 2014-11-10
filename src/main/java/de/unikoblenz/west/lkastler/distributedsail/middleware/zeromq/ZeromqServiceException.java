package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import de.unikoblenz.west.rdf.distributedsail.middleware.services.MiddlewareServiceException;

/**
 * triggered if something went wrong with ZeroMQ connections.
 * @author lkastler
 */
public class ZeromqServiceException extends MiddlewareServiceException {

	public ZeromqServiceException(Exception e) {
		super(e);
	}

	/** */
	private static final long serialVersionUID = 1L;

}
