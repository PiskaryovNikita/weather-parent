package main.tasks.n4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import interfaces.task4.CollectionUtils;

public class CollectionUtilsImpl implements CollectionUtils {

	@Override
	public Collection<Integer> difference(final Collection<Integer> c1,
			final Collection<Integer> c2) {
		if (c1 == null || c2 == null) {
			throw new NullPointerException();
		}
		List<Integer> list = new ArrayList<Integer>();
		out: for (Integer i1 : c1) {
			for (Integer i2 : c2) {
				if (i1.equals(i2)) {
					continue out;
				}
			}
			list.add(i1);
		}
		return list;
	}

	@Override
	public List<Integer> intersection(final Collection<Integer> c1,
			final Collection<Integer> c2) {
		if (c1 == null || c2 == null) {
			throw new NullPointerException();
		}
		List<Integer> list = new LinkedList<>();
		for (Integer i1 : c1) {
			for (Integer i2 : c2) {
				if (i1.equals(i2)) {
					list.add(i1);
					list.add(i2);
				}
			}
		}
		return list;
	}

	@Override
	public Set<Integer> intersectionWithoutDuplicate(
			final Collection<Integer> c1,
			final Collection<Integer> c2) {
		if (c1 == null || c2 == null) {
			throw new NullPointerException();
		}
		Set<Integer> set = new TreeSet<>();
		for (Integer i1 : c1) {
			for (Integer i2 : c2) {
				if (i1.equals(i2)) {
					set.add(i1);
				}
			}
		}
		return set;
	}

	@Override
	public List<Integer> union(final Collection<Integer> c1,
			final Collection<Integer> c2) {
		if (c1 == null || c2 == null) {
			throw new NullPointerException();
		}
		List<Integer> list = new ArrayList<>();
		list.addAll(c1);
		list.addAll(c2);
		return list;
	}

	public static void main(final String[] args) {
		CollectionUtils utils = new CollectionUtilsImpl();
		System.out.println(utils.intersection(Arrays.asList(1, 2),
				Arrays.asList(1, 2)));
	}
}

