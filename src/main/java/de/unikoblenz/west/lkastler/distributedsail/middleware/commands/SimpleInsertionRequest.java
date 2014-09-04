package de.unikoblenz.west.lkastler.distributedsail.middleware.commands;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

/**
 * implementation of an RDF insertion.
 * @author lkastler
 */
public class SimpleInsertionRequest extends InsertionRequest {

	/** */
	private static final long serialVersionUID = 1L;

	private Resource subject;
	private URI predicate;
	private Value object;
	
	/**
	 * creates a InsertionRequest for an RDF triple with given subject, predicate, and object.
	 * @param subject - subject of RDF triple to insert.
	 * @param predicate - predicate of RDF triple to insert.
	 * @param object - object of RDF triple to insert.
	 */
	public SimpleInsertionRequest(Resource subject, URI predicate, Value object) {
		super();
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}
	
	/**
	 * returns the RDF triple subject of this SimpleInsertionRequest. 
	 * @return the RDF triple subject of this SimpleInsertionRequest.
	 */
	public Resource getSubject() {
		return subject;
	}
	
	/**
	 * returns the RDF triple predicate of this SimpleInsertionRequest. 
	 * @return the RDF triple predicate of this SimpleInsertionRequest.
	 */
	public URI getPredicate() {
		return predicate;
	}
	
	/**
	 * returns the RDF triple object of this SimpleInsertionRequest. 
	 * @return the RDF triple object of this SimpleInsertionRequest.
	 */
	public Value getObject() {
		return object;
	}
	
	@Override
	public String toString() {
		return "SimpleInsertionRequest [" + subject + " " + predicate + " " + object + "]";
	}	
}
