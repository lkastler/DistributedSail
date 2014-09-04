package de.unikoblenz.west.lkastler.distributedsail;

import org.openrdf.sail.Sail;
import org.openrdf.sail.SailException;

import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider;
/**
 * connects a SAIL storage with the middleware
 * @author lkastler
 */
public class DistributedSailConnector {
	
	protected Sail sail;
	protected MiddlewareServiceProvider<?,?> provider;
	
	public DistributedSailConnector(Sail sail, MiddlewareServiceProvider<?,?> provider) {
		this.sail = sail;
		this.provider = provider;
	}
	
	public void start() throws SailException {
		provider.start();
		sail.initialize();
	}
	
	public void stop() throws SailException {
		
		provider.stop();
		sail.shutDown();
	}
}

