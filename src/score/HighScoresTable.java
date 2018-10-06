package score;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Score highest score and can load and save them to the hard-drive.
 * @author Omry Zur
 *
 */
public class HighScoresTable implements Serializable {
    private static int defaultSize = 5;
    private int size;
    private List<ScoreInfo> scores;

    /**
     * Create an empty high-scores table with the specified size.
     * @param size - means that the table holds up to size top scores.
     */
    public HighScoresTable(int size) {
        this.scores = new ArrayList<ScoreInfo>();
        this.size = size;
    }

    /**
     * Add a new score to the table.
     * @param score - the score we want to add
     */
    public void add(ScoreInfo score) {
        if (this.scores.isEmpty()) {
            this.scores.add(score);
            return;
        }
        int place = this.getRank(score.getScore());
        this.scores.add(place - 1, score);

        if (this.scores.size() > this.size) {
            this.scores.remove(this.size);
        }

    }

    /**
     * @return - the size of the table.
     */
    public int size() {
        return this.size;
    }

    /**
     *  The list is sorted such that the highest scores come first.
     * @return -  Return the current high scores.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * Return the rank of the given score (where will it be on the list.
     * @param score - the score we want to check
     * @return  - the rank of the given score (where will it be on the list
     * 1 - this is the highest score, size - the lowest score. size + 1 not on the list
     */
    public int getRank(int score) {
        int i = 1;
        for (ScoreInfo currScore: this.scores) {
            if (score >= currScore.getScore()) {
                return i;
            }
            i++;
        }
        return i;
    }

    /**
     * Clear the table.
     */
    public void clear() {
        this.scores.clear();
    }

    /**
     * Save the table to the hard-drive.
     * @param filename - the path we want to save the file to
     * @throws IOException - if there is any error saving the file
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream  objectOut = null;
        try {
            objectOut = new ObjectOutputStream(
                                        new FileOutputStream(filename));
            objectOut.writeObject(this);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (objectOut != null) {
                    objectOut.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * Load the table from the hard-drive, clear the table and load the new one to it.
     * @param filename - the path we want to load the file from
     * @throws IOException - if there is any error loading the file
     */
    public void load(File filename) throws IOException {
        this.clear();

        ObjectInputStream objectIn = null;
        HighScoresTable temp = null;
        try {
            objectIn = new ObjectInputStream(
                                        new FileInputStream(filename));
            temp = (HighScoresTable) objectIn.readObject();
            this.scores = temp.getHighScores();
            this.size = temp.size;
        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to find calss for object in the file: " + filename);
            e.printStackTrace();
        } finally {
            try {
                if (objectIn != null) {
                    objectIn.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * Load the table from the hard drive - clear the table and load the new one to it.
     * if there is no table, create new one
     * @param filename - the path of the file we want to read.
     * @return - table from the path (if there is no table - new one)
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable temp = new HighScoresTable(defaultSize);
        try {
            temp.load(filename);
            return temp;
        } catch (IOException e) {
            return temp;
        }
    }

}
