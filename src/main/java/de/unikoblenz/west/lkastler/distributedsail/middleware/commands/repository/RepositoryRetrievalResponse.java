package de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository;

import info.aduna.iteration.CloseableIteration;

import org.openrdf.model.Statement;
import org.openrdf.sail.SailException;

import de.unikoblenz.west.lkastler.distributedsail.middleware.IntermediateResult;

public class RepositoryRetrievalResponse implements RetrievalResponse {

	/** */
	private static final long serialVersionUID = 1L;

	private IntermediateResult<Statement,SailException> result;
	
	/**
	 * @param result
	 */
	public RepositoryRetrievalResponse(
			IntermediateResult<Statement, SailException> result) {
		super();
		this.result = result;
	}

	public CloseableIteration<Statement,SailException> getResult() {
		return result;
	}
	
}
