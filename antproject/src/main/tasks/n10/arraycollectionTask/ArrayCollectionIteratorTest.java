package main.tasks.n10.arraycollectionTask;


import static org.junit.Assert.assertArrayEquals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import interfaces.task5.ArrayCollection;
import interfaces.task5.ArrayIterator;
import main.tasks.n5.ArrayCollectionImpl;

import java.util.NoSuchElementException;

import org.junit.Test;

public class ArrayCollectionIteratorTest {
	private ArrayCollection<Double> arrayCollection = new ArrayCollectionImpl<>();
	private Double[] doubleArray = { 1d, 2d, 3d };

	@Test
	public void testGetArray() {
		ArrayIterator<Double> it = (ArrayIterator<Double>) arrayCollection.iterator();
		assertNotNull("getArray should not return null", it);

		arrayCollection.setArray(doubleArray);

		it = (ArrayIterator<Double>) arrayCollection.iterator();
		assertNotNull("getArray returned null after setArray", it.getArray());
		assertArrayEquals("added to collection array should be equals iterator array", doubleArray, it.getArray());
	}

	@Test
	public void testHasNext() {
		ArrayIterator<Double> it = (ArrayIterator<Double>) arrayCollection.iterator();
		assertFalse("hasNext should return false in clean collection", it.hasNext());

		arrayCollection.setArray(doubleArray);
		it = (ArrayIterator<Double>) arrayCollection.iterator();
		assertTrue("collections should has next element after add", it.hasNext());

		it.next();
		it.next();
		it.next();
		assertFalse("collections shouldn't has next element after all nexts", it.hasNext());
	}

	@Test
	public void testNext() {
		arrayCollection.setArray(doubleArray);
		ArrayIterator<Double> it = (ArrayIterator<Double>) arrayCollection.iterator();

		assertEquals("next should return correct value", doubleArray[0], it.next());
		assertEquals("next should return correct value", doubleArray[1], it.next());
		assertEquals("next should return correct value", doubleArray[2], it.next());
		try {
			assertFalse("collections shouldn't has next element after all nexts", it.hasNext());
			it.next();
			fail("should throw NoSuchElementException if has no element for next");
		} catch (NoSuchElementException e) {
		} catch (Exception e) {
			fail("should throw NoSuchElementException instead of " + e.getClass().getName());
		}
	}

	@Test
	public void testRemove() {
		ArrayIterator<Double> it = (ArrayIterator<Double>) arrayCollection.iterator();
		arrayCollection.setArray(doubleArray);
		it = (ArrayIterator<Double>) arrayCollection.iterator();
		try {
			try {
				it.remove();
				fail("should throw IllegalStateException if next not called");
			} catch (IllegalStateException e) {
			}
			it.next();
			it.next();
			it.next();
			it.remove();
			assertEquals(2, arrayCollection.size());
		} catch (UnsupportedOperationException e) {
		}
	}

}
