package game.mechanics;
import java.util.List;

/**
 * Level information - interface.
 * @author Omry Zur
 *
 */
public interface LevelInformation {

    /**
     * @return - number of balls in the level.
     */
    int numberOfBalls();

    /**
     * @return - list of the velocities of the balls.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return -the speed of the paddle.
     */
    int paddleSpeed();

    /**
     * @return - the width of the paddle.
     */
    int paddleWidth();

    /**
     * @return - the name of the level.
     */
    String levelName();

    /**
     * @return - the sprite that is the background of the level.
     */
    Sprite getBackground();

    /**
     * @return - list of the blocks in the level.
     */
    List<Block> blocks();

    /**
     * @return - list of the blocks in the level.
     */
    int numberOfBlocksToRemove();
}
