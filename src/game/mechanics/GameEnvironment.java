package game.mechanics;

import java.util.ArrayList;
import java.util.List;

import geometry.Line;
import geometry.Point;
/**
 * GameEnvirnment - its the list of all the Collidable's.
 * Responsible to check the collision of objects with the ball
 * @author Omry
 *
 */
public class GameEnvironment {
    private List<Collidable> collidables = new ArrayList<Collidable>();

    /**
     * Construcotr - Create GameEnvironment object with empty list.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * Add the given Collidable to the environment.
     * @param c - the Collidable we want to add
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidable's
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory - The movement Line of the Ball
     * @return - CollisionInfo the Collision Point and the Collidable that the Ball collision with
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        //Set flag of collision found to false
        boolean collisitonFound = false;
        //Set the members of the CollisionInfo to null
        Collidable currCollidable = null;
        Point closestCollide = null;
        //Get the maximum distance that the collision can be from the Line.start()
        double distance = trajectory.start().distance(trajectory.end());
        //For each Colldiable check the closest intersection to the start of the line.
        //If it less than the closer collsion we found set it to the new currCollison and set collisionFound to true
        for (Collidable coll: collidables) {
            if (coll.getCollistionRectangle().intersectionPoint(trajectory) != null) {
                Point currPoint = trajectory.closestIntersectionToStartOfLine(coll.getCollistionRectangle());
                if (trajectory.start().distance(currPoint) <= distance) {
                    collisitonFound = true;
                    currCollidable = coll;
                    closestCollide = currPoint;
                    distance = trajectory.start().distance(currPoint);
                }
            }
        }
        //If there is no collision at all - return null
        if (!collisitonFound) {
            return null;
        }
        //Else return the CollisionInfo
        return new CollisionInfo(closestCollide, currCollidable);
    }

    /**
     * return the list of the Collidable's of the GameEnvironment.
     * @return the list of the Collidable's of the GameEnvironment
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }
}
