package de.unikoblenz.west.lkastler.distributedsail;

import org.openrdf.sail.Sail;
import org.openrdf.sail.SailException;

import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.SailResponse;

/**
 * connects a SAIL storage with the middleware
 * @author lkastler
 */
public class DistributedSailConnector {
	
	protected Sail sail;
	protected MiddlewareServiceProvider<SailRequest,SailResponse> provider;
	
	public DistributedSailConnector(Sail sail, MiddlewareServiceProvider<SailRequest,SailResponse> provider) {
		this.sail = sail;
		this.provider = provider;
	}
	
	public void start() throws SailException {
		sail.initialize();
		provider.start();
	}
	
	public void stop() throws SailException {
		provider.stop();
		sail.shutDown();
	}
}

