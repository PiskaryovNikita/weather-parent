package main.tasks.n10.serialization;

import interfaces.task8.PathClassLoader;

import main.tasks.n8.PathClassLoaderImpl;

import static junit.framework.Assert.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

/**
 * @author yura
 */
public class PathClassLoaderTest {
	private static Class<?> testingClass = PathClassLoaderImpl.class;

	private static String className = "TestClass";
	private static String className2 = "TestClass2";
	private static String destinationDir;
	private static String destinationDir2;

	private static String filePath;
	private static String filePath2;

	private PathClassLoader loader;
	private ClassLoader classLoader;

	@BeforeClass
	public static void beforeClass() throws Exception {
		destinationDir = System.getProperty("java.io.tmpdir");
		filePath2 = destinationDir2 = System.getProperty("user.home");
	}

	@AfterClass
	public static void afterClass() {
		new File(filePath).delete();
		new File(filePath2).delete();
	}

	@Before
	public void before() throws Exception {
		loader = (PathClassLoader) testingClass.newInstance();
		classLoader = (ClassLoader) loader;
	}

	@Test(timeout = 1000)
	public void testSetPathNull() throws Exception {
		try {
			loader.setPath(null);
			fail("path should be tested for null");
		} catch (NullPointerException ex) {
		} catch (Exception ex) {
			fail("path should be tested for null");
		}
	}

	@Test(timeout = 1000)
	public void testSetPath() throws Exception {
		loader.setPath(destinationDir);
		assertEquals("getPath not equals setPath", new File(destinationDir), new File(loader.getPath()));

		loader.setPath(destinationDir2);
		assertEquals("getPath not equals setPath", new File(destinationDir2), new File(loader.getPath()));
	}

	@Test(timeout = 1000)
	public void testLoadClass() throws Exception {
		loader.setPath(destinationDir);

		Class<?> clazz = null;

		try {
			clazz = classLoader.loadClass(className);
		} catch (Exception e) {
			fail(e.getMessage() + " path=\"" + destinationDir + "\"" + "className=\"" + className + "\""
					+ " maybe you should test path for last path separator");
		}
		System.out.println("clazz.getName() " + clazz.getName());
		System.out.println("className " + className);
		assertTrue("Class name not equal", className.equals(clazz.getName()));

		classLoader.loadClass("java.lang.String");

		loader.setPath(destinationDir2);
		assertEquals("getPath not equals setPath", new File(destinationDir2), new File(loader.getPath()));

		try {
			clazz = classLoader.loadClass(className2);
		} catch (Exception e) {
			fail(e.getMessage() + " path=\"" + destinationDir2 + "\"" + "className=\"" + className2 + "\""
					+ " maybe you should test path for last path separator");
		}

		assertTrue("Class dosen't loaded after change path first", className2.equals(clazz.getName()));
	}

	@Test(timeout = 1000)
	public void testGetClassWrongPath() throws Exception {
		try {
			loader.setPath("Abrakadabra");
			((ClassLoader) loader).loadClass(className);
			fail("Try to load in not existing directory");
		} catch (Exception ex) {
		}
	}

	@Test(timeout = 1000)
	public void testClassNotFoundException() throws Exception {
		((ClassLoader) loader).loadClass("java.lang.String");

		try {
			loader.setPath(destinationDir);
			((ClassLoader) loader).loadClass("java.lang.String12");
			fail("ClassNotFoundException must be thrown");
		} catch (ClassNotFoundException ex) {
		} catch (Exception ex) {
			fail("ClassNotFoundException must be thrown");
		}
	}
}
