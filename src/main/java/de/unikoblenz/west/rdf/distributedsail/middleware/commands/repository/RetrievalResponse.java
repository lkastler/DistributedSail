package de.unikoblenz.west.rdf.distributedsail.middleware.commands.repository;

import de.unikoblenz.west.rdf.distributedsail.middleware.services.Response;

/**
 * a response to a retrieval task.
 * 
 * @author lkastler
 */
public interface RetrievalResponse extends Response {

	public RetrievalRequest getRequest();
}
