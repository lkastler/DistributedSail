package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionRequest;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.InsertionResponse;

/**
 * dispatches insertion queries and stores given data to a DistributedSailConnection.
 *
 * @author lkastler
 */
public abstract class InsertionTransformator extends Transformator<InsertionRequest, InsertionResponse> {
	
	
}
