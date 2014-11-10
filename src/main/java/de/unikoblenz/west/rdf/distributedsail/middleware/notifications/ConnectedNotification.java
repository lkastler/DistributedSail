package de.unikoblenz.west.rdf.distributedsail.middleware.notifications;

import de.unikoblenz.west.rdf.distributedsail.middleware.notifications.Notification;

/**
 * Signalizes connected NotificationReceivers that sender has connected.
 * 
 * @author lkastler
 */
public class ConnectedNotification implements Notification {

	private final long id;
	
	private static final long serialVersionUID = 1L;

	/**
	 * constructs a notification that NotificationSender, identified by id, has connected to the middleware.
	 * @param id - id of newly connected NotificationSender.
	 */
	public ConnectedNotification(long id) {
		this.id = id;
	}

	/**
	 * returns the id of the sender.
	 * @return the id of the sender.
	 */
	public long getId() {
		return id;
	}
}
