package de.unikoblenz.west.lkastler.distributedsail.middleware.notifications;

/**
 * sends a notification to several NotificationRecievers.
 * 
 * @author lkastler
 * @param <T> - type of notification
 */
public interface NotificationSender<T extends Notification> {

	/**
	 * TODO add comment
	 */
	public void start();
	
	/**
	 * TODO add comment
	 */
	public void stop();
	
	/**
	 * TODO add comment
	 * @param notification
	 */
	public void sendNotification(T notification);
}
