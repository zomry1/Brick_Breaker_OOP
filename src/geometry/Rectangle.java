package geometry;
import java.util.ArrayList;
import java.util.List;
/**
 * Rectangle - the base for Paddle and Block.
 * @author Omry
 *
 */
public class Rectangle {
    private Point upperLeft;
    private Point upperRight;
    private Point bottomLeft;
    private Point bottomRight;

    /**
     * Constructor.
     * @param upperLeft - the upperLeft point of the Rectangle
     * @param width - the width of the Rectangle
     * @param height - the height of the Rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        this.bottomLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        this.bottomRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }

    /**
     * Return list of Points that the Line is collision with the Rectangle.
     * @param line - the line we want to check the Rectangle collision with
     * @return list of Points that the Line is collision with the Rectangle
     */
    public java.util.List<Point> intersectionPoint(Line line) {
        //Create the lines of the rectangle;
        Line upper = new Line(this.upperLeft, this.upperRight);
        Line bottom = new Line(this.bottomLeft, this.bottomRight);
        Line left = new Line(this.upperLeft, this.bottomLeft);
        Line right = new Line(this.upperRight, this.bottomRight);

        //Create a new list of Points
        List<Point> intersectionPoints = new ArrayList<Point>();
        //For each side of the Rectangle check collision with the line
        boolean isIntersection = false;
        if (line.intersectionWith(upper) != null) {
            intersectionPoints.add(line.intersectionWith(upper));
            isIntersection = true;
        }
        if (line.intersectionWith(bottom) != null) {
            intersectionPoints.add(line.intersectionWith(bottom));
            isIntersection = true;
        }
        if (line.intersectionWith(left) != null) {
            intersectionPoints.add(line.intersectionWith(left));
            isIntersection = true;
        }
        if (line.intersectionWith(right) != null) {
            intersectionPoints.add(line.intersectionWith(right));
            isIntersection = true;
        }
        //If there is no collision points return false
        if (!isIntersection) {
            return null;
        }
        //Else return the list of points
        return intersectionPoints;
    }

    /**
     * Return the width of the Rectangle.
     * @return the width of the Rectangle
     */
    public double getWidth() {
        return (this.upperRight.getX() - this.upperLeft.getX());
    }

    /**
     * Return the height of the Rectangle.
     * @return the height of the Rectangle
     */
    public double getHeight() {
        return (this.bottomLeft.getY() - this.upperLeft.getY());
    }

    /**
     * Return the upper left Point of the Rectangle.
     * @return the upper left Point of the Rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Return the upper right Point of the Rectangle.
     * @return the upper right Point of the Rectangle
     */
    public Point getUpperRight() {
        return this.upperRight;
    }

    /**
     * Return the bottom left Point of the Rectangle.
     * @return the bottom left Point of the Rectangle
     */
    public Point getBottomLeft() {
        return this.bottomLeft;
    }

    /**
     * Return the bottom right Point of the Rectangle.
     * @return the bottom right Point of the Rectangle
     */
    public Point getBottomRight() {
        return this.bottomRight;
    }

    /**
     * Move the Rectangle x pixel's to the side.
     * @param x - the number of pixel's we want to move the Rectangle
     */
    public void moveRectangle(double x) {
        //Move each point the x pixel's
        this.upperLeft.setX(this.upperLeft.getX() + x);
        this.upperRight.setX(this.upperRight.getX() + x);
        this.bottomLeft.setX(this.bottomLeft.getX() + x);
        this.bottomRight.setX(this.bottomRight.getX() + x);
    }
}
