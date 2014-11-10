package de.unikoblenz.west.rdf.distributedsail.middleware.commands.repository;

import java.util.Random;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

public class RepositoryRetrievalRequest extends RetrievalRequest {

	/** */
	private static final long serialVersionUID = 1L;
	
	private Resource subject;
	private URI predicate;
	private Value object;
	
	public RepositoryRetrievalRequest(long id, Resource subj, URI pred, Value obj) {
		super(id);
		
		this.subject = subj;
		this.predicate = pred;
		this.object = obj;
	}
	
	public RepositoryRetrievalRequest(Resource subj, URI pred, Value obj) {
		this(new Random(System.currentTimeMillis()).nextLong(), subj, pred, obj);
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RepositoryRetrievalRequest [subject=" + subject
				+ ", predicate=" + predicate + ", object=" + object + "]";
	}

}
