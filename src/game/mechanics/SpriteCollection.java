package game.mechanics;

import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
/**
 * SpriteCollection list of Sprite's.
 * @author Omry
 *
 */
public class SpriteCollection {

    private List<Sprite> sprites = new ArrayList<Sprite>();

    /**
     * Add Sprite to the list.
     * @param s - the Sprite we want to add
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Call timePassed() on all the Sprite's.
     * @param dt - the relative time
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> spritesCopy = new ArrayList<Sprite>(this.sprites);
        for (Sprite spr: spritesCopy) {
            spr.timePassed(dt);
        }
    }

    /**
     * call DrawOn(d) all the Sprite's.
     * to Draw all the Sprite's
     * @param d - the surface we want to draw on it
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite spr: sprites) {
            spr.drawOn(d);
        }
    }

    /**
     * return the list of the Sprite's of the SpriteCollection.
     * @return the list of the Sprite's of the SpriteCollection
     */
    public List<Sprite> getSprites() {
        return this.sprites;
    }
}
