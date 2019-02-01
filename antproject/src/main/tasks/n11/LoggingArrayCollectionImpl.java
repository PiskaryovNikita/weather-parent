package main.tasks.n11;


import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;

import interfaces.logging.LoggingArrayCollection;
import main.tasks.n5.ArrayCollectionImpl;

public class LoggingArrayCollectionImpl<E> extends ArrayCollectionImpl<E> implements LoggingArrayCollection<E> {
	private static Logger logger = new Log4jLoggerFactory().getLogger("LoggingArrayCollectionImpl");
	static {
		PropertyConfigurator.configure("./src/tasks/n11/log4j.properties");
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public boolean add(E e) {
		logger.trace("at add");
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		try {
			logger.trace("at addAll");
			return super.addAll(c);
		} catch (Exception e) {
			logger.error("at addAll", e);
			throw e;
		}
	}

	@Override
	public void clear() {
		logger.trace("at clear");
		super.clear();
	}

	@Override
	public boolean contains(Object o) {
		logger.trace("at contains");
		return super.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		try {
			logger.trace("at containsAll");
			return super.containsAll(c);
		} catch (Exception e) {
			logger.error("at containsAll", e);
			throw e;
		}
	}

	@Override
	public boolean equals(Object obj) {
		logger.trace("at equals");
		return super.equals(obj);
	}

	@Override
	public Object[] getArray() {
		logger.trace("at getArray");
		return super.getArray();
	}

	@Override
	public boolean isEmpty() {
		logger.trace("at isEmpty");
		return super.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		logger.trace("at iterator");
		return super.iterator();
	}

	@Override
	public boolean remove(Object o) {
		try {
			logger.trace("at remove");
			return super.remove(o);
		} catch (Exception e) {
			logger.error("at remove", e);
			throw e;
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		try {
			logger.trace("at removeAll");
			return super.removeAll(c);
		} catch (Exception e) {
			logger.error("at removeAll", e);
			throw e;
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		try {
			logger.trace("at retainAll");
			return super.retainAll(c);
		} catch (Exception e) {
			logger.error("at retainAll", e);
			throw e;
		}
	}

	@Override
	public void setArray(E[] a) {
		logger.trace("at setArray");
		super.setArray(a);
	}

	@Override
	public int size() {
		logger.trace("at size");
		return super.size();
	}

	@Override
	public Object[] toArray() {
		try {
			logger.trace("at toArray");
			return super.toArray();
		} catch (Exception e) {
			logger.error("at toArray", e);
			throw e;
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		try {
			logger.trace("at toArray");
			return super.toArray(a);
		} catch (Exception e) {
			logger.error("at toArray", e);
			throw e;
		}
	}

	@Override
	public String toString() {
		logger.trace("at toString");
		return super.toString();
	}
}
