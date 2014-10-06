package de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail;

/**
 * implementation of a SailResponse.
 * 
 * @author lkastler
 */
public class SailInsertionResponse implements SailResponse {

	/** */
	private static final long serialVersionUID = 1L;

	private SailRequest req;
	
	/**
	 * TODO add doc
	 * @param request
	 */
	public SailInsertionResponse(SailRequest request) {
		req = request;
	}
	
	@Override
	public String toString() {
		return "DefaultSailResponse";
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.SailResponse#getRequest()
	 */
	public SailRequest getRequest() {
		return req;
	}

	
}
