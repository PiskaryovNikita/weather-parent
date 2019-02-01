package main.tasks.n10.threads.executor;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import interfaces.task7.executor.CopyTask;
import main.tasks.n7.executor.CopyTaskImpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CopyTaskTest {
	private static Class<?> testingClass = CopyTaskImpl.class;

	private static String sourceFileName = "source.dat";
	private static String destFileName = "dest.dat";
	private static String sourseFilePath;
	private static String destFilePath;
	private static long SOURCE_SIZE_KB = 1000;

	@BeforeClass
	public static void beforeClass() throws Exception {
		sourseFilePath = System.getProperty("java.io.tmpdir") + File.separator + sourceFileName;
		destFilePath = System.getProperty("java.io.tmpdir") + File.separator + destFileName;

		OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(sourseFilePath));
		Random random = new Random();
		byte[] buffer = new byte[1024];
		for (int i = 0; i < SOURCE_SIZE_KB; i++) {
			random.nextBytes(buffer);
			outputStream.write(buffer);
		}
		outputStream.write(123);
		outputStream.close();
	}

	@AfterClass
	public static void afterClass() {
		new File(sourseFilePath).delete();
	}

	private CopyTask copyTask;

	@Before
	public void before() throws Exception {
		copyTask = (CopyTask) testingClass.newInstance();
	}

	@After
	public void after() {
		new File(destFilePath).delete();
	}

	@Test
	public void testSetSourceNull() throws Exception {
		try {
			copyTask.setSource(null);
			fail("source should be tested for null");
		} catch (NullPointerException e) {
		} catch (Exception e) {
			fail("source should be tested for null");
		}
	}

	@Test
	public void testSetDestNull() throws Exception {
		try {
			copyTask.setDest(null);
			fail("dest should be tested for null");
		} catch (NullPointerException e) {
		} catch (Exception e) {
			fail("dest should be tested for null");
		}
	}

	@Test
	public void testSetSourceWrong() throws Exception {
		try {
			copyTask.setSource(System.getProperty("java.io.tmpdir"));
			fail("source should be tested for wrong value");
		} catch (IllegalArgumentException e) {
			assertNotNull("cause should be in IllegalArgumentException", e.getCause());
		} catch (Exception e) {
			fail("source should be tested for wrong value");
		}
	}

	@Test(timeout = 60000)
	public void testCopy() throws Exception {
		copyTask.setSource(sourseFilePath);
		copyTask.setDest(destFilePath);
		copyTask.execute();
		compareFiles();
	}

	private void compareFiles() throws Exception {
		InputStream inSourse = null;
		InputStream inDest = null;
		byte[] bufferSource = new byte[100000];
		byte[] bufferDest = bufferSource.clone();
		try {
			inSourse = new FileInputStream(sourseFilePath);
			inDest = new FileInputStream(destFilePath);
			assertEquals("wrong dest length", inSourse.available(), inDest.available());

			int count;
			while ((count = inSourse.read(bufferSource)) != -1) {
				assertEquals("wrong dest length", count, inDest.read(bufferDest));
				assertTrue("files are different", Arrays.equals(bufferSource, bufferDest));
			}
		} finally {
			if (null != inSourse)
				inSourse.close();
			if (null != inDest)
				inDest.close();
		}
	}

}
