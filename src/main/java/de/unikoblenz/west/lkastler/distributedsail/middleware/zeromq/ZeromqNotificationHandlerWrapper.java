package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import java.io.Serializable;

import net.hh.request_dispatcher.RequestHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationHandler;

/**
 * TODO add comment
 * @author lkastler
 * @param <T>
 */
public class ZeromqNotificationHandlerWrapper<T extends Notification> implements RequestHandler<T, Serializable> {

	/** */
	private static final long serialVersionUID = 1L;

	private final NotificationHandler<T> handler;

	/**
	 * TODO add comment
	 * @param handler
	 */
	public ZeromqNotificationHandlerWrapper(NotificationHandler<T> handler) {
		this.handler = handler;
	}

	/*
	 * (non-Javadoc)
	 * @see net.hh.request_dispatcher.RequestHandler#handleRequest(java.io.Serializable)
	 */
	public Serializable handleRequest(T notification) throws Exception {
		handler.handleNotification(notification);
		return null;
	}

}
