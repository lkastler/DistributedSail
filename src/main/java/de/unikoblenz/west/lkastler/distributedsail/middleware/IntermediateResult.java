package de.unikoblenz.west.lkastler.distributedsail.middleware;

import java.util.Iterator;
import java.util.LinkedList;

import org.openrdf.model.Statement;

import info.aduna.iteration.CloseableIteration;
import info.aduna.iteration.Iterations;

public class IntermediateResult<E extends Statement, X extends Exception> implements CloseableIteration<E, X> {

	private LinkedList<E> list = new LinkedList<E>();
	
	private Iterator<E> iter = null;

	private final X exception;
	
	public IntermediateResult(X exception) {
		this.exception = exception;
	}
	
	public void addAll(CloseableIteration<E,X> it) throws X {
		list = Iterations.addAll(it, list);
	}
	
	
	public boolean hasNext() throws X {
		checkIter();
		
		return iter.hasNext();
	}

	public E next() throws X {
		checkIter();
		
		return iter.next();
	}

	public void remove() throws X {
		checkIter();
		
		iter.remove();
	}

	public void close() throws X {
		iter = null;
	}

	private void checkIter() throws X {
		if(iter == null) {
			return;
		}
		throw exception;
	}
	
}
