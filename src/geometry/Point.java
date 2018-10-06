package geometry;
/**
 *
 * @author Omry
 * The class Point
 */
public class Point {
    /**
     * Class Point.
     * members: doubles x and y
     * Constructors: 1(x and y)
     * Methods: 6 (distance, equals, getX, getY, setX, setY)
     */
    private double x;
    private double y;

    /**
     * Constructor of the point class.
     * @param x - double, x of the point
     * @param y - double, y of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculate the distance between this point (where the function was called)
     * and the other point.
     * @param other - another point to calcualte the distance to.
     * @return double, the distance between the points
     */
    public double distance(Point other) {
        double x1 = this.x;
        double x2 = other.x;
        double y1 = this.y;
        double y2 = other.y;
        //the equation to calcualte distance between 2 points
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }
    /**
     * Check if the 2 points are the same.
     * @param other - another point to check the equation
     * @return boolean, true - the same point. false - not the same point
     */
    public boolean equals(Point other) {
        //the x and y are the same for both points
        if ((this.x == other.x) && (this.y == other.y)) {
            return true;
        }
        return false;
    }
    /**
     * Return the x value of the point.
     * @return double, x value of the point
     */
    public double getX() {
        return this.x;
    }
    /**
     * Return the y value of the point.
     * @return double, y value of the point
     */
    public double getY() {
        return this.y;
    }

    /**
     * Set the x value of the Point.
     * @param xIndex - the x value we want to set
     */
    public void setX(double xIndex) {
        this.x = xIndex;
    }

    /**
     * Set the y value of the Point.
     * @param yIndex - the t value we want to set
     */
    public void setY(double yIndex) {
        this.y = yIndex;
    }
}
