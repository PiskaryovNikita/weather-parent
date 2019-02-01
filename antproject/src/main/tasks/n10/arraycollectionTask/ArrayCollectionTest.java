package main.tasks.n10.arraycollectionTask;


import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import interfaces.task5.ArrayCollection;
import main.tasks.n5.ArrayCollectionImpl;

import java.util.Arrays;

import org.junit.Before;

public class ArrayCollectionTest {
	private static Class<?> testingClass = ArrayCollectionImpl.class;
	private ArrayCollection<Double> arrayCollection = null;
	private Double[] doubleArray = { 1d, 2d, 3d };

	@SuppressWarnings("unchecked")
	@Before
	public void before() throws Exception {
		arrayCollection = (ArrayCollection<Double>) testingClass.newInstance();
	}

	@Test(timeout = 1000)
	public void testSetArrayNull() {
		try {
			arrayCollection.setArray(null);
			fail("set array should throw NullPointerException " + "on setArray(null)");
		} catch (NullPointerException e) {
		} catch (Exception e) {
			fail("set array should throw NullPointerException " + "on setArray(null)");
		}
	}

	@Test(timeout = 1000)
	public void testSetGetArray() {
		assertNotNull("getArray should not return null after create", arrayCollection.getArray());
		arrayCollection.setArray(doubleArray);
		assertNotNull("getArray returned null after setArray", arrayCollection.getArray());
		assertArrayEquals("checking for equals of derived and initial arrays", doubleArray, arrayCollection.getArray());
	}

	@Test(timeout = 1000)
	public void testAddNull() {
		assertTrue("add should return true if addition was done", arrayCollection.add(null));
		assertNotNull("getArray returned null after add", arrayCollection.getArray());
		assertEquals("length of array should be 1", 1, arrayCollection.getArray().length);
		assertNull("first element should be null", arrayCollection.getArray()[0]);
	}

	@Test(timeout = 1000)
	public void testAdd() {
		for (Double item : doubleArray) {
			assertTrue("add should return true if addition was done", arrayCollection.add(item));
		}
		assertNotNull("getArray returned null after add", arrayCollection.getArray());
		assertArrayEquals("checking for equals of derived and initial arrays", doubleArray, arrayCollection.getArray());
	}

	@Test(timeout = 1000)
	public void testAddDuplicates() {
		assertTrue("add should return true if addition was done", arrayCollection.add(doubleArray[0]));
		assertTrue("add should return true if addition was done", arrayCollection.add(doubleArray[0]));
		Object[] arr = arrayCollection.getArray();
		assertEquals("should contains duplicated", 2, arr.length);
		int count = 0;
		for (Object item : arr) {
			if (item.equals(doubleArray[0]))
				count++;
		}
		assertEquals("should contains duplicated", 2, count);
	}

	@Test(timeout = 1000)
	public void testAddAllItself() {
		for (Double item : doubleArray) {
			arrayCollection.add(item);
		}
		try {
			arrayCollection.addAll(arrayCollection);
			fail("should throw IllegalArgumentException, if try to add " + " itself");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("should throw IllegalArgumentException, if try to add " + " itself");
		}
	}

	@Test(timeout = 1000)
	public void testAddAll() {
		arrayCollection.addAll(Arrays.asList(doubleArray));
		assertNotNull("getArray returned null after addAll", arrayCollection.getArray());
		assertArrayEquals("checking for equals of derived and initial arrays", doubleArray, arrayCollection.getArray());

		arrayCollection.addAll(Arrays.asList(doubleArray));
		assertEquals("count of item should be " + doubleArray.length * 2, doubleArray.length * 2,
				arrayCollection.getArray().length);
	}

	@Test(timeout = 1000)
	public void testClear() {
		arrayCollection.addAll(Arrays.asList(doubleArray));
		assertEquals("count of item should be " + doubleArray.length, doubleArray.length,
				arrayCollection.getArray().length);
		arrayCollection.clear();
		assertNotNull("in clear should return empty array", arrayCollection.getArray());
		assertEquals("should be empty after clear", 0, arrayCollection.getArray().length);
	}

	@Test(timeout = 1000)
	public void testIsEmpty() {
		assertTrue("should be empty at start", arrayCollection.isEmpty());
		arrayCollection.add(doubleArray[0]);
		assertFalse("should not be empty after add", arrayCollection.isEmpty());
		arrayCollection.clear();
		assertTrue("should be empty at clear", arrayCollection.isEmpty());
	}

	@Test(timeout = 1000)
	public void testSize() {
		assertEquals("after create size should be 0", 0, arrayCollection.size());
		arrayCollection.add(-doubleArray[0]);
		assertEquals("size should be 1", 1, arrayCollection.size());
		arrayCollection.addAll(Arrays.asList(doubleArray));
		assertEquals("if size of collection correspond to expected size", doubleArray.length + 1,
				arrayCollection.size());
		arrayCollection.clear();
		assertEquals("after clear size should be 0", 0, arrayCollection.size());
	}

	@Test(timeout = 1000)
	public void testContainsNull() {
		assertFalse("contains should return false for null after created", arrayCollection.contains(null));
		arrayCollection.add(null);
		assertTrue("wrong contains", arrayCollection.contains(null));
		assertEquals("size should be 1 after 1 add", 1, arrayCollection.getArray().length);
	}

	@Test(timeout = 1000)
	public void testContains() {
		arrayCollection.add(doubleArray[0]);
		assertTrue("wrong contains", arrayCollection.contains(doubleArray[0]));
		assertFalse("wrong contains", arrayCollection.contains(doubleArray[1]));
		assertEquals("size should be 1 after 1 add", 1, arrayCollection.getArray().length);
	}

	@Test(timeout = 1000)
	public void testContainsAllNullCollections() {
		try {
			arrayCollection.containsAll(null);
			fail("containsAll(null) should throw NullPointerException");
		} catch (NullPointerException e) {
		} catch (Exception e) {
			fail("containsAll(null) should throw NullPointerException");
		}
	}

	@Test(timeout = 1000)
	public void testContainsAll() {
		arrayCollection.addAll(Arrays.asList(doubleArray));
		assertTrue("wrong containsAll", arrayCollection.containsAll(Arrays.asList(doubleArray)));
		assertEquals("wrong size", doubleArray.length, arrayCollection.getArray().length);
		assertFalse("wrong containsAll for iten not in collection",
				arrayCollection.containsAll(Arrays.asList(new Double[] { -doubleArray[0] })));
	}

	@Test(timeout = 1000)
	public void testRemove() {
		arrayCollection.add(doubleArray[0]);
		assertTrue("should return true on remove", arrayCollection.remove(doubleArray[0]));
		assertFalse("contains removed element", Arrays.asList(arrayCollection.getArray()).contains(doubleArray[0]));

		arrayCollection.add(doubleArray[0]);
		arrayCollection.add(doubleArray[0]);
		assertFalse("sshould return false, if collection not changed", arrayCollection.remove(doubleArray[1]));
		assertEquals("size should not be changed", 2, arrayCollection.getArray().length);
		assertTrue("should return true on remove", arrayCollection.remove(doubleArray[0]));
		assertTrue("remove should remove only one elements",
				Arrays.asList(arrayCollection.getArray()).contains(doubleArray[0]));

	}

	@Test(timeout = 1000)
	public void testRemoveAllNullCollections() {
		try {
			arrayCollection.removeAll(null);
			fail("removeAll(null) should throw NullPointerException");
		} catch (NullPointerException e) {
		} catch (Exception e) {
			fail("removeAll(null) should throw NullPointerException");
		}
	}

	@Test(timeout = 1000)
	public void testRemoveAll() {
		arrayCollection.add(-doubleArray[0]);
		arrayCollection.addAll(Arrays.asList(doubleArray));

		assertFalse("should return false, if collection not changed",
				arrayCollection.removeAll(Arrays.asList(new Double[] { -doubleArray[1] })));

		assertTrue("should return true if coll changed", arrayCollection.removeAll(Arrays.asList(doubleArray)));
		assertFalse("should be not empty", arrayCollection.isEmpty());
		assertTrue("should contain one elem", Arrays.asList(arrayCollection.getArray()).contains(-doubleArray[0]));
		assertEquals("should contain one elem", 1, arrayCollection.getArray().length);
	}

	@Test(timeout = 1000)
	public void testRetainAllNullCollections() {
		try {
			arrayCollection.retainAll(null);
			fail("retainAll(null) should throw NullPointerException");
		} catch (NullPointerException e) {
		} catch (Exception e) {
			fail("retainAll(null) should throw NullPointerException");
		}
	}

	@Test(timeout = 1000)
	public void testRetainAll() {
		arrayCollection.add(-doubleArray[0]);
		arrayCollection.add(doubleArray[0]);
		arrayCollection.add(doubleArray[2]);
		assertFalse("should return false, if collection not changed", arrayCollection
				.retainAll(Arrays.asList(new Double[] { -doubleArray[0], doubleArray[0], doubleArray[2] })));
		assertTrue("should return true, if collection changed", arrayCollection.retainAll(Arrays.asList(doubleArray)));
		assertFalse("should contain only ratain elem", arrayCollection.contains(-doubleArray[0]));
		assertTrue("should contain only ratain elem",
				Arrays.asList(arrayCollection.getArray()).contains(doubleArray[0]));
		assertTrue("should contain only ratain elem",
				Arrays.asList(arrayCollection.getArray()).contains(doubleArray[2]));
	}

	@Test(timeout = 1000)
	public void testToArray() {
		arrayCollection.addAll(Arrays.asList(doubleArray));
		assertArrayEquals("added array and to array should be equals", doubleArray, arrayCollection.toArray());
	}

	@Test(timeout = 1000)
	public void testToArrayObjectArray() {
		arrayCollection.addAll(Arrays.asList(doubleArray));
		assertArrayEquals("added array and to array should be equals", doubleArray,
				arrayCollection.toArray(new Double[0]));
	}

}
