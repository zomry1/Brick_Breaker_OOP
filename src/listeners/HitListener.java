package listeners;
import game.mechanics.Ball;
import game.mechanics.Block;
/**
 * Hit Listener interface.
 * @author Omry Zur
 *
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit - the block that being hit.
     * @param hitter - the Ball that's doing the hitting.
     */
       void hitEvent(Block beingHit, Ball hitter);
}
