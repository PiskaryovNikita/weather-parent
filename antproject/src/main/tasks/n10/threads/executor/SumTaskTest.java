package main.tasks.n10.threads.executor;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import interfaces.task7.executor.SumTask;
import main.tasks.n7.executor.SumTaskImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SumTaskTest {
	private static Class<?> testingClass = SumTaskImpl.class;

	private SumTask sumTask;

	@Before
	public void before() throws Exception {
		sumTask = (SumTask) testingClass.newInstance();
	}

	@After
	public void after() {
	}

	@Test
	public void testSetSourceNull() throws Exception {
		try {
			sumTask.setMax(0);
			fail("max should be tested for wrong value");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("source should be tested for");
		}
	}

	@Test // (timeout = 10000)
	public void testSumExecuteZero() throws Exception {
		sumTask.setCount(0);
		sumTask.setMax(1);
		assertTrue("summ for 0 count should return true", sumTask.execute());
		assertTrue("getResult should return 0 after execute", sumTask.getResult().longValue() == 0);
	}

	@Test // (timeout = 10000)
	public void testSumExecute10() throws Exception {
		sumTask.setCount(1);
		sumTask.setMax(10);
		assertTrue("execute should return true", sumTask.execute());
		assertTrue("for 1 count and max == 10 should be 0<=result<=10", sumTask.getResult().longValue() >= 0);
		assertTrue("for 1 count and max == 10 should be 0<=result<=10", sumTask.getResult().longValue() <= 10);
	}

	@Test(timeout = 10000)
	public void testSumExecute() throws Exception {
		sumTask.setCount(100);
		sumTask.setMax(100);
		assertTrue("execute should return true", sumTask.execute());
		assertTrue("for 100 count and max == 100 should be 0<result<10000", sumTask.getResult().longValue() > 0);
		assertTrue("for 100 count and max == 100 should be 0<result<10000", sumTask.getResult().longValue() < 10000);
	}

	@Test(timeout = 10000)
	public void testGetRandom10() throws Exception {
		sumTask.setMax(10);
		assertTrue("for max == 10 should be 0<=getRandom<=10", sumTask.getRandom().longValue() >= 0);
		assertTrue("for max == 10 should be 0<=getRandom<=10", sumTask.getRandom().longValue() <= 10);
	}

	@Test(timeout = 10000)
	public void testGetRandomDiff() throws Exception {
		sumTask.setMax(1000);
		assertFalse("two consistent values should be different", sumTask.getRandom() == sumTask.getRandom());
	}

	@Test(timeout = 10000)
	public void testGetRandomMax() throws Exception {
		sumTask.setMax(Long.MAX_VALUE);
		assertTrue("for max == Long.MAX_VALUE should be " + "0<=getRandom<=Long.MAX_VALUE",
				sumTask.getRandom().longValue() >= 0);
		assertTrue("for max == Long.MAX_VALUE should be " + "0<=getRandom<=Long.MAX_VALUE",
				sumTask.getRandom().longValue() < Long.MAX_VALUE);
	}
}
