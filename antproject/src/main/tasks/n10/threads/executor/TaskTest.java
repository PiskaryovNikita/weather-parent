package main.tasks.n10.threads.executor;

import static junit.framework.Assert.assertEquals;

import interfaces.task7.executor.Task;
import main.tasks.n7.executor.TaskImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TaskTest {
	private static Class<?> testingClass = TaskImpl.class;
	private Task task;

	@Before
	public void before() throws Exception {
		task = (Task) testingClass.newInstance();
	}

	@After
	public void after() {
	}

	@Test
	public void testTryCount() throws Exception {
		assertEquals("tryCount should be 0 after create", 0, task.getTryCount());
		task.incTryCount();
		assertEquals("tryCount should be 1 after 1 incTryCount", 1, task.getTryCount());
	}
}
