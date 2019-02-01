package main.tasks.n6;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import interfaces.task6.IOUtils;

public class IOUtilsImpl implements IOUtils {

	// 6 nonbuff
	@Override
	public void copyFile(String filename1, String filename2) {
		File file1 = new File(filename1);
		File file2 = new File(filename2);
		if (file1 == null || file2 == null) {
			throw new NullPointerException();
		}

		try (FileInputStream fin = new FileInputStream(filename1);
				FileOutputStream fout = new FileOutputStream(filename2)) {
			int i;
			do {
				i = fin.read();
				if (i != -1) {
					fout.write(i);
				}
			} while (i != -1);
		} catch (IOException exc) {
			exc.printStackTrace();
			throw new IllegalArgumentException(exc);
		}
	}

	// 6 nio
	@Override
	public void copyFileBest(String filename1, String filename2) {
		File file = new File(filename1);
		File oFile = new File(filename2);
		if (file == null || oFile == null) {
			throw new NullPointerException();
		}
		try {
			FileInputStream is = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(oFile);
			FileChannel f = is.getChannel();
			FileChannel f2 = fos.getChannel();

			ByteBuffer buf = ByteBuffer.allocateDirect(64 * 1024);
			while (f.read(buf) != -1) {
				buf.flip();
				f2.write(buf);
				buf.clear();
			}
			is.close();
			fos.close();
			f2.close();
			f.close();
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new IllegalArgumentException(exc);
		}
	}

	// 6 buff // new File() - doesn't create file into filesystem
	@Override
	public void copyFileBuffered(File file1, File file2) {
		if (file1 == null || file2 == null) {
			throw new NullPointerException();
		}
		try (BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file1));
				BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(file2))) {
			int i;
			do {
				i = fin.read();
				if (i != -1) {
					fout.write(i);
				}
			} while (i != -1);
		} catch (IOException exc) {
			exc.printStackTrace();
			throw new IllegalArgumentException(exc);
		}
	}

	// 4
	@Override
	public String[] findFiles(String dir) {
		File file = new File(dir);
		if (dir == null) {
			throw new NullPointerException();
		} else if (!file.exists()) {
			throw new IllegalArgumentException("No such directory!");
		}

		if (file.isDirectory()) {
			String[] list = file.list();
			List<String[]> listOfArrays = new ArrayList<>();
			int arrlen = 0;
			for (int i = 0; i < list.length; i++) {
				String[] returned = findFiles(dir + "/" + list[i]);
				arrlen += returned.length;
				listOfArrays.add(returned);
			}
			String[] res = new String[arrlen];
			int k = 0;// pointer in the resarr
			for (String[] strings : listOfArrays) {
				for (String s : strings) {
					res[k++] = s;
				}
			}
			return res;
		} else {
			return new String[] { file.toString() };
		}
	}

	// 5
	@Override
	public String[] findFiles(String dir, String ext) {
		File file = new File(dir);
		if (dir == null) {
			throw new NullPointerException();
		} else if (!file.exists()) {
			throw new IllegalArgumentException("No such directory!");
		}
		if (file.isDirectory()) {
			String[] list = file.list();
			List<String[]> listOfArrays = new ArrayList<>();
			int arrlen = 0;
			for (int i = 0; i < list.length; i++) {
				String[] returned = findFiles(dir + "/" + list[i], ext);
				arrlen += returned.length;
				listOfArrays.add(returned);
			}
			String[] res = new String[arrlen];
			int k = 0;// pointer in the resarr
			for (String[] strings : listOfArrays) {
				for (String s : strings) {
					res[k++] = s;
				}
			}
			return res;
		} else {
			if (ext == null) {
				ext = "";
			}
			if (file.toString().endsWith(ext)) {
				return new String[] { file.getAbsolutePath() };
			} else {
				return new String[] {};
			}
		}

	}

	private String copyIn(Reader in) {
		try {
			int intch;
			String s = "";
			while ((intch = in.read()) != -1) {
				s += (char) intch;
			}
			return s;
		} catch (Exception e) {
			return null;
		}
	}

	// 3
	@Override
	public void replaceChars(Reader in, Writer out, String inChars, String outChars) {
		if (in == null || out == null) {
			throw new NullPointerException();
		} else if (inChars == null) {
			try {
				out.write(copyIn(in));
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (inChars.length() != outChars.length()) {
			throw new IllegalArgumentException();
		}
		Map<Character, Character> map = new TreeMap<Character, Character>();
		for (int i = 0; i < inChars.length(); i++) {
			map.put(inChars.charAt(i), outChars.charAt(i));
		}
		Set<Character> keyset = map.keySet();
		int intch;
		String readed = "";
		try {
			while ((intch = in.read()) != -1) {
				Character ch = (char) intch;
				if (keyset.contains(ch)) {
					ch = map.get(ch);
				}
				readed += ch;
			}
			out.write(readed);
		} catch (IOException exc) {
			exc.printStackTrace(System.err);
		}
	}

	public void test() {
		// 839 = bytestreams
		// 3 = nio NIO works way faster if the length of file is huge.
		// 271913 = bufferedstreams
		// list of files include directory
		try (Reader in = new FileReader(new File("./src/tasks/n6/read.txt"));
				Writer out = new FileWriter(new File("./src/tasks/n6/output.txt"))) {
			IOUtils utils = new IOUtilsImpl();
			utils.replaceChars(in, out, "357", "480");
			//
			long l1 = System.currentTimeMillis();
			utils.copyFileBest("./src/tasks/n6/src.txt", "./src/tasks/n6/dest.txt");
			long l2 = System.currentTimeMillis();
			System.out.println(l2 - l1);
		} catch (IOException | IllegalArgumentException exc) {
			exc.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			String[] s = new IOUtilsImpl().findFiles("./src/tasks", "java");
			System.out.println(Arrays.toString(s));
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
