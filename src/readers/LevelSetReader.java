package readers;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.mechanics.LevelSet;
import game.mechanics.LevelInformation;
/**
 * Read set of levels and load them, each set have a key.
 */
public class LevelSetReader {
    /**
     * Read set of levels and load them, each set have a key.
     * @param reader - the file with the set information
     * @return map of sets key and list of levels
     */
    public static Map<LevelSet, List<LevelInformation>> fromReader(Reader reader) {
        Map<LevelSet, List<LevelInformation>> mapSets = new HashMap<LevelSet, List<LevelInformation>>();
        String line;
        String evenValues = null;
        String[] oddValues = null;
        boolean evenFlag = false;
        boolean oddFlag = false;
        LineNumberReader lineReader = null;
        try {
            lineReader = new LineNumberReader(reader);
            //Read line , it a odd number line read the key and description, otherwise read the level path
            while ((line = lineReader.readLine()) != null) {
                if (lineReader.getLineNumber() % 2 != 0) {
                    oddValues = line.split(":");
                    oddFlag = true;
                } else {
                    evenValues = line;
                    evenFlag = true;
                }
                if (oddFlag && evenFlag) {
                    LevelSet set = new LevelSet(oddValues[0], oddValues[1]);
                    if (toLevels(evenValues) != null) {
                        mapSets.put(set, toLevels(evenValues));
                    }
                    oddFlag = false;
                    evenFlag = false;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file.");
            return null;
        } catch (IOException e) {
            System.err.println("Failed reading file" + ", message:" + e.getMessage());
            e.printStackTrace(System.err);
            return null;
        } finally {
            if (lineReader != null) {
                try {
                    lineReader.close();
                } catch (IOException e) {
                    System.err.println("Failed close the file" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return mapSets;
    }
    /**
     * take path of level definition and load it to a list of level information.
     * @param path - the path of the level definition
     * @return list of all the level inforamtion in the file
     */
    private static List<LevelInformation> toLevels(String path) {
        //Try to load the file of the level
        try {
            LevelSpecificationReader levelReader = new LevelSpecificationReader();
            Reader reader = null;
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            reader = new InputStreamReader(is);
            //Get list of levels from the file
            List<LevelInformation> levels = levelReader.fromReader(reader);
            return levels;
        } catch (Exception e) {
            System.out.println("Error load level set");
            return null;
        }
    }
}
