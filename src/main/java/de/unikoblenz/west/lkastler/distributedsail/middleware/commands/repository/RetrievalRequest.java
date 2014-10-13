package de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository;

import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;

/**
 * a Request for a retrieval task.
 * @author lkastler
 */
public class RetrievalRequest implements Request {

	/** */
	private static final long serialVersionUID = 1L;

	private final long id;
	
	public RetrievalRequest(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	
}
