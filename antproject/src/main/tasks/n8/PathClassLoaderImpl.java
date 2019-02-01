package main.tasks.n8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import interfaces.task8.PathClassLoader;

public class PathClassLoaderImpl extends ClassLoader implements PathClassLoader {
	private String path;

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public void setPath(String path) {
		if (path == null) {
			throw new NullPointerException();
		}
		this.path = path;
	}

	@Override
	public Class<?> findClass(String className) throws ClassNotFoundException {
		try {
			byte bytes[] = fetchClassFromFS(path + "/" + className + ".class");
			return defineClass(className, bytes, 0, bytes.length);
		} catch (FileNotFoundException ex) {
			// делeгируем findClass to parents
			return super.findClass(className);
		} catch (IOException ex) {
			return super.findClass(className);
		}
	}

	private byte[] fetchClassFromFS(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File(path);
		try (InputStream in = new FileInputStream(file)) {
			long len = file.length();
			if (len > Integer.MAX_VALUE) {
				throw new IllegalArgumentException();
			}
			byte[] bytes = new byte[(int) len];
			int readed = 0;
			int offset = 0;
			while (offset < (int) len && (readed = in.read(bytes, offset, (int) len - offset)) >= 0) {
				offset += readed;
			}
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileNotFoundException();
		}
	}

	public static void main(String[] args) {
		PathClassLoaderImpl classloader = new PathClassLoaderImpl();
		classloader.setPath("./src/tasks/n8");
		try {
			Class<?> clazz = classloader.findClass("HelloWorld");
			Method method = clazz.getMethod("test");
			method.setAccessible(true);
			method.invoke(null);
			System.out.println(System.getProperty("java.io.tmpdir"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
