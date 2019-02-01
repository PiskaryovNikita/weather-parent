package main.tasks.n7.executor;

import interfaces.task7.executor.TasksStorage;

public final class Demo {
	public static void main(String[] args) {
		TasksStorage storage = new TaskStorageImpl();
		for (int i = 0; i < 5; i++) {
			storage.add(new SumTaskImpl(i + 3, 12345 + i * 123));
		}
		for (int i = 0; i < 3; i++) {
			storage.add(new CopyTaskImpl("./src/tasks/n7/executor/src" + i + ".txt",
					"./src/tasks/n7/executor/dest" + i + ".txt"));
		}
		ExecutorImpl executor1 = new ExecutorImpl(storage);
		ExecutorImpl executor2 = new ExecutorImpl(storage);
		ExecutorImpl executor3 = new ExecutorImpl(storage);
		executor1.start();
		executor2.start();
		executor3.start();
		try {
			while (storage.count() > 0) {
				Thread.sleep(300);
			}
			executor1.stop();
			executor2.stop();
			executor3.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
