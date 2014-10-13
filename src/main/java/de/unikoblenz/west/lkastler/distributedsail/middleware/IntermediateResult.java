package de.unikoblenz.west.lkastler.distributedsail.middleware;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.openrdf.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.aduna.iteration.CloseableIteration;
import info.aduna.iteration.Iterations;

/**
 * 
 * @author lkastler
 * @param <E>
 * @param <X>
 */
public class IntermediateResult<E extends Statement, X extends Exception> implements CloseableIteration<E,X>, Serializable {

	/** */
	private static final long serialVersionUID = 1L;

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final LinkedList<E> list;
	
	private transient Iterator<E> iter = null;
		
	/**
	 * TODO add doc
	 * @param it
	 * @return
	 * @throws X
	 */
	public static <E extends Statement, X extends Exception> IntermediateResult<E,X> create(CloseableIteration<E,X> it) throws X {
		if(it == null) {
			return new IntermediateResult<E, X>(new LinkedList<E>());
		}
		return new IntermediateResult<E,X>(Iterations.addAll(it, new LinkedList<E>()));
	}
	
	/**
	 * TODO add doc
	 * @param res
	 * @param it
	 * @return
	 * @throws X
	 */
	public static <E extends Statement, X extends Exception> IntermediateResult<E,X> merge(IntermediateResult<E,X> res, CloseableIteration<E,X> it) throws X {
		return new IntermediateResult<E,X>(Iterations.addAll(it, res.asList()));
	}
	
	public static <E extends Statement, X extends Exception> IntermediateResult<E,X> merge(IntermediateResult<E,X> left, IntermediateResult<E,X> right) throws X {
		if(left == null) {
			return new IntermediateResult<E, X>(new LinkedList<E>());
		}
		if(right == null) {
			return new IntermediateResult<E, X>(new LinkedList<E>());
		}
		LinkedList<E> list = left.list;
		list.addAll(right.list);
		
		return new IntermediateResult<E,X>(list);
	}
	 
	/**
	 * TODO add doc
	 * @param list
	 */
	public IntermediateResult(List<E> list) {
		this.list =  new LinkedList<E>(list);
		iter = list.iterator();
		
		log.debug("created; list=" +list);
	}
	
	public IntermediateResult() {
		this(new LinkedList<E>());
	}
	
	/*
	 * (non-Javadoc)
	 * @see info.aduna.iteration.Iteration#hasNext()
	 */
	public boolean hasNext() throws X {
		if(iter == null) {
			iter = list.iterator();
		}
		
		return iter.hasNext();
	}

	/*
	 * (non-Javadoc)
	 * @see info.aduna.iteration.Iteration#next()
	 */
	public E next() throws X {
		if(iter == null) {
			iter = list.iterator();
		}
		
		return iter.next();
	}

	/*
	 * (non-Javadoc)
	 * @see info.aduna.iteration.Iteration#remove()
	 */
	public void remove() throws X {
		if(iter == null) {
			iter = list.iterator();
		}
		
		iter.remove();
	}

	/*
	 * (non-Javadoc)
	 * @see info.aduna.iteration.CloseableIteration#close()
	 */
	public void close() throws X {
	}

	
	public List<E> asList() {
		return list;
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IntermediateResult [list=" + list + "]";
	}	
}
