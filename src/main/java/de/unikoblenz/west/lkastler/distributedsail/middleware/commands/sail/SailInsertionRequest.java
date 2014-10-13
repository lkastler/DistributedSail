package de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail;

import java.util.Random;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.RepositoryInsertionRequest;

/**
 * implementation of the SailInsertionRequest interface.
 * @author lkastler
 */
public class SailInsertionRequest implements SailRequest {

	private static final long serialVersionUID = 1L;

	private static Random rand = new Random(System.currentTimeMillis());
	
	private final long id;
	
	private Resource subject;
	private URI predicate;
	private Value object;
	
	
	public static SailInsertionRequest create(RepositoryInsertionRequest req) {
		return new SailInsertionRequest(req.getSubject(), req.getPredicate(),req.getObject());
	}
	
	/**
	 * @param subject
	 * @param predicate
	 * @param object
	 */
	public SailInsertionRequest(Resource subject, URI predicate,
			Value object) {
		
		id = rand.nextLong();
		
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

	public long getId() {
		return id;
	}
}
