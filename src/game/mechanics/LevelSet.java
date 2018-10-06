package game.mechanics;

/**
 * Level set build from key and description.
 * @author Omry Zur
 *
 */
public class LevelSet {
    private String key;
    private String description;

    /**
     * Constructor.
     * @param key - the key for the level set
     * @param description - the description of the level set
     */
    public LevelSet(String key, String description) {
        this.key = key;
        this.description = description;
    }

    /**
     * @return the description of the level set.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the key of the level set.
     */
    public String getKey() {
        return key;
    }
}
