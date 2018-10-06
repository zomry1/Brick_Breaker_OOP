package game.mechanics;
import java.util.Map;
/**
 * Factory for blocks by symbols.
 * @author Omry Zur
 *
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Constructor.
     * @param spacerWidths - the map of symbols and spacers
     * @param blockCreators - the map of symbols and blockCreators
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * @param s - the symbol
     * @return - the spacer with the symbol
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * Create block by symbol and location.
     * @param s - the symbol
     * @param x - the x of the block
     * @param y - the y of the block
     * @return - the block itself
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }

    /**
     * Check if there is spacer with the symbol.
     * @param s - the symbol we want to find
     * @return true if there is a spacer with this symbol, otherwise false.
     */
    public boolean isSpaceSymbol(String s) {
        if (this.spacerWidths.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     * Check if there is block with the symbol.
     * @param s - the symbol we want to find
     * @return true if there is a block with this symbol, otherwise false.
     */
    public boolean isBlockSymbol(String s) {
        if (this.blockCreators.containsKey(s)) {
            return true;
        }
        return false;
    }
}
