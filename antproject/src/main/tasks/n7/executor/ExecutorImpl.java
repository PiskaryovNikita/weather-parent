package main.tasks.n7.executor;

import interfaces.task7.executor.Executor;
import interfaces.task7.executor.Task;
import interfaces.task7.executor.TasksStorage;

public class ExecutorImpl implements Executor, Runnable {
	private TasksStorage storage;
	private Thread thread;
	private boolean isRunning;

	public ExecutorImpl() {
	}

	public ExecutorImpl(TasksStorage ts) {
		this.storage = ts;
	}

	@Override
	public TasksStorage getStorage() {
		return storage;
	}

	@Override
	public void setStorage(TasksStorage ts) {
		if (ts == null) {
			throw new NullPointerException();
		}
		this.storage = ts;
	}

	@Override
	public void run() {
		try {
			// while((task = storage.get()) != null), but mb add task.
			while (true) {
				Thread.sleep(100);
				Task task = storage.get();
				if (task == null) {
					continue;
				}
				try {
					if (!task.execute()) {
						task.incTryCount();
						if (task.getTryCount() < 5) {
							storage.add(task);
						}
					}
				} catch (Exception e) {
					task.incTryCount();
					if (task.getTryCount() < 5) {
						storage.add(task);
					}
				}
			}
		} catch (Exception e) {
			// end whiletrue by interrupt
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		if (isRunning) {
			throw new IllegalStateException();
		}
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}

	@Override
	public void stop() {
		if (!isRunning) {
			throw new IllegalStateException();
		}
		thread.interrupt();
		isRunning = false;
	}

	public Thread getThread() {
		return thread;
	}

}
