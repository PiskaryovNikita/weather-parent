/**
 *
 */
package main.tasks.n1.figures;

import java.awt.Point;

/**
 * @author piskaryov
 *
 */
public class Circle extends Figure {
    /**
     * The center of circle.
     */
    private Point point;
    /**
     * The radius of the circle.
     */
    private int radius;

    /**
     * Creates a circle with specified length and radius.
     *
     * @param p The center of circle.
     * @param r The radius of the circle.
     */
    public Circle(final Point p, final int r) {
        this.point = p;
        this.radius = r;
    }

    /**
     * Outputs type of figure point, radius and perimeter using default output
     * stream(System.out).
     */
    @Override
    public void print() {
        System.out.println("Circle, (" + point.x + ", " + point.y
                + "); radius: " + radius + ", perimeter: " + getPerimeter());
    }

    /**
     * Changes the cordinates of center according to direction.
     *
     * @param direction
     *            in which direction this figure ought to move.
     * @param distance
     *            how should be changed the cordinates of points
     */
    public void move(final Directions direction, final int distance) {
        if (direction == Directions.LEFT) {
            point.x -= distance;
        } else if (direction == Directions.RIGTH) {
            point.x += distance;
        } else if (direction == Directions.DOWN) {
            point.y -= distance;
        } else {
            point.y += distance;
        }
    }

    /**
     *
     * Increase/deacrease the scale(radius) of the circle according to the
     * boolean flag.
     *
     * @param increase
     *            represents how to change the scale of the circle.
     * @param factor
     *            represents how should we scale the circle.
     */
    @Override
    public void scale(final boolean increase, final int factor) {
        if (increase) {
            radius *= factor;
        } else {
            radius /= factor;
        }
    }

    /**
     * Returns the perimeter of this circle.
     */
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

}
