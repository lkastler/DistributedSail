package de.unikoblenz.west.lkastler.distributedsail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * configures the SAIL-based distributed Datastore
 * 
 * @author lkastler
 */
public class Configurator {

	protected static final Logger log = LoggerFactory.getLogger(Configurator.class);
	
	public static Configurator instance = null;
	
	private int count = 0;
	
	/**
	 * singleton class for Configurator.
	 * @return the instance of this Configurator. 
	 */
	public static Configurator getInstance() {
		if(instance == null) {
			instance = new Configurator();
		}
		
		return instance;
	}
	
	/**
	 * main, if needed
	 * @param args
	 */
	public static void main(String... args) {
		log.info("start");
		log.info("end");
	}
	
	private Configurator() {
		log.debug("created");
	}
	
	public int getCount() {
		return count;
	}
	
	public void increaseCount() {
		count++;
	}
}
