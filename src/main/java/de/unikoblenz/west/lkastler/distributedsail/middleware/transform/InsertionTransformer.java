package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

import net.hh.request_dispatcher.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.Configurator;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.DefaultResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.DefaultSailResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailInsertionRequestBase;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SimpleInsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq.ZeromqFactory;

/**
 * dispatches insertion queries and stores given data to a DistributedSailConnection.
 *
 * @author lkastler
 */
public class InsertionTransformer implements Transformer {

	protected final Logger log = LoggerFactory.getLogger(InsertionTransformer.class); 

	private MiddlewareServiceFactory services;
	
	protected ServiceProvider<SimpleInsertionRequest,DefaultResponse> clientConnection;
	protected ServiceClient<SailInsertionRequestBase,DefaultSailResponse> storeConnection;
	
	public InsertionTransformer(MiddlewareServiceFactory services) {
		this.services = services;
		
		log.info("created");
	}

	public void start() throws TransformerException {
		try {
			clientConnection = services.createServiceProvider(Configurator.CHANNEL_INSERTION, new ServiceHandler<SimpleInsertionRequest,DefaultResponse>() {

				public DefaultResponse handleRequest(SimpleInsertionRequest request)
						throws Throwable {
					//BasicConfigurator.configure();
					log.debug("got request: " + request);
//					System.out.println("got request: " + request);
					
					storeConnection.execute(SailInsertionRequestBase.makeSailInsertionRequest(request), new Callback<DefaultSailResponse>() {

						@Override
						public void onSuccess(DefaultSailResponse reply) {
							log.debug("WOHO!!!!!");
						}
						
					});
					
					return new DefaultResponse();
				}
			});
		
			storeConnection = ZeromqFactory.getInstance().createServiceClient(Configurator.CHANNEL_SAIL, SailInsertionRequestBase.class, DefaultSailResponse.class);
		} catch (MiddlewareServiceException e) {
			e.printStackTrace();
			throw new TransformerException(e);
		}
		
		log.debug("created");
		
		clientConnection.start();
		storeConnection.start();
	}	

	public void stop() {
		clientConnection.stop();
		storeConnection.stop();
	}
		
}