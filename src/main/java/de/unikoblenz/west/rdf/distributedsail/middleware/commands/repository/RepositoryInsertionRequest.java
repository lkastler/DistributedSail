package de.unikoblenz.west.rdf.distributedsail.middleware.commands.repository;

import java.util.Random;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

/**
 * implementation of an RDF insertion.
 * 
 * @author lkastler
 */
public class RepositoryInsertionRequest extends InsertionRequest {

	/** */
	private static final long serialVersionUID = 1L;

	private static final Random rand = new Random(System.currentTimeMillis());
	
	private Resource subject;
	private URI predicate;
	private Value object;
	
	/**
	 * creates a InsertionRequest for an RDF triple with given subject, predicate, and object.
	 * @param subject - subject of RDF triple to insert.
	 * @param predicate - predicate of RDF triple to insert.
	 * @param object - object of RDF triple to insert.
	 */
	public RepositoryInsertionRequest(Resource subject, URI predicate, Value object) {
		super(rand.nextLong());
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleInsertionRequest [id=" + getId() + ";" + subject + " " + predicate + " " + object + "]";
	}	
}
