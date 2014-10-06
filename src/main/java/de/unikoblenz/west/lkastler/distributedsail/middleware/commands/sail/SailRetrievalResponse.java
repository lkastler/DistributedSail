package de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail;

import info.aduna.iteration.CloseableIteration;

import org.openrdf.model.Statement;
import org.openrdf.sail.SailException;

/**
 * TODO add doc
 * @author lkastler
 */
public class SailRetrievalResponse implements SailResponse {

	/** */
	private static final long serialVersionUID = 1L;

	protected final SailRequest request;
	
	protected final CloseableIteration<Statement, SailException> result;
	
	/**
	 * TODO add doc
	 * @param request
	 * @param result
	 */
	public SailRetrievalResponse(SailRequest request,
			CloseableIteration<Statement, SailException> result) {
		super();
		this.request = request;
		this.result = result;
	}



	/**
	 * @return the result
	 */
	public CloseableIteration<Statement, SailException> getResult() {
		return result;
	}

	/**
	 * TODO add doc
	 */
	public SailRequest getRequest() {
		return request;
	}

}
