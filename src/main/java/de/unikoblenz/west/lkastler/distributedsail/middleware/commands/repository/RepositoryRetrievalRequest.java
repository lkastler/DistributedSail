package de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

public class RepositoryRetrievalRequest extends RetrievalRequest {

	/** */
	private static final long serialVersionUID = 1L;
	
	private Resource subject;
	private URI predicate;
	private Value object;
	
	public RepositoryRetrievalRequest(Resource subj, URI pred, Value obj) {
		this.subject = subj;
		this.predicate = pred;
		this.object = obj;
	}

	/**
	 * @return the subject
	 */
	public Resource getSubject() {
		return subject;
	}

	/**
	 * @return the predicate
	 */
	public URI getPredicate() {
		return predicate;
	}

	/**
	 * @return the object
	 */
	public Value getObject() {
		return object;
	}

}
