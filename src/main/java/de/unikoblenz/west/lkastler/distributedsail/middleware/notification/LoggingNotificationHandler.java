package de.unikoblenz.west.lkastler.distributedsail.middleware.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO add doc
 * @author lkastler
 * @param <T>
 */
public class LoggingNotificationHandler<T extends Notification> implements NotificationHandler<T> {

	private final Logger log = LoggerFactory.getLogger(LoggingNotificationHandler.class);

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationHandler#handleNotification(de.unikoblenz.west.lkastler.distributedsail.middleware.notification.Notification)
	 */
	public void handleNotification(T notification) {
		log.info(notification.toString());
	}

}
