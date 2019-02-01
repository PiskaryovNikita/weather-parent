package main.tasks.n4;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import interfaces.task4.MapUtils;

public class MapUtilsImpl implements MapUtils {
	private Map<String, Integer> map = new TreeMap<String, Integer>();
	private final int int3 = 3;
	private final int botnums = 48;
	private final int topnums = 57;
	private final int botuppercase = 65;
	private final int topuppercase = 90;
	private final int botlowercase = 97;
	private final int toplowercase = 122;

	@Override
	public Map<String, Integer> findThrees(String s) {
		map.clear();
		StringTokenizer stringTokenizer = new StringTokenizer(s, " _");
		if (s == null) {
			throw new NullPointerException();
		}
		while (stringTokenizer.hasMoreElements()) {
			String lexeme = (String) stringTokenizer.nextElement();
			System.out.println("lexeme: " + lexeme);
			for (int i = 0; i < lexeme.length(); i++) {
				String temp = "";
				for (int j = i; i + int3 <= lexeme.length() && j < i + int3; j++) {
					char ch = lexeme.charAt(j);
					if (ch >= botuppercase && ch <= topuppercase || ch >= botlowercase && ch <= toplowercase
							|| ch >= botnums && ch <= topnums) {
						temp += ch;
					}
				}
				if (!temp.equals("") && temp.length() == 3) {
					Integer k = map.get(temp);
					if (k == null) {
						map.put(temp, 1);
					} else {
						map.put(temp, k + 1);
					}
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		MapUtilsImpl utils = new MapUtilsImpl();
		Map<String, Integer> map = utils.findThrees("1234");
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry);
		}
		System.out.println(map.size());
		System.out.println();
		map = utils.findThrees("1234 123");
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry);
		}
		System.out.println(map.size());
		System.out.println();
		map = utils.findThrees("1234 123_234");
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry);
		}
		System.out.println(map.size());
		System.out.println();
		map = utils.findThrees("1234 123_234*12");
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry);
		}
		System.out.println(map.size());
	}
}
