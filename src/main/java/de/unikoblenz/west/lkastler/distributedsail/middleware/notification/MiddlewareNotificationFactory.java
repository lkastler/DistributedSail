package de.unikoblenz.west.lkastler.distributedsail.middleware.notification;

/**
 * provides methods to create NotificationReceiver and NotificationSender.
 * 
 * @author lkastler
 */
public interface MiddlewareNotificationFactory {

	public <T extends Notification> NotificationSender<T> getNotificationSender(Class<T> notificationType) throws MiddlewareNotificationException;
	
	public <T extends Notification> NotificationReceiver<T, NotificationHandler<T>> getNotificationReceiver(NotificationHandler<T> handlerType) throws MiddlewareNotificationException;
}
