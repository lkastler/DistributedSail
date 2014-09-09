package de.unikoblenz.west.lkastler.distributedsail.middleware.services;

/**
 * triggered if middleware services are not responding, not able to create and so on. 
 * 
 * @author lkastler
 */
public class MiddlewareServiceException extends Exception {

	public MiddlewareServiceException(Exception e) {
		super(e);
	}

	/** */
	private static final long serialVersionUID = 1L;

}
