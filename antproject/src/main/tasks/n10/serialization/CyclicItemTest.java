package main.tasks.n10.serialization;

import static junit.framework.Assert.assertEquals;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import interfaces.task8.CyclicItem;
import main.tasks.n8.CyclicItemImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;

/**
 * @author yura
 */
public class CyclicItemTest {
	private static Class<?> testingClass = CyclicItemImpl.class;

	private CyclicItem firstItem;
	private CyclicItem secondItem;

	@Before
	public void before() throws Exception {
		firstItem = (CyclicItem) testingClass.newInstance();
		secondItem = (CyclicItem) testingClass.newInstance();
	}

	@Test
	public void testSerializable() throws Exception {
		assertTrue(testingClass.getSimpleName() + " not support Serializable", firstItem instanceof Serializable);
	}

	@Test
	public void testGetValues() throws Exception {
		String str = "testValue";
		firstItem.setValue(str);
		assertSame("getValue return not same object that was set", str, firstItem.getValue());

		firstItem.setTemp(str);
		assertSame("getTemp return not same object that was set", str, firstItem.getTemp());
	}

	@Test
	public void testNextItem() throws Exception {
		assertSame("nextItem() must return itself for new element", firstItem, firstItem.nextItem());

		firstItem.setNextItem(secondItem);
		assertSame("nextItem return not the same object that was setted", secondItem, firstItem.nextItem());
	}

	@Test
	public void testSerialization() throws Exception {
		String value = "test value";
		String temp = "test temp";

		firstItem.setValue(value);
		firstItem.setTemp(temp);

		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		try {
			baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(firstItem);

			byte[] objectAsBytes = baos.toByteArray();

			bais = new ByteArrayInputStream(objectAsBytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			CyclicItem firstItem = (CyclicItem) ois.readObject();

			assertEquals("Value after deserialization must be equal ", value, firstItem.getValue());
			assertFalse("Temp after deserialization must be null", temp.equals(firstItem.getTemp()));
			assertNull("Temp after deserialization must not null", firstItem.getTemp());

		} catch (IOException ex) {
			ex.printStackTrace();
			fail("Error while serialization/deserialization :" + ex);
		} finally {
			if (baos != null) {
				baos.close();
			}
			if (bais != null) {
				bais.close();
			}
		}
	}
}
