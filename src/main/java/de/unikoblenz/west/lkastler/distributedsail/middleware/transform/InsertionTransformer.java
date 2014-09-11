package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationReceiver;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceProvider;

/**
 * dispatches insertion queries and stores given data to a DistributedSailConnection.
 *
 * @author lkastler
 */
public abstract class InsertionTransformer extends Transformer<InsertionRequest, InsertionResponse> {

	/**
	 * 
	 * @param clientRequests
	 * @param receiver - NotificationReceiver for DistributedSailConnector discovery.
	 */
	public InsertionTransformer(
			MiddlewareServiceProvider<InsertionRequest, InsertionResponse> clientRequests,
			NotificationReceiver<Notification, NotificationHandler<Notification>> receiver) {
		
		super(clientRequests, receiver);
	}
	
}
