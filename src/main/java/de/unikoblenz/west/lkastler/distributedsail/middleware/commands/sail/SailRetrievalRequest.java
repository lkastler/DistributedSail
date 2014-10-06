package de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.RetrievalRequest;

/**
 * implements the SailRequest interface for retrieval tasks.
 * 
 * @author lkastler
 */
public class SailRetrievalRequest implements SailRequest {

	/** */
	private static final long serialVersionUID = 1L;

	protected Resource subject;
	protected URI predicate;
	protected Value object;
	
	/**
	 * TODO add doc
	 * @param req
	 * @return
	 */
	public static SailRetrievalRequest create(RetrievalRequest req){
		return null;
	}

	/**
	 * @param subject
	 * @param predicate
	 * @param object
	 */
	protected SailRetrievalRequest(Resource subject, URI predicate, Value object) {
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

}
