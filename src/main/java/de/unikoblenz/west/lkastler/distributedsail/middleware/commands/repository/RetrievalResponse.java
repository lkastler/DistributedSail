package de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository;

import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;

/**
 * a response to a retrieval task.
 * 
 * @author lkastler
 */
public interface RetrievalResponse extends Response {

	public RetrievalRequest getRequest();
}
