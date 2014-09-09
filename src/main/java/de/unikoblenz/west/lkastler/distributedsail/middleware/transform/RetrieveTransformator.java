package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.RetrievalRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.RetrievalResponse;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationReceiver;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceProvider;

/**
 * dispatches a retrieval query and orders the correct DistributedSail storages to send results to the DistributedRepository.
 * 
 * @author lkastler
 */
public abstract class RetrieveTransformator extends Transformator<RetrievalRequest, RetrievalResponse> {

	public RetrieveTransformator(
			MiddlewareServiceProvider<RetrievalRequest, RetrievalResponse> clientRequests,
			NotificationReceiver<Notification, NotificationHandler<Notification>> receiver) {
		super(clientRequests, receiver);
	}
	
	
}
