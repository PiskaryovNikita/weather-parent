package main.tasks.n8;

import java.io.Serializable;

import interfaces.task8.CyclicCollection;
import interfaces.task8.CyclicItem;

public class CyclicCollectionImpl implements CyclicCollection, Serializable {
	private static final long serialVersionUID = 1L;
	private CyclicItem first;
	// setim sami
	private CyclicItem last;
	private int size;

	private boolean contains(CyclicItem cyclicItem) {
		CyclicItem curr = new CyclicItemImpl();
		curr.setNextItem(first);
		while (curr.nextItem() != null && curr != last) {
			curr = curr.nextItem();
			if (curr.equals(cyclicItem)) {
				return true;
			}
		}
		return false;
	}

	// true if coll changed.
	@Override
	public boolean add(CyclicItem cyclicItem) {
		if (cyclicItem == null) {
			throw new NullPointerException();
		} else if (first == null) {
			first = cyclicItem;
			last = cyclicItem;
			cyclicItem.setNextItem(cyclicItem);
			size++;
			return true;
		} else if (contains(cyclicItem)) {
			throw new IllegalArgumentException();
		}
		insertAfter(last, cyclicItem);
		last = cyclicItem;
		return true;
	}

	@Override
	public CyclicItem getFirst() {
		return first;
	}

	public CyclicItem getLast() {
		return last;
	}

	private CyclicItem findItem(Object value) {
		CyclicItem curr = first;
		while (!curr.getValue().equals(value)) {
			curr = curr.nextItem();
		}
		return curr;
	}

	@Override
	public void insertAfter(CyclicItem item, CyclicItem newItem) {
		if (item == null || newItem == null) {
			throw new NullPointerException();
		} else if (!contains(item) || contains(newItem)) {
			throw new IllegalArgumentException();
		}
		CyclicItem next;
		if (item.nextItem() != null) {
			next = item.nextItem();
		} else {
			item = findItem(item.getValue());
			next = item.nextItem();
		}
		item.setNextItem(newItem);
		newItem.setNextItem(next);
		if (item.equals(last)) {
			last = newItem;
		}
		size++;
	}

	private CyclicItem beforeItem(CyclicItem cyclicItem) {
		CyclicItem curr = first;
		// удаляем по об. или по знач.
		while (curr.nextItem() != cyclicItem && !curr.nextItem().equals(cyclicItem)) {
			curr = curr.nextItem();
		}
		return curr;
	}

	@Override
	public boolean remove(CyclicItem cyclicItem) {
		if (cyclicItem == null) {
			throw new NullPointerException();
		} else if (!contains(cyclicItem)) {
			return false;
		}
		CyclicItem beforeItem = beforeItem(cyclicItem);
		CyclicItem nextItem;
		// об входящий в колл.
		if (cyclicItem.nextItem() != null) {
			nextItem = cyclicItem.nextItem();
		} else {// об имеющий один. знач. с об. входящим в колл.
			nextItem = beforeItem.nextItem().nextItem();
		}
		beforeItem.setNextItem(nextItem);
		if (first.equals(last)) {
			first = last = null;
		} else if (cyclicItem.equals(last)) {
			last = beforeItem;
		} else if (cyclicItem.equals(first)) {
			first = nextItem;
		}
		size--;
		return true;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public String toString() {
		CyclicItem curr = new CyclicItemImpl();
		curr.setNextItem(first);
		String res = "[";
		while (curr != last) {
			curr = curr.nextItem();
			res += curr.toString();
			if (curr != last) {
				res += ", ";
			} else if (curr == last) {
				res += "]";
			}
		}
		return res;
	}

	public static void main(String[] args) {
		try {
			CyclicCollectionImpl collection = new CyclicCollectionImpl();
			CyclicItem ci1 = new CyclicItemImpl(1);
			System.out.println(collection.remove(new CyclicItemImpl(2)));
			collection.add(ci1);
			collection.add(new CyclicItemImpl(2));
			collection.add(new CyclicItemImpl(3));
			collection.add(new CyclicItemImpl(4));
			System.out.println(collection.getFirst());
			System.out.println(collection.getLast());
			System.out.println(collection);
			System.out.println(collection.remove(new CyclicItemImpl(4)));
			System.out.println(collection);
			collection.insertAfter(new CyclicItemImpl(1), new CyclicItemImpl(10));
			collection.insertAfter(new CyclicItemImpl(3), new CyclicItemImpl(17));
			collection.insertAfter(new CyclicItemImpl(17), new CyclicItemImpl(19));
			System.out.println(collection.remove(new CyclicItemImpl(1)));
			collection.insertAfter(new CyclicItemImpl(2), new CyclicItemImpl(13));
			System.out.println(collection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * Дотетсить run junit tests git log4j ant
		 */
	}
}
