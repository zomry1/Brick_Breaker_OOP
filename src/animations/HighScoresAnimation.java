package animations;
import java.awt.Color;

import biuoop.DrawSurface;
import score.HighScoresTable;
import score.ScoreInfo;
/**
 * Animation that show the score table.
 * implements Animation
 * @author Omry zur
 */
public class HighScoresAnimation implements Animation {

    private HighScoresTable scores;
    /**
     * Constructor.
     * @param scores - the table we want to show
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }

    /**
     * Draw the table.
     * @param d - the surface we want to draw on
     * @param dt - the realtive time
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //Background and frame
        d.setColor(new Color(105, 105, 105));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        for (int i = 12; i > 0; i--) {
            d.setColor(new Color(255 - i * 5, 255 - i * 5, 255 - i * 5));
            d.drawRectangle(0, 0, d.getWidth() - i, d.getHeight() - i);
        }

        //Draw the title
        String title = "Scoreboard";
        d.setColor(Color.WHITE);
        d.drawText(50, 50, title, 50);
        d.drawText(49, 50, title, 50);
        d.drawText(48, 50, title, 50);
        d.setColor(Color.LIGHT_GRAY);
        d.drawText(52, 50, title, 50);

        //Draw the table
        int i = 0;
        boolean firstPlace = true;
        for (ScoreInfo score: this.scores.getHighScores()) {
            if (firstPlace) {
                d.setColor(new Color(255, 215, 0));
                d.drawText(100, 150 + i, score.getName() + " -", 40);
                d.setColor(new Color(218, 165, 32));
                d.drawText(99, 150 + i, score.getName(), 40);
                d.drawText(98, 150 + i, score.getName(), 40);
                d.setColor(new Color(176, 196, 222));
                d.drawText(500, 150 + i, String.valueOf(score.getScore()), 40);
                d.setColor(new Color(119, 136, 153));
                d.drawText(499, 150 + i, String.valueOf(score.getScore()), 40);
                d.drawText(498, 150 + i, String.valueOf(score.getScore()), 40);
                d.setColor(new Color(119, 136, 153));
                d.fillCircle(80, 130 + i, 5);
                firstPlace = false;
            } else {
                d.setColor(Color.LIGHT_GRAY);
                d.drawText(100, 150 + i, score.getName() + " -", 40);
                d.setColor(Color.DARK_GRAY);
                d.drawText(99, 150 + i, score.getName(), 40);
                d.drawText(98, 150 + i, score.getName(), 40);
                d.setColor(Color.RED);
                d.drawText(500, 150 + i, String.valueOf(score.getScore()), 40);
                d.setColor(Color.BLACK);
                d.drawText(499, 150 + i, String.valueOf(score.getScore()), 40);
                d.drawText(498, 150 + i, String.valueOf(score.getScore()), 40);
                d.setColor(Color.BLACK);
                d.fillCircle(80, 130 + i, 5);
            }
            i += 80;
        }
        firstPlace = true;
    }


    /**
     * @return  should stop = true  - the animation warrepd by KeyPressedAnimation decorator.
     */
    public boolean shouldStop() {
        return true;
    }

}
