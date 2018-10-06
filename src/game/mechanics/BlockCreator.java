package game.mechanics;
/**
 * Block creator interface.
 * can create block with specific x and y
 * @author Omry Zur
 *
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     * @param xpos - the x location of the block
     * @param ypos - the y location of the block
     * @return - the block itself
     */
    Block create(int xpos, int ypos);
}
