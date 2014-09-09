package de.unikoblenz.west.lkastler.distributedsail.middleware.zeromq;

import java.io.Serializable;

import net.hh.request_dispatcher.ZmqWorker;
import net.hh.request_dispatcher.ZmqWorkerProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.Notification;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationHandler;
import de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationReceiver;

/**
 * receives and processes notifications from a NotificationSender.
 * @author lkastler
 */
public class ZeromqNotificationReciever<T extends Notification, H extends NotificationHandler<T>> implements NotificationReceiver<T, H> {

	private final static Logger log = LoggerFactory.getLogger(ZeromqNotificationReciever.class);
	
	protected ZmqWorkerProxy proxy;
	
	/**
	 * creates a ZeroMQ ServiceProvider with given input channel and request handler.
	 * @param inputChannel - String representation of the input channel.
	 * @param handler - handler for incoming messages.
	 */
	public ZeromqNotificationReciever(final String inputChannel, NotificationHandler<T> handler) {
		proxy = new ZmqWorkerProxy(inputChannel);
		
		proxy.add(new ZmqWorker<T, Serializable>(new ZeromqNotificationHandlerWrapper<T>(handler)));
		
		log.debug("created");
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationReciever#start()
	 */
	public void start() {
		synchronized(proxy) {
			proxy.startWorkers();
		}
		log.debug("started");
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.distributedsail.middleware.notification.NotificationReciever#stop()
	 */
	public void stop() {
		synchronized(proxy) {
			proxy.shutdown();
		}
		log.debug("stopped");
	}

}
