package de.unikoblenz.west.lkastler.distributedsail.middleware.notifications;

/**
 * provides methods to create NotificationReceiver and NotificationSender.
 * 
 * @author lkastler
 */
public interface MiddlewareNotificationFactory {

	/**
	 * creates a notification sender.
	 * 
	 * @param notificationType
	 *            - defines the type of notification.
	 * @return a new NotificationSender.
	 * @throws MiddlewareNotificationException
	 *             triggered if notification sender could not be created.
	 */
	public <T extends Notification> NotificationSender<T> createNotificationSender(
			Class<T> notificationType) throws MiddlewareNotificationException;

	/**
	 * creates a notification receiver.
	 * 
	 * @param handlerType
	 *            - NotificationHandler that handles incoming messages.
	 * @return a new NotificationReceiver.
	 * @throws MiddlewareNotificationException
	 *             triggered if notification receivera could not be created.
	 */
	public <T extends Notification> NotificationReceiver<T, NotificationHandler<T>> createNotificationReceiver(
			NotificationHandler<T> handlerType)
			throws MiddlewareNotificationException;
}
