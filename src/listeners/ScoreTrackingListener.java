package listeners;
import game.mechanics.Ball;
import game.mechanics.Block;
import game.mechanics.Counter;

/**
 * ScoreTrackingListener . update the counter of the score
 * implements HitLisetener
 * @author Omry Zur
 */
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore;

    /**
     * Constructor.
     * @param scoreCounter - the counter that save the score of the user
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }


    /**
     * Update the score when a block being hit.
     * @param beingHit - the block that being hit
     * @param hitter - the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //if the block dosent destroyed this time - 5 points
        if (beingHit.getHitPoints() > 1) {
            this.currentScore.increase(5);
        } else {
            //else - the block is destroyed - 10 points
            this.currentScore.increase(15);
        }

    }

}
