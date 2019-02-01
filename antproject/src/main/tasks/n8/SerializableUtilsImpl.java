package main.tasks.n8;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import interfaces.task8.SerializableUtils;

public class SerializableUtilsImpl implements SerializableUtils {

	@Override
	public Object deserialize(InputStream in) {
		if (in == null) {
			throw new NullPointerException();
		}
		try {
			return new ObjectInputStream(in).readObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void serialize(OutputStream out, Object obj) {
		if (obj == null || out == null) {
			throw new NullPointerException();
		}
		try {
			new ObjectOutputStream(out).writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
