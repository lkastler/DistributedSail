package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import net.hh.request_dispatcher.Dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationSender;

/**
 * implementation of NotificationSender for ZeroMQ.
 * @author lkastler
 * @param <T>
 */
public class ZeromqNotificationSender<T extends Notification> implements NotificationSender<T> {

	private static final Logger log = LoggerFactory.getLogger(ZeromqNotificationSender.class);
	
	protected Dispatcher dispatcher = new Dispatcher();
	protected final String endPoint;
	protected final Class<T> notificationClass;
	
	/**
	 * TODO add comment
	 * @param endPoint
	 */
	public ZeromqNotificationSender(Class<T> notificationClass, String endPoint) {
		this.endPoint = endPoint;
		this.notificationClass = notificationClass;
		
		log.debug("created");
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationSender#start()
	 */
	public void start() {
		dispatcher.registerService(notificationClass, endPoint);
		log.debug("started");
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationSender#stop()
	 */
	public void stop() {
		dispatcher.shutdown();
		log.debug("stopped");
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationSender#sendNotification(de.unikoblenz.west.lkastler.distributedsail.middleware.notification.Notification)
	 */
	public void sendNotification(T notification) {
		dispatcher.execute(notification, null);
	} 
	
	
	
}
