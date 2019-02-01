/**
 *
 */
package main.tasks.n1.figures;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author piskaryov
 *
 */
public class Triangle extends Figure {
    /**
     * Stores the points of the triangle.
     */
    private List<Point> list;

    /**
     * @param x1,
     *            y1, x2, y2, x3, y3 specified points of the triangle which
     *            compose the triangle.
     */
    public Triangle(final int x1, final int y1, final int x2, final int y2,
            final int x3, final int y3) {
        list = new ArrayList<Point>();
        list.add(new Point(x1, y1));
        list.add(new Point(x2, y2));
        list.add(new Point(x3, y3));
    }

    /**
     * Outputs type of figure point, radius and perimeter using default output
     * stream(System.out).
     */
    @Override
    public void print() {
        String points = "";
        for (Point point : list) {
            points += "(" + point.x + ", " + point.y + "), ";
        }
        System.out.println(
                "Triangle, " + points + "perimeter: " + getPerimeter());
    }

    /**
     * Changes the cordinates of triangle's points according to direction.
     *
     * @param direction
     *            in which direction this figure ought to move.
     * @param distance
     *            how should be changed the cordinates of points
     */
    @Override
    public void move(final Directions direction, final int distance) {
        if (direction == Directions.LEFT) {
            for (Point point : list) {
                point.x -= distance;
            }
        } else if (direction == Directions.RIGTH) {
            for (Point point : list) {
                point.x += distance;
            }
        } else if (direction == Directions.DOWN) {
            for (Point point : list) {
                point.y -= distance;
            }
        } else {
            for (Point point : list) {
                point.y += distance;
            }
        }
    }

    /**
     *
     * Increase/deacrease the scale of the circle according to the boolean flag.
     * Triangle scaling is implemented by multiplicating each his point on the
     * factor.
     *
     * @param increase
     *            represents how to change the scale of the triangle.
     * @param factor
     *            represents how should we scale the triangle.
     */
    @Override
    public void scale(final boolean increase, final int factor) {
        if (increase) {
            for (Point point : list) {
                point.x *= factor;
                point.y *= factor;
            }
        } else {
            for (Point point : list) {
                point.x /= factor;
                point.y /= factor;
            }
        }
    }

    /**
     * Returns the perimeter of this circle.
     */
    @Override
    public double getPerimeter() {
        Point p1 = list.get(0);
        Point p2 = list.get(1);
        Point p3 = list.get(2);
        double a = Math
                .sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
        double b = Math
                .sqrt(Math.pow(p3.x - p2.x, 2) + Math.pow(p3.y - p2.y, 2));
        double c = Math
                .sqrt(Math.pow(p3.x - p1.x, 2) + Math.pow(p3.y - p1.y, 2));
        return a + b + c;
    }

}
