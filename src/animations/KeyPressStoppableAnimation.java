package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
/**
 * decorator for animation to stop animation by key.
 * @author Omry Zur
 *
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor ks;
    private String keyString;
    private Animation animation;
    private boolean isAlreadyPressed;
    private boolean stop;

    /**
     * Constructor.
     * @param sensor - the keyboard to get input from user
     * @param key - the key we want to stop the animation with
     * @param animation - the animation we warp (what we want to show)
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.ks = sensor;
        this.keyString = key;
        this.animation = animation;
        //If the key already pressed
        if (this.ks.isPressed(this.keyString)) {
            this.isAlreadyPressed = true;
        }
    }

    /**
     * Draw the animation we warp and check for key pressed.
     * @param d - the surface we want to draw on
     * @param dt - the realtive time
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //Draw the animation we warp
        this.animation.doOneFrame(d, dt);
        //Check if the key not already pressed - if the key is pressed
        if (!this.isAlreadyPressed) {
            if (this.ks.isPressed(this.keyString)) {
                this.stop =  true;
                return;
            }
            this.stop = false;
        } else {
            //If the key is already pressed - check if is not pressed
            if (!this.ks.isPressed(this.keyString)) {
                this.isAlreadyPressed = false;
            }
        }
    }

    /**
     * @return - return true if the key was pressed - to stop the animation.
     */
    public boolean shouldStop() {
        return this.stop;
    }

}
