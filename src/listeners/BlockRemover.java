package listeners;
import game.body.GameLevel;
import game.mechanics.Ball;
import game.mechanics.Block;
import game.mechanics.Counter;

/**
 * Block remover. remove block when hit by a ball and the hits is 0
 * implements HitLisetener
 * @author Omry Zur
 */
public class BlockRemover implements HitListener {

    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Constructor.
     * @param game - the game we want to remove the block from
     * @param removedBlocks - counter of the blocks
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     *  Blocks that are hit and reach 0 hit-points should be removed
     *  from the game.
     *  @param beingHit - the block that being hit - we want to remove
     *  @param hitter - the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 1) {
            // remove this listener from the block that is being removed from the game.
            beingHit.removeHitListener(this);
            beingHit.removeFromeGame(this.game);
            this.remainingBlocks.decrease(1);
        }

    }

}
