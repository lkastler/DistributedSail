package de.unikoblenz.west.rdf.distributedsail.middleware.commands.sail;

import de.unikoblenz.west.rdf.distributedsail.middleware.services.Response;

/**
 * a Response sent from a DistributedSailConnector.
 * 
 * @author lkastler
 */
public interface SailResponse extends SailCommand, Response {

	public SailRequest getRequest();
}
