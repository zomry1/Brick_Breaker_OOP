package game.mechanics;

import geometry.Point;

/**
 * @author Omry
 */
public class Velocity {
    /**
     * Class Velocity.
     * Members: dx - the number of x unit to move
     *          dy - the number of y unit to move
     * Constructor 2 - (dx,dy) and (angle,speed)
     */
    private double dx;
    private double dy;

    /**
     * Constructor.
     * @param dx - the number of x unit to move
     * @param dy - the number of y unit to move
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * (There is already constructor with 2 double-we created him static).
     * @param angle - the angle of the movment
     * @param speed - the number of unit per move
     * @return the new Velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        //Cast the angle from degrees to radians
        angle = Math.toRadians(angle);
        //Calcualte the dx,dy by the speed and angle
        /*
        double dx = speed * Math.cos(angle);
        double dy = speed * -Math.sin(angle);*/
        double dx = speed * Math.sin(angle);
        double dy = speed * -Math.cos(angle);
        //Create new Velocity and return him
        return new Velocity(dx, dy);
    }

    /**
     * Apply the movement on point and return the new point.
     * @param p - the point we want to move
     * @return - the new point after the movement
     */
    public Point applyToPoint(Point p) {
        //Get the x and y of the point
        double xIndex = p.getX();
        double yIndex = p.getY();
        //Return the new point by the same coordinate + the dx and dy
        return new Point(xIndex + this.dx, yIndex + this.dy);
    }

    /**
     * Return the dx of the Velocity.
     * @return the dx of the Velocity
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Return the dy of the Velocity.
     * @return the dy of the Velocity
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Set the dx of the Velocity.
     * @param dxValue the value of dx we want to set
     */
    public void setDx(double dxValue) {
        this.dx = dxValue;
    }

    /**
     * Set the dy of the Velocity.
     * @param dyValue the value of dy we want to set
     */
    public void setDy(double dyValue) {
        this.dy = dyValue;
    }

    /**
     * Return the speed of the Velocity.
     * @return the speed of the Velocity
     */
    public double getSpeed() {
        //Calculate the distance of the movement each step
        return Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
    }
}
