package de.unikoblenz.west.lkastler.distributedsail.middleware;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionResponse;

/**
 * dispatches insertion queries and stores given data to a DistributedSail storage.
 *
 * @author lkastler
 */
public abstract class InsertionDispatcher extends Dispatcher<InsertionRequest, InsertionResponse> {
	
	
}
