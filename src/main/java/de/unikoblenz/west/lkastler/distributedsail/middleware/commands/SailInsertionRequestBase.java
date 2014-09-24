package de.unikoblenz.west.lkastler.distributedsail.middleware.commands;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

/**
 * implementation of the SailInsertionRequest interface.
 * @author lkastler
 */
public class SailInsertionRequestBase implements SailInsertionRequest {

	private static final long serialVersionUID = 1L;

	private Resource subject;
	private URI predicate;
	private Value object;
	
	
	public static SailInsertionRequestBase makeSailInsertionRequest(SimpleInsertionRequest req) {
		return new SailInsertionRequestBase(req.getSubject(), req.getPredicate(),req.getObject());
	}
	
	/**
	 * @param subject
	 * @param predicate
	 * @param object
	 */
	public SailInsertionRequestBase(Resource subject, URI predicate,
			Value object) {
		super();
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
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
		return "SailInsertionRequestBase [subject=" + subject + ", predicate="
				+ predicate + ", object=" + object + "]";
	}
}
