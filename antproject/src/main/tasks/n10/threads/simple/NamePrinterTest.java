package main.tasks.n10.threads.simple;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import interfaces.task7.simple.NamePrinter;
import interfaces.task7.simple.NamePrinterRunnable;
import interfaces.task7.simple.NamePrinterThread;
import main.tasks.n7.simple.NamePrinterThreadExt;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NamePrinterTest {
	private static Class<?> testingClass = NamePrinterThreadExt.class;

	private static long PRINT_INTERVAL = 100;
	private static int PRINT_COUNT = 10;
	private static String PRINT_NAME = "testName";

	private NamePrinter printer;
	private Thread thread;

	@Before
	public void before() throws Exception {
		printer = (NamePrinter) testingClass.newInstance();
		if (printer instanceof NamePrinterRunnable) {
			thread = new Thread((NamePrinterRunnable) printer);
		} else if (printer instanceof NamePrinterThread) {
			thread = (NamePrinterThread) printer;
		} else {
			fail("class is NamePrinter but it is neither NamePrinterRunnable " + "nor NamePrinterThread");
		}
	}

	@After
	public void after() {
	}

	@Test
	public void testSetPrintNameNull() throws Exception {
		try {
			printer.setPrintName(null);
			fail("name should be tested for null");
		} catch (NullPointerException e) {
		} catch (Exception e) {
			fail("name should be tested for null");
		}
	}

	@Test
	public void testSetPrintNameClear() throws Exception {
		try {
			printer.setPrintName("");
			fail("name should be tested for ''");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("name should be tested for ''");
		}
	}

	@Test
	public void testSetStreamNull() throws Exception {
		try {
			printer.setStream(null);
			fail("stream should be tested for null");
		} catch (NullPointerException e) {
		} catch (Exception e) {
			fail("stream should be tested for null");
		}
	}

	@Test
	public void testSetIntervalWrong() throws Exception {
		try {
			printer.setInterval(0);
			fail("ms should be tested for wrong value");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("ms should be tested for wrong value");
		}
	}

	@Test
	public void testSetCountWrong() throws Exception {
		try {
			printer.setCount(0);
			fail("count should be tested for wrong value");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("count should be tested for wrong value");
		}
	}

	@Test(timeout = 10000)
	public void testStart() throws Exception {
		if (thread.isAlive()) {
			interruptThread(thread);
			fail("thread is alive before start");
		}

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		PrintStream stream = new PrintStream(byteStream);

		try {
			printer.setPrintName(PRINT_NAME);
			printer.setInterval(PRINT_INTERVAL);
			printer.setCount(PRINT_COUNT);
			printer.setStream(stream);

			thread.start();
			thread.join(PRINT_INTERVAL * (PRINT_COUNT + 1));

			if (thread.isAlive()) {
				interruptThread(thread);
				fail("thread is alive very long time");
			}

			checkPrintedName(byteStream.toByteArray());
		} finally {
			stream.close();
		}
	}

	@Test(timeout = 10000)
	public void testRun() throws Exception {
		if (thread.isAlive()) {
			interruptThread(thread);
			fail("thread is alive before start");
		}

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		PrintStream stream = new PrintStream(byteStream);

		try {
			printer.setPrintName(PRINT_NAME);
			printer.setInterval(PRINT_INTERVAL);
			printer.setCount(PRINT_COUNT);
			printer.setStream(stream);

			long startTime = System.currentTimeMillis();
			thread.run();
			long runTime = System.currentTimeMillis() - startTime;
			assertTrue("time of run is short", PRINT_INTERVAL * (PRINT_COUNT - 1) <= runTime);
			assertTrue("time of run is long", PRINT_INTERVAL * (PRINT_COUNT + 1) >= runTime);

			checkPrintedName(byteStream.toByteArray());
		} finally {
			stream.close();
		}
	}

	private void checkPrintedName(byte[] buffer) {
		assertTrue("output stream is empty", buffer.length != 0);

		StringBuffer stringBuffer = new StringBuffer(new String(buffer));
		int count = 0;
		int index = -1;
		while ((index = stringBuffer.indexOf(PRINT_NAME, index + 1)) >= 0) {
			count++;
		}
		assertEquals("wrong count of names in output stream", PRINT_COUNT, count);
	}

	@SuppressWarnings("deprecation")
	private void interruptThread(Thread thread) throws Exception {
		long start = System.currentTimeMillis();
		do {
			thread.interrupt();
			Thread.sleep(1);
			if ((PRINT_INTERVAL * PRINT_COUNT * 2) + start < System.currentTimeMillis()) {
				thread.stop();
				Thread.sleep(10);
			}
		} while (thread.isAlive());
	}
}
