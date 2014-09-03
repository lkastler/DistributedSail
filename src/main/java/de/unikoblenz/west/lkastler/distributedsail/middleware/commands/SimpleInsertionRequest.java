package de.unikoblenz.west.lkastler.distributedsail.middleware.commands;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

public class SimpleInsertionRequest extends InsertionRequest {

	/** */
	private static final long serialVersionUID = 1L;

	private Resource subject;
	private URI predicate;
	private Value object;
	public SimpleInsertionRequest(Resource subject, URI predicate, Value object) {
		super();
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}
	public Resource getSubject() {
		return subject;
	}
	public URI getPredicate() {
		return predicate;
	}
	public Value getObject() {
		return object;
	}
	@Override
	public String toString() {
		return "SimpleInsertionRequest [" + subject + " " + predicate + " " + object + "]";
	}	
}
