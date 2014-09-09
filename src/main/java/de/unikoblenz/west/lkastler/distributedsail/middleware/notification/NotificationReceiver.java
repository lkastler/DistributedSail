package de.unikoblenz.west.lkastler.distributedsail.middleware.notification;

/**
 * TODO add comment
 * @author lkastler
 * @param <T>
 * @param <H>
 */
public interface NotificationReceiver<T extends Notification, H extends NotificationHandler<T>> {

	/**
	 * connects and starts this NotificationReciever.
	 */
	public void start();
	
	/**
	 * stops and disconnects this NotificationReciever.
	 */
	public void stop();
}
