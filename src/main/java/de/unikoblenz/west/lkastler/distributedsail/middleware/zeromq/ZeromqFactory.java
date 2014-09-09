package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import de.unikoblenz.west.lkastler.distributedsail.MiddlewareServiceFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.handler.Handler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.MiddlewareNotificationException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.MiddlewareNotificationFactory;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationReceiver;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationSender;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceClient;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceException;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.services.Response;

/**
 * implementation of the MiddlewareServiceFactory interface for ZeroMQ
 * 
 * @author lkastler
 */
public class ZeromqFactory implements MiddlewareServiceFactory, MiddlewareNotificationFactory {

	public static final String CHANNEL = "ipc://test";
	public static final String CHANNEL_NOTIFICATION = "ipc://notify";
	
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
	public <R extends Request, S extends Response> MiddlewareServiceClient<R, S> getMiddlewareServiceClient(
			Class<R> request, Class<S> response)
			throws MiddlewareServiceException {
		
		return new ZeromqServiceClient<R, S>(request, CHANNEL);
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.MiddlewareServiceFactory#getMiddlewareServiceProvider(java.lang.Class)
	 */
	public <R extends Request, S extends Response>MiddlewareServiceProvider<R, S> getMiddlewareServiceProvider(Handler<R, S> handler) throws MiddlewareServiceException {
			return new ZeromqServiceProvider<R,S>(CHANNEL, handler);
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.notification.MiddlewareNotificationFactory#getNotificationSender(java.lang.Class)
	 */
	public <T extends Notification> NotificationSender<T> getNotificationSender(
			Class<T> notificationType) throws MiddlewareNotificationException {
		return new ZeromqNotificationSender<T>(notificationType, CHANNEL_NOTIFICATION);
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.notification.MiddlewareNotificationFactory#getNotificationReceiver(de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationHandler)
	 */
	public <T extends Notification> NotificationReceiver<T, NotificationHandler<T>> getNotificationReceiver(
			NotificationHandler<T> handler)
			throws MiddlewareNotificationException {
		return new ZeromqNotificationReciever<T, NotificationHandler<T>>(CHANNEL_NOTIFICATION, handler);
	}
}
