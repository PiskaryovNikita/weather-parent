package main.tasks.n7.executor;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import interfaces.task7.executor.Task;
import interfaces.task7.executor.TasksStorage;

public class TaskStorageImpl implements TasksStorage {
	private Queue<Task> storage = new LinkedBlockingQueue<>();

	@Override
	public void add(Task task) {
		if (task == null) {
			throw new NullPointerException();
		}
		storage.add(task);
	}

	@Override
	public int count() {
		return storage.size();
	}

	@Override
	public Task get() {
		// poll - null, if empty, get - exc
		return storage.poll();
	}
	
	public Queue<Task> getStorage() {
		return storage;
	}

}
