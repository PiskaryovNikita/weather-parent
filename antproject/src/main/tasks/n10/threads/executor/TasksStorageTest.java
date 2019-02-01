package main.tasks.n10.threads.executor;

import static junit.framework.Assert.assertEquals;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.fail;
import interfaces.task7.executor.Task;
import interfaces.task7.executor.TasksStorage;
import main.tasks.n7.executor.TaskStorageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockobjects.dynamic.Mock;

public class TasksStorageTest {
	private static Class<?> testingClass = TaskStorageImpl.class;

	private TasksStorage tasksStorage;
	private Task task1;
	private Task task2;

	@Before
	public void before() throws Exception {
		tasksStorage = (TasksStorage) testingClass.newInstance();
		Mock test1Mock = new Mock(Task.class);
		Mock test2Mock = new Mock(Task.class);
		test1Mock.matchAndReturn("toString", "test1Mock");
		test2Mock.matchAndReturn("toString", "test2Mock");
		task1 = (Task) test1Mock.proxy();
		task2 = (Task) test2Mock.proxy();
	}

	@After
	public void after() {
	}

	@Test(timeout = 1000)
	public void testClearStorage() throws Exception {
		assertEquals("count should be 0 after create", 0, tasksStorage.count());
		try {
			assertNull("if count == 0 get should return null", tasksStorage.get());
		} catch (Exception e) {
			fail("if count == 0 get should return null");
		}
	}

	@Test(timeout = 1000)
	public void testStorageAddNull() throws Exception {
		try {
			tasksStorage.add(null);
			fail("task should be tested for null");
		} catch (NullPointerException e) {
		} catch (Exception e) {
			fail("task should be tested for null");
		}
	}

	@Test(timeout = 1000)
	public void testStorageAddCount() throws Exception {
		tasksStorage.add(task1);
		assertEquals("after one add count should be 1", 1, tasksStorage.count());
		tasksStorage.add(task2);
		assertEquals("after 2 add count should be 1", 2, tasksStorage.count());
	}

	@Test(timeout = 1000)
	public void testStorageAddGet() throws Exception {
		tasksStorage.add(task1);
		assertEquals("after one add count should be 1", 1, tasksStorage.count());
		Task task = tasksStorage.get();
		assertNotNull("after one add get should return same object", task);
		assertSame("after one add get should return same object", task, task1);

		assertEquals("after one add and one get count should be 0", 0, tasksStorage.count());
		try {
			assertNull("if count == 0 get should return null", tasksStorage.get());
		} catch (Exception e) {
			fail("if count == 0 get should return null");
		}
	}

	@Test(timeout = 1000)
	public void testStorage2Add2Get() throws Exception {
		tasksStorage.add(task1);
		tasksStorage.add(task2);
		Task task = tasksStorage.get();
		assertNotNull("after 2 add get should return first object", task);
		assertSame("after 2 add get should return first object", task, task1);
		assertEquals("after get task should be deleted", 1, tasksStorage.count());

		task = tasksStorage.get();

		assertNotNull("after 2 add 2 get should return second object", task);
		assertSame("after 2 add 2 get should return second object", task, task2);
		assertEquals("after get task should be deleted", 0, tasksStorage.count());
	}

	@Test(timeout = 60000)
	public void testStorageSynchronized() {
		ArrayList<StorageTesterGet> listGet = new ArrayList<StorageTesterGet>();
		ArrayList<StorageTesterAdd> listAdd = new ArrayList<StorageTesterAdd>();
		ArrayList<Callable<Object>> testers = new ArrayList<Callable<Object>>();
		for (int i = 0; i < 5; i++) {
			StorageTesterAdd storageTesterAdd = new StorageTesterAdd(tasksStorage, 5000, 1);
			listAdd.add(storageTesterAdd);
			testers.add(Executors.callable(storageTesterAdd));
		}
		for (int i = 0; i < 10; i++) {
			StorageTesterGet storageTesterGet = new StorageTesterGet(tasksStorage, 1, 20000);
			listGet.add(storageTesterGet);
			testers.add(Executors.callable(storageTesterGet));
		}

		ExecutorService executorService = Executors.newScheduledThreadPool(20);
		try {
			executorService.invokeAll(testers);
			executorService.shutdown();
			executorService.awaitTermination(50000, TimeUnit.MILLISECONDS);
			if (!executorService.isTerminated()) {
				throw new Exception("storage test timed out");
			}
		} catch (Throwable e) {
			fail("your storage not synchronized. Error:" + e.toString());
		}

		int getCount = 0;
		for (StorageTesterGet testerGet : listGet) {
			getCount += testerGet.getGottenTasks().size();
		}

		assertEquals("wrong count of gotten tasks, maybe your storage " + "not synchronized", 5000 * listAdd.size(),
				getCount);
	}

	static public class StorageTesterAdd implements Runnable {

		private TasksStorage storage;
		private int count;
		private long interval;

		public StorageTesterAdd(TasksStorage storage, int count, long interval) {
			this.storage = storage;
			this.count = count;
			this.interval = interval;
		}

		public void run() {
			for (int i = 0; i < count; i++) {
				Mock taskMock = new Mock(Task.class);
				taskMock.matchAndReturn("toString", "test1Mock");
				Task task = (Task) taskMock.proxy();
				storage.add(task);
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	static public class StorageTesterGet implements Runnable {

		private TasksStorage storage;
		private long interval;
		private long lifeTime;
		private ArrayList<Task> gottenTasks = new ArrayList<Task>();

		public StorageTesterGet(TasksStorage storage, long interval, long lifeTime) {
			this.storage = storage;
			this.interval = interval;
			this.lifeTime = lifeTime;
		}

		public void run() {
			long start = System.currentTimeMillis();
			while (start + lifeTime > System.currentTimeMillis()) {
				Task task = null;
				try {
					task = storage.get();
				} catch (NoSuchElementException e) {
					task = null;
				}
				if (task != null)
					gottenTasks.add(task);
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public List<Task> getGottenTasks() {
			return gottenTasks;
		}
	}
}
