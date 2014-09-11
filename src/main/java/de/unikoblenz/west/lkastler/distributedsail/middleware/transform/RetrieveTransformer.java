package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.RetrievalRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.RetrievalResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationReceiver;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;

/**
 * dispatches a retrieval query and orders the correct DistributedSail storages
 * to send results to the DistributedRepository.
 * 
 * @author lkastler
 */
public abstract class RetrieveTransformer extends
		Transformer<RetrievalRequest, RetrievalResponse> {

	/**
	 * creates an RetrieveTransformer with given MiddlewareServiceProvider for
	 * clients and NotificationReceiver for DistributedSailConnector discovery.
	 * 
	 * @param clientRequests
	 * @param receiver
	 */
	public RetrieveTransformer(
			ServiceProvider<RetrievalRequest, RetrievalResponse> clientRequests,
			NotificationReceiver<Notification, NotificationHandler<Notification>> receiver) {
		super(clientRequests, receiver);
	}

}
