package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.MiddlewareNotificationException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.MiddlewareNotificationFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationReceiver;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.NotificationSender;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.ServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;

/**
 * implementation of the MiddlewareServiceFactory interface for ZeroMQ
 * 
 * @author lkastler
 */
public class ZeromqFactory implements MiddlewareServiceFactory, MiddlewareNotificationFactory {

	//public static final String CHANNEL = "ipc://test";
	// FIXME inproc doesnt work, ipc does not allow multiple connection
	public static final String CHANNEL = "inproc://test";
	public static final String CHANNEL_NOTIFICATION = "inproc://notify";
	
	private static ZeromqFactory instance = null;
	
	/**
	 * returns the instance of ZeromqServiceFactory.
	 * @return the instance of ZeromqServiceFactory.
	 */
	public static ZeromqFactory getInstance() {
		if(instance == null)
			instance = new ZeromqFactory();
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.MiddlewareServiceFactory#getMiddlewareServiceClient(java.lang.Class, java.lang.Class)
	 */
	public <R extends Request, S extends Response> ServiceClient<R, S> createServiceClient(
			Class<R> request, Class<S> response)
			throws MiddlewareServiceException {
		
		return new ZeromqServiceClient<R, S>(CHANNEL, request);
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.MiddlewareServiceFactory#getMiddlewareServiceProvider(java.lang.Class)
	 */
	public <R extends Request, S extends Response> ServiceProvider<R, S> createServiceProvider(ServiceHandler<R, S> handler) throws MiddlewareServiceException {
			return new ZeromqServiceProvider<R,S>(CHANNEL, handler);
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.notification.MiddlewareNotificationFactory#getNotificationSender(java.lang.Class)
	 */
	public <T extends Notification> NotificationSender<T> createNotificationSender(
			Class<T> notificationType) throws MiddlewareNotificationException {
		return new ZeromqNotificationSender<T>(CHANNEL_NOTIFICATION, notificationType);
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.notification.MiddlewareNotificationFactory#getNotificationReceiver(de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationHandler)
	 */
	public <T extends Notification> NotificationReceiver<T, NotificationHandler<T>> createNotificationReceiver(
			NotificationHandler<T> handler)
			throws MiddlewareNotificationException {
		return new ZeromqNotificationReciever<T, NotificationHandler<T>>(CHANNEL_NOTIFICATION, handler);
	}
}
