package game.mechanics;

import geometry.Point;

/**
 * The CollisionInfo storage -.
 * the Point of the collision
 * the Collidable the ball collision with
 * @author Omry
 *
 */
public class CollisionInfo {
    private Point collitionPoint;
    private Collidable collisionObject;

    /**
     * Construcor of CollisionInfo.
     * @param collisionPoint - the Point of the collision
     * @param collisionObject - the Collidable the ball collision with
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collitionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Return the Point of the collision.
     * @return Point of the collision
     */
    public Point collitionPoint() {
        return this.collitionPoint;
    }

    /**
     * Return the Collidable the ball collision with.
     * @return Collidable the ball collision with.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
