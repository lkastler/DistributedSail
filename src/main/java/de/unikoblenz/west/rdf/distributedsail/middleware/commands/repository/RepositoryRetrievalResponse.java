package de.unikoblenz.west.rdf.distributedsail.middleware.commands.repository;

import org.openrdf.model.Statement;
import org.openrdf.sail.SailException;

import de.unikoblenz.west.rdf.distributedsail.middleware.IntermediateResult;

public class RepositoryRetrievalResponse implements RetrievalResponse {

	/** */
	private static final long serialVersionUID = 1L;

	private RetrievalRequest request;
	
	private IntermediateResult<Statement,SailException> result;
	
	/**
	 * @param request
	 * @param result
	 */
	public RepositoryRetrievalResponse(
			RetrievalRequest request,
			IntermediateResult<Statement, SailException> result) {
		this.request = request;
		this.result = result;
	}

	public IntermediateResult<Statement,?> getResult() {
		return result;
	}

	public RetrievalRequest getRequest() {
		return request;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RepositoryRetrievalResponse [request=" + request + ", result="
				+ result + "]";
	}
}
