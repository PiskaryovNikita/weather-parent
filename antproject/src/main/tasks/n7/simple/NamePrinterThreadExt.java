package main.tasks.n7.simple;

import java.io.PrintStream;

import interfaces.task7.simple.NamePrinterThread;

public class NamePrinterThreadExt extends NamePrinterThread {
	private int count;
	private long interval;
	private String printName;
	private PrintStream printStream;

	@Override
	public void run() {
		for (int i = 0; i < count; i++) {
			printStream.println(printName);
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setCount(int c) {
		if (c <= 0) {
			throw new IllegalArgumentException();
		}
		this.count = c;
	}

	@Override
	public void setInterval(long interval) {
		if (interval <= 0) {
			throw new IllegalArgumentException();
		}
		this.interval = interval;
	}

	@Override
	public void setPrintName(String pn) {
		if (pn == null) {
			throw new NullPointerException();
		} else if (pn.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.printName = pn;
	}

	@Override
	public void setStream(PrintStream stream) {
		if (stream == null) {
			throw new NullPointerException();
		}
		this.printStream = stream;
	}
}
