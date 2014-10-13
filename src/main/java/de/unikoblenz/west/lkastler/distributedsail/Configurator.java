package de.unikoblenz.west.lkastler.distributedsail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * configures the SAIL-based distributed Datastore
 * 
 * @author lkastler
 */
abstract public class Configurator {

	protected static final Logger log = LoggerFactory.getLogger(Configurator.class);
	
	public static final String CHANNEL_INSERTION = "inproc://insert";
	public static final String CHANNEL_RETRIEVAL = "inproc://retrieve";
	public static final String CHANNEL_SAIL_INSERTION = "inproc://sail-insert";
	public static final String CHANNEL_SAIL_RETRIEVAL = "inproc://sail-retrieve";
	public static final String CHANNEL_NOTIFICATION = "ipc://notify";
	
	public static final int MAX_STORES = 2;
	
	
}
