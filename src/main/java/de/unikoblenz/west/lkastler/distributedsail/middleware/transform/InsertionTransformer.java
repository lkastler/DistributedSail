package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationReceiver;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;

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
			ServiceProvider<InsertionRequest, InsertionResponse> clientRequests,
			NotificationReceiver<Notification, NotificationHandler<Notification>> receiver) {
		
		super(clientRequests, receiver);
	}
	
}
