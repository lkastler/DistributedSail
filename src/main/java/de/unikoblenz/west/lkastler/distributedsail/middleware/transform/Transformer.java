package de.unikoblenz.west.lkastler.distributedsail.middleware.transform;

/**
 * dispatches a message and redirect it according to.
 * @author lkastler
 */
public interface Transformer {
	
	/**
	 * starts this Transformator.
	 * @throws TransformerException triggered if this transformer could not be started.
	 */
	public void start() throws TransformerException;	
	/**
	 * stops this Transformator.
	 * @throws TransformerException triggered if this transformer could not be stopped.
	 */
	public void stop() throws TransformerException;
}
