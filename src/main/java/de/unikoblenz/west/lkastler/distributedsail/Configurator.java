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
	
	/**
	 * main, if needed
	 * @param args
	 */
	public static void main(String... args) {
		log.info("start");
		log.info("end");
	}
}
