package de.unikoblenz.west.lkastler.distributedsail.middleware.commands;

import de.unikoblenz.west.lkastler.distributedsail.middleware.notifications.Notification;

/**
 * Signalizes connected NotificationReceivers that sender has connected.
 * 
 * @author lkastler
 */
public class ConnectedNotification implements Notification {

	private final String id;
	
	private static final long serialVersionUID = 1L;

	/**
	 * constructs a notification that NotificationSender, identified by id, has connected to the middleware.
	 * @param id - id of newly connected NotificationSender.
	 */
	public ConnectedNotification(String id) {
		this.id = id;
	}

	/**
	 * returns the id of the sender.
	 * @return the id of the sender.
	 */
	public String getId() {
		return id;
	}
}
