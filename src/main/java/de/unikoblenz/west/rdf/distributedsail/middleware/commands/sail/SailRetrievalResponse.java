package de.unikoblenz.west.rdf.distributedsail.middleware.commands.sail;

import info.aduna.iteration.CloseableIteration;
import info.aduna.iteration.Iterations;

import java.io.Serializable;
import java.util.List;

import org.openrdf.model.Statement;
import org.openrdf.sail.SailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.rdf.distributedsail.middleware.IntermediateResult;

/**
 * TODO add doc
 * 
 * @author lkastler
 */
public class SailRetrievalResponse implements SailResponse, Serializable {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	/** */
	private static final long serialVersionUID = 1L;

	protected final String id;
	
	protected final SailRequest request;

	protected final List<Statement> result;

	/**
	 * TODO add doc
	 * 
	 * @param id
	 * @param request
	 * @param result
	 * @throws SailException 
	 */
	public SailRetrievalResponse(String id, SailRequest request,
			CloseableIteration<Statement, SailException> result) throws SailException {
		super();
		this.id = id;
		this.request = request;
		
		this.result = Iterations.asList(result);
		
		log.debug("created");
	}

	/**
	 * @return the result
	 */
	public CloseableIteration<Statement, SailException> getResult() {
		return new IntermediateResult<Statement,SailException>(result);
	}

	/**
	 * TODO add doc
	 */
	public SailRequest getRequest() {
		return request;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SailRetrievalResponse [id=" + id + ", request=" + request + ", result="
				+ result + "]";
	}
}
