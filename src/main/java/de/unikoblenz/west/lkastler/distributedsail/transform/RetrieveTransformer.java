package de.unikoblenz.west.lkastler.distributedsail.transform;

import java.util.HashMap;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.Configurator;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.DefaultRepositoryResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.RetrievalRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.DefaultSailResponse;
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
import net.hh.request_dispatcher.Callback;

/**
 * dispatches a retrieval query and orders the correct DistributedSail storages
 * to send results to the DistributedRepository.
 * 
 * @author lkastler
 */
public class RetrieveTransformer extends Callback<SailResponse>
	implements Transformer, ServiceHandler<RetrievalRequest, DefaultRepositoryResponse> {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final MiddlewareServiceFactory factory;
	
	private ServiceProvider<RetrievalRequest, DefaultRepositoryResponse> repoConnection;
	private LinkedList<ServiceClient<SailRetrievalRequest, SailResponse>> storeConnections;
	
	private HashMap<Long, SailRetrievalRequest> collectors = new HashMap<Long, SailRetrievalRequest>();
	
	/**
	 * TODO add doc
	 * @param factory
	 */
	public RetrieveTransformer(MiddlewareServiceFactory factory) {
		super();
		this.factory = factory;
	}
	
	public void start() throws TransformerException {
		try {
			repoConnection = factory.createServiceProvider(Configurator.CHANNEL_RETRIEVAL, this);
			
			repoConnection.start();
			
			storeConnections = new LinkedList<ServiceClient<SailRetrievalRequest, SailResponse>>();
			
			for(int i = 0; i < Configurator.MAX_STORES; i++) {
				ServiceClient<SailRetrievalRequest, SailResponse> store = factory.createServiceClient(Configurator.CHANNEL_RETRIEVAL + Integer.toString(i), SailRetrievalRequest.class, SailResponse.class);
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
		
		for(ServiceClient<?,?> store : storeConnections) {
			store.stop();
		}
	}

	public DefaultRepositoryResponse handleRequest(RetrievalRequest request)
			throws Throwable {
		log.debug("received retrieval request: " + request.toString());
		
		for(ServiceClient<SailRetrievalRequest, SailResponse> store : storeConnections) {
			store.execute(SailRetrievalRequest.create(request), this);
		}
		
		log.debug("done");
		
		return new DefaultRepositoryResponse();
	}

	@Override
	public void onSuccess(SailResponse reply) {
		log.debug("success!");
	}


}
