/**
 *
 */
package main.tasks.n1.figures;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author piskaryov
 */
public final class Demo {
    /**
     * utility class shouldn't be instantiated, so the constructor is private.
     */
    private Demo() { }
    /**
     * @param args parameters invoked function
     */
    public static void main(final String[] args) {
        final int minlength = 10;
        Figure[] figures = new Figure[minlength];
        Map<Integer, String> map = new TreeMap<Integer, String>();
        for (int i = 0, j = 1; i < figures.length; i++) {
            int random = (int) (Math.random() * 2);
            if (random == 0) {
                figures[i] = new Triangle(i, i, i, i + i, i + i, i + i);
            } else {
                figures[i] = new Circle(new Point(i, i), i * i + 1);
            }
            map.put(j++, figures[i].getClass().getSimpleName());
            System.out.print(i + 1 + " - ");
            figures[i].print();
        }
        System.out.println();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }

}
