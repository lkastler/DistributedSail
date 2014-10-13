package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

import java.util.LinkedList;
import java.util.Random;

import net.hh.request_dispatcher.Callback;
import net.hh.request_dispatcher.RequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.Configurator;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.RepositoryInsertionResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.repository.RepositoryInsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.SailInsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.sail.SailInsertionResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.Transformer;
import de.unikoblenz.west.lkastler.distributedsail.middleware.transform.TransformerException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq.ZeromqFactory;

/**
 * dispatches insertion queries and stores given data to a
 * DistributedSailConnection.
 *
 * @author lkastler
 */
public class InsertionTransformer extends Callback<SailInsertionResponse>
		implements Transformer, ServiceHandler<RepositoryInsertionRequest, RepositoryInsertionResponse> {
	
	protected final Logger log = LoggerFactory
			.getLogger(InsertionTransformer.class);

	private MiddlewareServiceFactory services;
	
	private static final Random rand = new Random(1);
	private long id = rand.nextLong();
	
	protected ServiceProvider<RepositoryInsertionRequest, RepositoryInsertionResponse> repoConnection;
	protected LinkedList<ServiceClient<SailInsertionRequest, SailInsertionResponse>> storeConnection;

	public InsertionTransformer(MiddlewareServiceFactory services) {
		this.services = services;

		log.info(id + " created");
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.transform.Transformer#start()
	 */
	public void start() throws TransformerException {
		try {
			repoConnection = services
					.createServiceProvider(
							Configurator.CHANNEL_INSERTION,
							this);

			repoConnection.start();
			
			storeConnection = new LinkedList<ServiceClient<SailInsertionRequest, SailInsertionResponse>>(); 
			for(int i = 0; i < Configurator.MAX_STORES; i++) {
				
				ServiceClient<SailInsertionRequest,SailInsertionResponse> store = ZeromqFactory.getInstance().createServiceClient(
						Configurator.CHANNEL_SAIL_INSERTION + Integer.toString(i), SailInsertionRequest.class,
						SailInsertionResponse.class);
				
				store.start();
				
				storeConnection.add(store);
			}
		} catch (MiddlewareServiceException e) {
			e.printStackTrace();
			throw new TransformerException(e);
		}

		log.debug(id + " started");
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.transform.Transformer#stop()
	 */
	public void stop() {
		repoConnection.stop();
		for(ServiceClient<?,?> store: storeConnection) {
			store.stop();
		}
		
		log.debug(id + " stopped");
	}

	/*
	 * (non-Javadoc)
	 * @see net.hh.request_dispatcher.Callback#onSuccess(java.io.Serializable)
	 */
	@Override
	public void onSuccess(SailInsertionResponse reply) {
		log.debug(id + " received answer from DSC");
	}

	/* (non-Javadoc)
	 * @see net.hh.request_dispatcher.Callback#onTimeout()
	 */
	@Override
	public void onTimeout() {
		log.error("received timeout");
	}

	/* (non-Javadoc)
	 * @see net.hh.request_dispatcher.Callback#onError(net.hh.request_dispatcher.RequestException)
	 */
	@Override
	public void onError(RequestException e) {
		log.error("received error: " + e.getMessage());
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler#handleRequest(de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request)
	 */
	public RepositoryInsertionResponse handleRequest(RepositoryInsertionRequest request)
			throws Throwable {
		log.debug(id + " got request: " + request);

		storeConnection.get(rand.nextInt(storeConnection.size())).execute(SailInsertionRequest.create(request), this);
				
		return new RepositoryInsertionResponse();
	}

}