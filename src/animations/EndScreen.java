package animations;
import java.awt.Color;
import java.awt.Polygon;

import biuoop.DrawSurface;
import game.mechanics.Counter;

/**
 * End screen Animation - at the end of the game, shows the score.
 * implement Animation
 * @author Omry Zur
 */
public class EndScreen implements Animation {
    private boolean win;
    private Counter score;

    /**
     * Constructor.
     * @param win - boolean if the user win or loose
     * @param score - the score the user score
     */
    public EndScreen(boolean win, Counter score) {
        this.win = win;
        this.score = score;
    }

    /**
     * Draw the frame and the score of the user (add crown if the user win).
     * @param d - the surface we want to draw on
     * @param dt - the relative time
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //Background and frame
        d.setColor(new Color(105, 105, 105));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        for (int i = 12; i > 0; i--) {
            d.setColor(new Color(255 - i * 5, 255 - i * 5, 255 - i * 5));
            d.drawRectangle(0, 0, d.getWidth() - i, d.getHeight() - i);
        }
        //Draw crown if the user win and a appropriate message
        if (this.win) {
            d.setColor(new Color(255, 215, 0));
            for (int i = 15; i > 0; i--) {
                d.drawPolygon(createCrown(150 + i, 60 + i));
            }
            d.drawText(d.getWidth() / 4, d.getHeight() / 2, "You win! Your score is "
                                                + this.score.getValue(), 32);
        } else {
            d.setColor(new Color(178, 34, 34));
            d.drawText(d.getWidth() / 4, d.getHeight() / 2, "Game Over.", 60);
            d.setColor(Color.BLACK);
            d.drawText(d.getWidth() / 4 - 1, d.getHeight() / 2, "Game Over.", 60);
            d.drawText(d.getWidth() / 4 - 2, d.getHeight() / 2, "Game Over.", 60);
            d.drawText(d.getWidth() / 4 + 50, d.getHeight() / 2 + 100, "Your score is " + this.score.getValue(), 32);
        }
    }

    /**
     * create a polygon of crown in specific x and y coordinates.
     * @param xStart - the x coordinate of the crown
     * @param yStart - the y coordinate of the crown
     * @return - the polygon (crown)
     */
    private Polygon createCrown(int xStart, int yStart) {
        int size = 2;
        int[] xpoint = {17 * size + xStart, 49  * size + xStart, 152  * size + xStart,
                        172  * size + xStart, 135  * size + xStart, 113  * size + xStart,
                        87  * size + xStart, 48  * size + xStart,
                        62  * size + xStart, 138  * size + xStart, 156  * size + xStart,
                        140  * size + xStart, 119  * size + xStart, 98  * size + xStart};

        int[ ] ypoint = {30  * size + yStart, 97  * size + yStart, 97  * size + yStart,
                        28  * size + yStart, 45  * size + yStart, 21  * size + yStart,
                        45  * size + yStart, 28  * size + yStart,
                        83  * size + yStart, 83  * size + yStart, 53  * size + yStart,
                        61  * size + yStart, 43  * size + yStart, 61  * size + yStart};

        return new Polygon(xpoint, ypoint, 14);
    }

    /**
     * Stop the countdown.
     * @return - true - stop the animation. the animation warrped by KeyPressStoppableAnimation decorator
     */
    public boolean shouldStop() {
        return true;
    }

}
