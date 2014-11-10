package de.unikoblenz.west.rdf.distributedsail.middleware.transform;

import java.util.HashMap;
import java.util.LinkedList;

import net.hh.request_dispatcher.Callback;

import org.openrdf.model.Statement;
import org.openrdf.sail.SailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.rdf.distributedsail.Configurator;
import de.unikoblenz.west.rdf.distributedsail.middleware.commands.repository.RepositoryRetrievalRequest;
import de.unikoblenz.west.rdf.distributedsail.middleware.commands.repository.RepositoryRetrievalResponse;
import de.unikoblenz.west.rdf.distributedsail.middleware.commands.sail.SailRetrievalRequest;
import de.unikoblenz.west.rdf.distributedsail.middleware.commands.sail.SailRetrievalResponse;
import de.unikoblenz.west.rdf.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.rdf.distributedsail.middleware.services.MiddlewareServiceFactory;
import de.unikoblenz.west.rdf.distributedsail.middleware.services.ServiceClient;
import de.unikoblenz.west.rdf.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.rdf.distributedsail.middleware.services.ServiceProvider;
import de.unikoblenz.west.rdf.distributedsail.middleware.transform.Transformer;
import de.unikoblenz.west.rdf.distributedsail.middleware.transform.TransformerException;
import de.unikoblenz.west.rdf.distributedsail.middleware.IntermediateResult;
import de.unikoblenz.west.rdf.distributedsail.middleware.Status;

/**
 * dispatches a retrieval query and orders the correct DistributedSail storages
 * to send results to the DistributedRepository.
 * 
 * @author lkastler
 */
public class RetrievalTransformer extends Callback<SailRetrievalResponse> implements
		Transformer,
		ServiceHandler<RepositoryRetrievalRequest, RepositoryRetrievalResponse> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private Status status = Status.not_running;
	
	private final MiddlewareServiceFactory factory;

	private ServiceProvider<RepositoryRetrievalRequest, RepositoryRetrievalResponse> repoConnection;
	private LinkedList<ServiceClient<SailRetrievalRequest, SailRetrievalResponse>> storeConnections;

	// TODO do i have to set this volatile?
	private HashMap<Long, IntermediateResult<Statement, SailException>> collectors = new HashMap<Long, IntermediateResult<Statement, SailException>>();
	
	private int pendingResponses = 0;
	
	/**
	 * creates a new RetrievalTransformer using the given MiddlewareServiceFactory for connections.
	 * @param factory - creates connections for this RetrievalTransformer from it.
	 */
	public RetrievalTransformer(MiddlewareServiceFactory factory) {
		this.factory = factory;
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.transform.Transformer#start()
	 */
	public void start() throws TransformerException {
		if(status == Status.running) {
			throw new TransformerException("Transformer already running");
		}
		
		try {
			repoConnection = factory.createServiceProvider(
					Configurator.CHANNEL_RETRIEVAL, this);

			repoConnection.start();

			storeConnections = new LinkedList<ServiceClient<SailRetrievalRequest, SailRetrievalResponse>>();

			for (int i = 0; i < Configurator.MAX_STORES; i++) {
				ServiceClient<SailRetrievalRequest, SailRetrievalResponse> store = factory
						.createServiceClient(Configurator.CHANNEL_SAIL_RETRIEVAL + Integer.toString(i),
								SailRetrievalRequest.class, SailRetrievalResponse.class);
				storeConnections.add(store);
				store.start();
			}
			
			status = Status.running;
			
			log.debug("created");
		} catch (MiddlewareServiceException e) {
			log.error("coult not create service provider: ", e);
			throw new TransformerException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.transform.Transformer#stop()
	 */
	public void stop() throws TransformerException {
		if(status == Status.not_running) {
			throw new TransformerException("Transformer is not running");
		}
		
		repoConnection.stop();

		for (ServiceClient<?, ?> store : storeConnections) {
			store.stop();
		}
		
		status = Status.not_running;
		
		log.debug("stopped");
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler#handleRequest(de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request)
	 */
	public RepositoryRetrievalResponse handleRequest(RepositoryRetrievalRequest request)
			throws Throwable {
		log.debug("received retrieval request: " + request.toString());

		SailRetrievalRequest req = SailRetrievalRequest.create(request);
		
		log.debug("sending message to stores");
		
		pendingResponses = storeConnections.size();
		
		for (ServiceClient<SailRetrievalRequest, SailRetrievalResponse> store : storeConnections) {
			store.execute(req, this);
		}

		while(pendingResponses > 0) {
			// FIXME no busy waiting!
		}
		
		log.debug("recieved all messages from stores");
		
		IntermediateResult<Statement, SailException> result = collectors.get(req.getId());

		log.debug("collective answer: " + result);
		
		collectors.remove(req.getId());
		
		return new RepositoryRetrievalResponse(request, result);
	}

	/*
	 * (non-Javadoc)
	 * @see net.hh.request_dispatcher.Callback#onSuccess(java.io.Serializable)
	 */
	@Override
	public void onSuccess(SailRetrievalResponse reply) {
		
		log.debug("answer received: " + reply.toString());
		
		if(reply instanceof SailRetrievalResponse) {
			SailRetrievalRequest req = (SailRetrievalRequest) reply.getRequest();
			
			try {
				log.debug("before: " + collectors.get(req.getId()));
				
				collectors.put(req.getId(), IntermediateResult.merge(collectors.get(req.getId()) == null ? new IntermediateResult<Statement, SailException>() : collectors.get(req.getId()), reply.getResult()));
				
				log.debug("after: " + collectors.get(req.getId()));
				
				pendingResponses--;
			} catch (SailException e) {
				log.error("ERROR", e);
			}
			
			log.debug("done");
		}
	}

}
