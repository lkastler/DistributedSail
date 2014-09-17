package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

import net.hh.request_dispatcher.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.DefaultResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailInsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handlers.LoggingNotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handlers.LoggingServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.MiddlewareNotificationException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.MiddlewareNotificationFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationReceiver;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq.ZeromqFactory;

/**
 * dispatches insertion queries and stores given data to a DistributedSailConnection.
 *
 * @author lkastler
 */
public class InsertionTransformer<R1 extends Request, S1 extends Response, R2 extends SailRequest, S2 extends SailResponse> implements Transformer {

	protected final static Logger log = LoggerFactory.getLogger(InsertionTransformer.class); 

	private MiddlewareServiceFactory services;
	
	protected ServiceProvider<R1,S1> clientConnection;
	protected ServiceClient<R2, S2> storeConnection;
	
	public InsertionTransformer(MiddlewareServiceFactory services) {
		this.services = services;
	}

	public void start() throws TransformerException {
		try {
			clientConnection = services.createServiceProvider("ipc://insert", new ServiceHandler<R1,S1>() {
	
				public S1 handleRequest(R1 request) throws Throwable {
					log.debug("fooo: " + request);
					return (S1) new DefaultResponse();
				}
				
			});
		
			Class<R2> r = (Class<R2>) SailInsertionRequest.class;
			Class<S2> s = (Class<S2>) DefaultResponse.class;
			
			storeConnection = ZeromqFactory.getInstance().createServiceClient("ipc://sail-1", r, s);
		} catch (MiddlewareServiceException e) {
			e.printStackTrace();
			throw new TransformerException(e);
		}
		
		log.debug("created");
		
		clientConnection.start();
		storeConnection.stop();
	}	

	public void stop() {
		clientConnection.stop();
		storeConnection.stop();
	}
		
}