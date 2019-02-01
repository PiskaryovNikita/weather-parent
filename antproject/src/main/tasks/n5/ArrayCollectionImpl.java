package main.tasks.n5;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import interfaces.task5.ArrayCollection;
import interfaces.task5.ArrayIterator;

/**
 * try to copy array
 * 
 * @author piskaryov
 *
 * @param <E>
 */
public class ArrayCollectionImpl<E> implements ArrayCollection<E> {
	private Object[] arr = {};
	/**
	 * size - the number of elements the array contains.
	 */
	private int size;

	@Override
	public boolean add(final E e) {
		Object[] temp = new Object[arr.length + 1];
		System.arraycopy(arr, 0, temp, 0, arr.length);
		arr = temp;
		arr[size++] = e;
		return true;
	}

	@Override
	public boolean addAll(final Collection<? extends E> c) {
		if (c == null) {
			throw new NullPointerException();
		} else if (c == this) {
			throw new IllegalArgumentException();
		}
		Object[] copy = c.toArray();
		Object[] temp = Arrays.copyOf(arr, arr.length + copy.length);
		System.arraycopy(copy, 0, temp, arr.length, copy.length);
		arr = temp;
		size += copy.length;
		return true;
	}

	@Override
	public void clear() {
		arr = new Object[] {};
		size = 0;
	}

	@Override
	public boolean contains(final Object o) {
		if (size == 0) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (arr[i] == o) {
				return true;
			} else if (arr[i].equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		for (Object object : c) {
			if (!contains(object)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new ArrayIteratorImpl();
	}

	@Override
	public boolean remove(final Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < size; i++) {
			if (arr[i].equals(o)) {
				arr[i] = null;
				// offset
				for (int j = i; j < size - 1; j++) {
					arr[j] = arr[j + 1];
				}
				size--;
				arr = Arrays.copyOf(arr, size);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		int beforeModify = size();
		for (Object object : c) {
			remove(object);
		}
		arr = Arrays.copyOf(arr, size);
		return beforeModify != size();
	}

	/**
	 * sets intersection.
	 */
	@Override
	public boolean retainAll(final Collection<?> c) {
		final int beforeModify = size();
		if (c == null) {
			throw new NullPointerException();
		}
		out: for (int i = 0; i < size; i++) {
			for (Object object : c) {
				if (arr[i] != null && arr[i].equals(object)) {
					// skip remove
					continue out;
				}
			}
			arr[i] = null;
			for (int j = i; arr[j] != null && j < size; j++) {
				arr[j] = arr[j + 1];
			}
			size--;
			// после сдвига влево, указатель на след. эл.//-1+1
			i--;
		}
		return beforeModify != size;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(arr, size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(final T[] a) {
		if (a == null) {
			throw new NullPointerException();
		}
		if (a.length < size) {
			return (T[]) Arrays.copyOf(arr, size, a.getClass());
		}
		System.arraycopy(arr, 0, a, 0, size);
		return a;
	}

	@Override
	public Object[] getArray() {
		return arr;
	}

	@Override
	public void setArray(final E[] a) {
		if (a == null) {
			throw new NullPointerException();
		}
		this.size = a.length;
		this.arr = a;
	}

	@Override
	public String toString() {
		String res = "[";
		for (int i = 0; i < arr.length; i++) {
			res += arr[i];
			if (i < arr.length - 1) {
				res += ", ";
			}
		}
		return res += "]";
	}

	public class ArrayIteratorImpl implements ArrayIterator<E> {
		private int pointer;

		@Override
		public boolean hasNext() {
			if (pointer >= arr.length || arr[pointer] == null) {
				return false;
			}
			return true;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			if (pointer >= arr.length) {
				throw new NoSuchElementException();
			}
			return (E) arr[pointer++];
		}

		@Override
		public Object[] getArray() {
			return Arrays.copyOf(arr, size);
		}

	}
}
