package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

import java.util.HashMap;
import java.util.LinkedList;

import net.hh.request_dispatcher.Callback;

import org.openrdf.model.Statement;
import org.openrdf.sail.SailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.Configurator;
import de.unikoblenz.west.lkastler.distributedsail.middleware.IntermediateResult;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.RepositoryRetrievalResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.RetrievalRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.SailResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.SailRetrievalRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.SailRetrievalResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.Transformer;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.TransformerException;

/**
 * dispatches a retrieval query and orders the correct DistributedSail storages
 * to send results to the DistributedRepository.
 * 
 * @author lkastler
 */
public class RetrievalTransformer extends Callback<SailResponse> implements
		Transformer,
		ServiceHandler<RetrievalRequest, RepositoryRetrievalResponse> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final MiddlewareServiceFactory factory;

	private ServiceProvider<RetrievalRequest, RepositoryRetrievalResponse> repoConnection;
	private LinkedList<ServiceClient<SailRetrievalRequest, SailResponse>> storeConnections;

	private HashMap<SailRetrievalRequest, IntermediateResult<Statement, SailException>> collectors = new HashMap<SailRetrievalRequest, IntermediateResult<Statement, SailException>>();
	private int count = 0;

	/**
	 * TODO add doc
	 * 
	 * @param factory
	 */
	public RetrievalTransformer(MiddlewareServiceFactory factory) {
		super();
		this.factory = factory;
	}

	public void start() throws TransformerException {
		try {
			repoConnection = factory.createServiceProvider(
					Configurator.CHANNEL_RETRIEVAL, this);

			repoConnection.start();

			storeConnections = new LinkedList<ServiceClient<SailRetrievalRequest, SailResponse>>();

			for (int i = 0; i < Configurator.MAX_STORES; i++) {
				ServiceClient<SailRetrievalRequest, SailResponse> store = factory
						.createServiceClient(Configurator.CHANNEL_RETRIEVAL
								+ Integer.toString(i),
								SailRetrievalRequest.class, SailResponse.class);
				storeConnections.add(store);
				store.start();
			}

		} catch (MiddlewareServiceException e) {
			log.error("coult not create service provider: ", e);
			throw new TransformerException(e);
		}
	}

	public void stop() throws TransformerException {
		repoConnection.stop();

		for (ServiceClient<?, ?> store : storeConnections) {
			store.stop();
		}
	}

	public RepositoryRetrievalResponse handleRequest(RetrievalRequest request)
			throws Throwable {
		log.debug("received retrieval request: " + request.toString());

		SailRetrievalRequest req = SailRetrievalRequest.create(request);
		
		count = storeConnections.size();
		
		for (ServiceClient<SailRetrievalRequest, SailResponse> store : storeConnections) {
			store.execute(req, this);
		}

		while(count > 0) {
			// FIXME no busy waiting!
		}
		
		log.debug("done");
		
		IntermediateResult<Statement, SailException> result = collectors.get(req);

		collectors.remove(req);
		
		return new RepositoryRetrievalResponse(result);
	}

	@Override
	public void onSuccess(SailResponse reply) {
		
		log.debug("answer received: " + reply.toString());
		
		if(reply instanceof SailRetrievalResponse) {
			SailRetrievalRequest req = (SailRetrievalRequest) reply.getRequest();
			SailRetrievalResponse resp = (SailRetrievalResponse)reply;
			
			if(collectors.get(req) == null) {
				collectors.put(req, new IntermediateResult<Statement, SailException>(new SailException("something something dark side")));
			}
			try {
				collectors.get(req).addAll(resp.getResult());
		
			} catch (SailException e) {
				log.error("well i dont know", e);
			}
			count--;
		}
	}

}
