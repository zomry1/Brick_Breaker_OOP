package geometry;
/**
 *
 * @author Omry
 * The class Line.
 */
public class Line {
    /**
     * Class Line.
     * members: Point start, Point end
     * Constructors: 2 - (2 points),(2 x's and 2 y's)
     * Methods: 12 (length , middle, start, end, pointInBorder, notEquation,
     *  intersectionWith, isIntersecting, equals,closestIntersectionToStartOfLine, setStart, setEnd)
     */
    private Point start;
    private Point end;

    /**
     * Constructor of the Line class with points.
     * @param start - point, the start point of the line
     * @param end - point, the end point of the line
     */
    public Line(Point start, Point end) {
        //the x of the start point is less than the x of the end point
        if (start.getX() < end.getX()) {
            //set the start for start and end of end
            this.start = start;
            this.end = end;
      } else {
            //set the start for the end and end for the start
            this.start = end;
            this.end = start;
        }
    }

    /**
     * Constructor of the Line class with coordinates.
     * @param x1 - double, the x of the start point
     * @param y1 - double, the y of the start point
     * @param x2 - double, the x of the end point
     * @param y2 - double, the y of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        //create 2 points from the coordinates and call the main constructor
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Calculate the length of the line.
     * @return - double, the length of the line
     */
    public double length() {
        //return the distance between the 2 point in the line
        return this.start.distance(end);
    }

    /**
     * Calculate the middle point in the line.
     * @return - point, the middle point in the line
     */
    public Point middle() {
        //the middle of the x coordinate
        double middleX = (this.start.getX() + this.end.getX()) / 2;
      //the middle of the y coordinate
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(middleX, middleY);
    }

   /**
    * return start point of the line.
    * @return - point, the start point of the line
    */
    public Point start() {
        return this.start;
    }

    /**
     * return end point of the line.
     * @return - point, the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Set the end point of the Line.
     * @param endPoint - the end point we want to set
     */
    public void setEnd(Point endPoint) {
        this.end = endPoint;
    }

    /**
     * Set the start point of the Line.
     * @param startPoint - the start point we want to set
     */
    public void setStart(Point startPoint) {
        this.start = startPoint;
    }


    /**
     * check if the x coordinate of a point is in the range of a line.
     * @param point - point,
     * @return boolean, true - if the point is in the range, false - otherwise
     */
    public boolean pointInBorder(Point point) {
        /*if the x of the point is bigger than the x of the start
        and smaller from the end*/
        if ((point.getX() >= this.start().getX() && point.getX() <= this.end.getX())
             || (point.getX() <= this.start().getX() && point.getX() >= this.end.getX())) {
            return true;
        }
        return false;
    }

    /**
     * Calculate the intersection of a point if the line is vertical to axis x.
     * @param other - line, the other line to check intersection to.
     * @return - point, the point of the intersection,
     *  if there is no point - null
     */
    public Point notEquation(Line other) {
        //The second line incline is not infinite
        if (other.start.getX() != other.end.getX()) {
            if (other.pointInBorder(start)) {
                //Calculate the incline of the other line
                double inclineOther = (other.end.getY() - other.start.getY())
                              / (other.end.getX() - other.start.getX());

                //Find Y of the intersection point
                double interY = (inclineOther * this.start.getX())
                                - (inclineOther * other.start.getX())
                                + other.start.getY();
                //Check that the y coordinate(point) is in the range of the line
                if ((this.start.getY() <= interY && this.end.getY() >= interY)
                     || (this.start.getY() >= interY && this.end.getY() <= interY)) {
                    return new Point(this.start.getX(), interY);
                }
            }
            return null;
        }
        //First line is a point
        if (this.start.getX() == this.end.getX()
           && this.start.getY() == this.end.getY()) {
           //The x (point) is in the range of the other line
            if (this.start.getX() >= other.start.getX()
               && this.start.getX() <= other.end.getX()) {
                //The y (point) is in the range of the other line
                if (this.start.getY() >= other.start.getY()
                   && this.start.getY() <= other.end.getY()) {

                    return new Point(this.start.getX(), this.start.getY());
                }
            }
            return null;

        }
      //Second line is a point
        if (other.start.getX() == other.end.getX()
            && other.start.getY() == other.end.getY()) {
            //The x (point) is in the range of the other line
            if (other.start.getX() >= this.start.getX()
               && other.start.getX() <= this.end.getX()) {
                //The y (point) is in the range of the other line
                if (other.start.getY() >= this.start.getY()
                   && other.start.getY() <= this.end.getY()) {
                    return new Point(other.start.getX(), other.start.getY());
                }
            }
            return null;


        }   else {
            //Calculate the incline of the other line
            double incline = (other.end.getY() - other.start.getY())
                             / (other.end.getX() - other.start.getX());
            //Find Y of the intersection point
            double interY = (incline * this.start.getX())
                          - (incline * other.start.getX()) + other.start.getY();

            if (interY == this.start.getY()) {
                return new Point(this.start.getX(), this.start.getY());
            }
            return null;
        }

    }

    /**
     * Check if there is a intersection point for the lines.
     * if yes return the point
     * @param other - point, the other point to check intersection to
     * @return the intersection point, if there is no point - return null
     */
    public Point intersectionWith(Line other) {
        //If one of the line is vertical to axis x
        if (this.start.getX() == this.end.getX()) {
            return this.notEquation(other);
        }
        if (other.start.getX() == other.end.getX()) {
            return other.notEquation(this);
        }

        //Calculate the incline
        double inclineThis = (this.end.getY() - this.start().getY())
                             / (this.end.getX() - this.start().getX());

        double inclineOther = (other.end.getY() - other.start().getY())
                            / (other.end.getX() - other.start().getX());
        //Their incline is equal - the piazza allow it.
        if (inclineThis == inclineOther) {
            return null;
        }

        //Calculate the equations
        double equationThis = this.start().getY()
                              - inclineThis * this.start.getX();

        double equationOther = other.start().getY()
                               - inclineOther * other.start.getX();

        double diffrenceEquation = equationThis - equationOther;
        double diffrenceIncline = inclineOther - inclineThis;

        //To get the x of the intersection point
        double interX = diffrenceEquation / diffrenceIncline;
        double interY = (interX * inclineThis)
                         - (inclineThis * this.start().getX())
                         + this.start().getY();
        //Create that point
        Point interPoint = new Point(interX, interY);
        //Check if the point is in the x range of each line
        if (this.pointInBorder(interPoint) && other.pointInBorder(interPoint)) {
            return interPoint;
        }
        return null;
    }

    /**
     * Check if the two lines have intersection.
     * @param other - line, the other line to check the intersection to.
     * @return boolean, true - if the lines have intersection,
     *  false - if the lines don't have intersection
     */
    public boolean isIntersecting(Line other) {
        //return value is null means that there is no intersection point
        if (this.intersectionWith(other) == null) {
            return false;
        }
        return true;
    }

    /**
     * Check if two lines are equals.
     * @param other - line, the anther line to equal to.
     * @return boolean, true if the 2 lines are equal,
     * false if the 2 lines are not equal
     */
    public boolean equals(Line other) {
        if ((this.start == other.start) && (this.end == other.end)) {
            return true;
        }
        return false;
    }

    /**
     * Return the closest intersection point of the line to a Rectangle.
     * @param rect - the Rectangle we want to check intersection with
     * @return the closest intersection Point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        //Create a list of all the intersection Points with the Rectangle
        java.util.List<Point> intersectionPoints = rect.intersectionPoint(this);
        //If the list is null - empty - return null - no point
        if (intersectionPoints == null) {
            return null;
        }
        //Else set the first point as the closest and check if the next's are closer
        Point closestPoint = intersectionPoints.get(0);
        double distance = this.start.distance(closestPoint);
        for (Point inter: intersectionPoints) {
            if (this.start.distance(inter) < distance) {
                closestPoint = inter;
                distance = this.start.distance(inter);
            }
        }
        return closestPoint;
    }
}
