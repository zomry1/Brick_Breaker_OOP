package animations;
import biuoop.DrawSurface;
/**
 *  Animation Interface.
 * @author Omry Zur
 * animations : countdown, endscreen, pausescreen
 */
public interface Animation {
    /**
     * Draw frame of the animation.
     * @param d - the surface to drow on it
     * @param dt - the relative time
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * When to end the animation.
     * @return - true to stop animation, false to continue.
     */
    boolean shouldStop();
}
