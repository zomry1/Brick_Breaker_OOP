package game.mechanics;

import biuoop.DrawSurface;
/**
 * The interface of the Sprite -.
 * each object we want to draw on the screen
 * Include - Ball, Block, Paddle
 * @author Omry
 *
 */
public interface Sprite {
    /**
     * Draw the sprite on the screen.
     * @param d - the surface we want to draw on it
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the Sprite that the time has passed -.
     * the Ball - move by Velocity
     * the Paddle - check for user input to move
     * @param dt - the relative time
     */
    void timePassed(double dt);
}
