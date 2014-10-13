package de.unikoblenz.west.lkastler.distributedsail.middleware.handlers;

import org.openrdf.model.Resource;
import org.openrdf.sail.SailConnection;
import org.openrdf.sail.SailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.SailInsertionResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.SailInsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;

public class SailInsertionHandler implements
		ServiceHandler<SailInsertionRequest, SailInsertionResponse> {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final SailConnection sailConnect;
	
		/**
	 * @param connection
	 */
	public SailInsertionHandler(SailConnection connection) {
		super();
		this.sailConnect = connection;
		
		log.debug("created");
	}




	public SailInsertionResponse handleRequest(SailInsertionRequest request)
			throws Throwable {
		log.debug("handle request: " + request);

		if (!sailConnect.isActive()) {
			sailConnect.begin();
			sailConnect.addStatement(request.getSubject(),
					request.getPredicate(), request.getObject(),
					new Resource[0]);
			sailConnect.commit();
			
			return new SailInsertionResponse(request);
		}

		throw new SailException("connection could not be opened");
	}

}
