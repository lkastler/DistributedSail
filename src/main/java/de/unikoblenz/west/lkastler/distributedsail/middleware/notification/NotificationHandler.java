package de.unikoblenz.west.lkastler.distributedsail.middleware.notification;

/**
 * handles notifications. 
 * 
 * @author lkastler
 * 
 * @param <T> - notification to handle
 */
public interface NotificationHandler<T extends Notification> {

	/**
	 * handles given notification.
	 * @param notification - notification to handle.
	 */
	public void handleNotification(T notification);
}
