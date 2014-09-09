package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

import de.unikoblenz.west.lkastler.distributedsail.middleware.MiddlewareServiceProvider;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Request;
import de.unikoblenz.west.lkastler.distributedsail.middleware.commands.Response;

/**
 * dispatches a message and redirect it according to.
 * @author lkastler
 */
public abstract class Transformator<R extends Request, T extends Response> {
	// TODO find a more appropriate name
	protected MiddlewareServiceProvider<R,T> in;
}
