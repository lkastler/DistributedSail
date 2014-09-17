package de.unikoblenz.west.lkastler.distributedsail.middleware.commands;

/**
 * simple response that does nothing.
 * 
 * @author lkastler
 */
public class DefaultResponse implements InsertionResponse, RetrievalResponse {

	/** */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "default response";
	}

	
}
