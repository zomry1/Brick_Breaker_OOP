import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import animations.AnimationRunner;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.body.GameFlow;
import game.mechanics.LevelSet;
import game.mechanics.Task;
import game.mechanics.LevelInformation;
import menu.Menu;
import menu.MenuAnimation;
import readers.LevelSetReader;
import score.HighScoresTable;

/**
 * Run the Game we created.
 * @author Omry Zur
 *
 */
public class BrickBreaker {
    /**
     * Initialize and run the Game.
     * @param args
     *            - nothing
     */
    public static void main(String[] args) {
        // Create the base of the game
        GUI gui = new GUI("Brick Breaker v100", 800, 600);
        DialogManager dm = gui.getDialogManager();
        AnimationRunner ar = new AnimationRunner(gui);
        KeyboardSensor ks = gui.getKeyboardSensor();

        String levelToSet;
        // Check if there is arg for the level set file
        if (args.length > 0) {
            levelToSet = args[0];
        } else {
            levelToSet = "level_sets.txt";
        }
        // Hard coded file locations
        final String setLocation = levelToSet;
        final String tableLocation = "highscores.ser";

        // Load table - if there is no table create new one
        File filename = new File(tableLocation);
        HighScoresTable scoresTable = HighScoresTable.loadFromFile(filename);
        if (scoresTable.getHighScores().isEmpty()) {
            try {
                scoresTable.save(filename);
            } catch (IOException e) {
                System.err.println("Unable to load the table");
                e.printStackTrace();
            }
        }

        // Create game flow
        GameFlow gf = new GameFlow(ar, ks, dm, scoresTable);

        // Create levelset reader and read the sets (levels, images and all)
        LevelSetReader setReader = new LevelSetReader();
        Map<LevelSet, List<LevelInformation>> sets = null;
        Reader reader2 = null;
        try {
            InputStream is2 = ClassLoader.getSystemClassLoader().getResourceAsStream(setLocation);
            reader2 = new InputStreamReader(is2);
            sets = setReader.fromReader(reader2);
        } catch (Exception e) {
            System.out.println("Error loading set level. error:" + e.getMessage());
        }

        // Create subMenu and add each set a key to the menu selection
        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>(ks, "level Sets");
        if (sets != null) {
            for (Map.Entry<LevelSet, List<LevelInformation>> set : sets.entrySet()) {
                subMenu.addSelection(set.getKey().getKey(), set.getKey().getDescription(), new Task() {
                    public Object run() {
                        gf.runLevels(set.getValue());
                        return null;
                    }

                });
            }
        }

        //Create the menu and add selctions
        Menu<Task<Void>> menu = new MenuAnimation<>(ks, "Menu");

        menu.addSubMenu("s", "play", subMenu);

        menu.addSelection("h", "High Scores Table", new Task() {
            public Void run() {
                ar.run(new KeyPressStoppableAnimation(ks, KeyboardSensor.SPACE_KEY,
                        new HighScoresAnimation(scoresTable)));
                return null;
            }
        });

        menu.addSelection("q", "Quit", new Task() {
            public Void run() {
                System.exit(0);
                return null;
            }
        });

        // show the menu
        while (true) {
            ar.run(menu);
            Task toDo = menu.getStatus();
            toDo.run();
        }

    }

}
