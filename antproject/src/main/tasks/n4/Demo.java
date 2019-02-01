package main.tasks.n4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class Demo {
    
    private Demo(){}

    public static void main(final String[] args) {
        try {
            final int intbound = 10;
            CollectionUtilsImpl utilsImpl = new CollectionUtilsImpl();
            List<Integer> list1 = new ArrayList<Integer>();
            List<Integer> list2 = new ArrayList<Integer>();
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                list1.add(random.nextInt(intbound));
                list2.add(random.nextInt(intbound));
            }
            System.out.println("src list1:" + list1);
            System.out.println("src list2:" + list2);
            System.out.println(utilsImpl.union(list1, list2));
            System.out.println(utilsImpl.intersection(list1, list2));
            System.out.println(utilsImpl.intersectionWithoutDuplicate(list1, list2));
            System.out.println(utilsImpl.difference(list1, list2));
            Map<String, Integer> map = new MapUtilsImpl()
                    .findThrees("1234 345_23");
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                System.out.println(entry);
            }
        } catch (NullPointerException exc) {
            exc.printStackTrace(System.err);
        }
    }

}
