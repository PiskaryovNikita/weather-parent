package main.tasks.n7.executor;

import interfaces.task7.executor.Task;

public class TaskImpl implements Task {
	private int tryCount;

	@Override
	public boolean execute() throws Exception {
		return false;
	}

	@Override
	public int getTryCount() {
		return tryCount;
	}

	@Override
	public void incTryCount() {
		tryCount++;
	}

}
