/**
 *
 */
package main.tasks.n1.figures;

/**
 *
 * @author piskaryov
 *
 */
public abstract class Figure {
    /**
     *Outputs type of figure point, radius and perimeter using default output stream(System.out).
     */
    public abstract void print();

    /**
     *Changes the cordinates of a figure.
     *@param direction in which direction this figure ought to move.
     *@param distance how should be changed the cordinates of points
     */
    public abstract void move(Directions direction, int distance);

    /**
    *
    *Increase/deacrease the scale of the figure according to the boolean flag.
    *@param increase represents should we increase the figure or decrease it.
    *@param factor represents how should we scale the figure.
    */
    public abstract void scale(boolean increase, int factor);

    /**
     *Returns the perimeter of this circle.
     *@return returns the value of perimeter.
     */
    public abstract double getPerimeter();

}
