package main.tasks.n10.threads.executor;

import static junit.framework.Assert.assertNotNull;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import interfaces.task7.executor.Executor;
import interfaces.task7.executor.Task;
import interfaces.task7.executor.TasksStorage;
import main.tasks.n7.executor.ExecutorImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockobjects.dynamic.Mock;

public class ExecutorTest {
	private static Class<?> testingClass = ExecutorImpl.class;

	private Mock tasksStorageMock;
	private TasksStorage storage;
	private Mock taskMock;
	private Task task;
	private Executor executor;

	@Before
	public void before() throws Exception {
		executor = (Executor) testingClass.newInstance();
		tasksStorageMock = new Mock(TasksStorage.class);
		taskMock = new Mock(Task.class);
		storage = (TasksStorage) tasksStorageMock.proxy();
		task = (Task) taskMock.proxy();
	}

	@After
	public void after() {
	}

	@Test
	public void testTasksStorage() throws Exception {
		assertNull("storage should be null after create", executor.getStorage());
		executor.setStorage(storage);
		assertNotNull("storage should not be null after set", executor.getStorage());
		assertSame("storage should not be same after set", storage, executor.getStorage());
	}

	@Test(timeout = 10000)
	public void testExecutorNullStorage() throws Exception {
		try {
			executor.setStorage(null);
			executor.start();
			fail("storage should be tested for null");
		} catch (NullPointerException e) {
		} catch (Exception e) {
			fail("storage should be tested for null");
		}
	}

	@Test(timeout = 10000)
	public void testExecutorStartTwice() throws Exception {
		try {
			tasksStorageMock.matchAndReturn("get", null);
			tasksStorageMock.matchAndReturn("count", 0);

			executor.setStorage(storage);
			executor.start();
			executor.start();
			fail("if call start twice without stop should throw IllegalStateException");
		} catch (IllegalStateException e) {
		} catch (Exception e) {
			fail("if call start twice without stop should throw IllegalStateException");
		}
		executor.stop();
	}

	@Test(timeout = 10000)
	public void testExecutorStopBeforeStart() throws Exception {
		try {
			executor.setStorage(storage);
			executor.stop();
			fail("if call stop without start should throw IllegalStateException");
		} catch (IllegalStateException e) {
		} catch (Exception e) {
			fail("if call stop without start should throw IllegalStateException");
		}
	}

	@Test(timeout = 10000)
	public void testExecutorGood() throws Exception {
		taskMock.expectAndReturn("execute", true);
		taskMock.matchAndReturn("incTryCount", null);
		taskMock.matchAndReturn("toString", "taskMock");
		taskMock.matchAndReturn("getTryCount", 1);

		tasksStorageMock.expectAndReturn("get", task);
		tasksStorageMock.matchAndReturn("get", null);
		tasksStorageMock.matchAndReturn("count", 1);

		executor.setStorage(storage);

		int threadCount = Thread.currentThread().getThreadGroup().activeCount();

		executor.start();
		Thread.sleep(50);
		assertTrue("Executor have not started new thread",
				threadCount < Thread.currentThread().getThreadGroup().activeCount());

		Thread.sleep(3000);
		executor.stop();

		taskMock.verify();
		tasksStorageMock.verify();
	}

	@Test(timeout = 10000)
	public void testExecutorBad() throws Exception {
		taskMock.matchAndReturn("toString", "taskMock");
		taskMock.matchAndReturn("execute", false);
		taskMock.expectAndReturn("getTryCount", 0);
		taskMock.expectAndReturn("getTryCount", 5);
		taskMock.matchAndReturn("getTryCount", 5);
		taskMock.matchAndReturn("incTryCount", null);

		tasksStorageMock.expectAndReturn("get", task);
		tasksStorageMock.expectAndReturn("add", task, null);
		tasksStorageMock.expectAndReturn("get", task);
		tasksStorageMock.matchAndReturn("get", null);
		tasksStorageMock.matchAndReturn("count", 1);

		executor.setStorage(storage);
		int threadCount = Thread.currentThread().getThreadGroup().activeCount();

		executor.start();
		Thread.sleep(50);
		assertTrue("Executor have not started new thread",
				threadCount < Thread.currentThread().getThreadGroup().activeCount());

		Thread.sleep(3000);
		executor.stop();

		taskMock.verify();
		tasksStorageMock.verify();
	}

	@Test(timeout = 10000)
	public void testExecutorBadException() throws Exception {
		taskMock.matchAndReturn("toString", "taskMock");
		taskMock.matchAndThrow("execute", new Exception("test Exception"));
		taskMock.expectAndReturn("getTryCount", 0);
		taskMock.expectAndReturn("getTryCount", 5);
		taskMock.matchAndReturn("getTryCount", 5);
		taskMock.matchAndReturn("incTryCount", null);

		tasksStorageMock.expectAndReturn("get", task);
		tasksStorageMock.expectAndReturn("add", task, null);
		tasksStorageMock.expectAndReturn("get", task);
		tasksStorageMock.matchAndReturn("get", null);
		tasksStorageMock.matchAndReturn("count", 1);

		executor.setStorage(storage);
		executor.start();
		Thread.sleep(3000);
		executor.stop();

		try {
			taskMock.verify();
		} catch (Throwable e) {
			throw new Exception("you should catch exceptions from task.execute and return " + "task to storage if need",
					e);
		}
		tasksStorageMock.verify();
	}
}
