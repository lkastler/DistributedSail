package de.unikoblenz.west.lkastler.distributedsail.middleware;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.RetrievalRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.RetrievalResponse;

/**
 * dispatches a retrieval query and orders the correct DistributedSail storages to send results to the DistributedRepository.
 * 
 * @author lkastler
 */
public abstract class RetrieveDispatcher extends Dispatcher<RetrievalRequest, RetrievalResponse> {
	
	
}
