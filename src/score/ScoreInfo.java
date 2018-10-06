package score;
import java.io.Serializable;

/**
 * Storage the score of a game with name.
 * @author Omry Zur
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;

    /**
     * Constructor.
     * @param name - the name of the player
     * @param score - the score the player score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @return - the name of the player who score this.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return - the score of this scoreInfo.
     */
    public int getScore() {
        return this.score;
    }

}
