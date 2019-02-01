package main.tasks.n7.executor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import interfaces.task7.executor.CopyTask;

public class CopyTaskImpl extends TaskImpl implements CopyTask {
	private InputStream src;
	private OutputStream dest;

	public CopyTaskImpl() {
	}

	public CopyTaskImpl(String src, String dest) {
		setSource(src);
		setDest(dest);
	}

	@Override
	public boolean execute() throws Exception {
		try {
			int intch;
			while ((intch = src.read()) != -1) {
				dest.write(intch);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			src.close();
			dest.close();
		}
	}

	@Override
	public void setDest(String d) {
		if (d == null) {
			throw new NullPointerException();
		}
		try {
			dest = new FileOutputStream(d);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setSource(String s) {
		if (s == null) {
			throw new NullPointerException();
		}
		try {
			this.src = new FileInputStream(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public static void main(String[] args) {
		CopyTaskImpl copyTaskImpl = new CopyTaskImpl();
		copyTaskImpl.setSource("./src/tasks/n7/executor/src.txt");
		copyTaskImpl.setDest("./src/tasks/n7/executor/dest.txt");
		try {
			copyTaskImpl.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
