package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationReceiver;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;

/**
 * dispatches a message and redirect it according to.
 * @author lkastler
 */
public abstract class Transformer<R extends Request, T extends Response> {

	protected final ServiceProvider<R,T> clientRequests;
	
	protected final NotificationReceiver<Notification, NotificationHandler<Notification>> receiver;

	public Transformer(
			ServiceProvider<R, T> clientRequests,
			NotificationReceiver<Notification, NotificationHandler<Notification>> receiver) {
		this.clientRequests = clientRequests;
		this.receiver = receiver;
	}
	
	/**
	 * starts this Transformator.
	 */
	public void start() {
		clientRequests.start();
		receiver.start();
		
		startInternally();
	}
	
	/**
	 * stops this Transformator.
	 */
	public void stop() {
		stopInternally();
		
		clientRequests.stop();
		receiver.stop();
	}
	
	/**
	 * starts additional internal things.
	 */
	abstract protected void startInternally();
	
	
	/**
	 * stops additional internal things.
	 */
	abstract protected void stopInternally();
}
