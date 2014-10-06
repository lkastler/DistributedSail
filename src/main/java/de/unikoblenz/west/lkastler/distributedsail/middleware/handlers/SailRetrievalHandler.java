package de.unikoblenz.west.lkastler.distributedsail.middleware.handlers;

import info.aduna.iteration.CloseableIteration;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.sail.SailConnection;
import org.openrdf.sail.SailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.SailRetrievalRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.SailRetrievalResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;

public class SailRetrievalHandler implements
		ServiceHandler<SailRetrievalRequest, SailRetrievalResponse> {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final SailConnection connection;
		
	/**
	 * @param connection
	 */
	public SailRetrievalHandler(SailConnection connection) {
		super();
		this.connection = connection;
	}

	public SailRetrievalResponse handleRequest(SailRetrievalRequest request)
			throws Throwable {
		
		log.debug("received request: " + request);
		
		if(connection.isActive()) {
			throw new SailException("active transaction still running");
		}
		
		connection.begin();
		
		@SuppressWarnings("unchecked")
		CloseableIteration<Statement, SailException> result = (CloseableIteration<Statement, SailException>) connection.getStatements(request.getSubject(), request.getPredicate(), request.getObject(), false, new Resource[0]);
		
		connection.commit();
		
		return new SailRetrievalResponse(request, result);
	}

}
