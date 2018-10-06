package animations;
import java.awt.Color;
import java.awt.Polygon;
import java.util.Random;

import biuoop.DrawSurface;
import game.mechanics.SpriteCollection;

/**
 * Countdown Animation - at the beginning of each turn.
 * implement Animation
 * @author Omry Zur
 */
public class CountdownAnimation implements Animation {
    private int countFrom;
    private double showTime;
    private SpriteCollection gameScreen;
    private boolean firstRun;
    private long pervMiliSeconds;
    private long lastDraw;

    /**
     * Constructor.
     * @param numOfSeconds - the duration of the whole animation
     * @param countFrom - the number to count from
     * @param gameScreen - the sprites (background) we want to draw under the countdown
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        //showtime of each number in miliseconds
        this.showTime = (numOfSeconds / countFrom) * 1000;
        this.gameScreen = gameScreen;
        this.firstRun = true;
    }

    /**
     * Draw the frame (the background and the countdown).
     * @param d - the surface we want to draw on
     * @param dt - the realtive time
     */
    public void doOneFrame(DrawSurface d, double dt) {

        //Draw the background
        this.gameScreen.drawAllOn(d);

        //Draw the circles
        Random rand = new Random();
        d.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        for (int i = 5; i > 0; i--) {
            d.drawCircle(d.getWidth() / 2, d.getHeight() / 2, 103 - i);
            d.drawCircle(d.getWidth() / 2, d.getHeight() / 2, 53 - i);
        }

        //Draw the number of the countdown
        d.setColor(java.awt.Color.YELLOW);
         d.drawText(d.getWidth() / 2 - 20, d.getHeight() / 2 + 30, Integer.toString(this.countFrom), 100);

        //Draw the sandclock
        d.setColor(new Color(139, 69, 19));
        for (int i = 4; i > 0; i--) {
            d.drawPolygon(createSandClock(20 + i, 30 + i));
        }

        //First time only measure the time
        if (this.firstRun) {
            this.firstRun = false;
            this.pervMiliSeconds = System.currentTimeMillis();
        }

        //Not first time - check if showtime passed from the previous print
        if (!this.firstRun) {
            if ((System.currentTimeMillis() - this.pervMiliSeconds) >= this.showTime) {
                this.countFrom--;
                this.pervMiliSeconds = System.currentTimeMillis();
            }
        }
    }

    /**
     * Stop the countdown.
     * @return - true - stop the animation. false - continue the animation
     */
    public boolean shouldStop() {
        if (this.countFrom == 0) {
            return true;
        }
        return false;
    }

    /**
     * create a polygon of sandclock in specific x and y coordinates.
     * @param xStart - the x coordinate of the sandclock
     * @param yStart - the y coordinate of the sandclock
     * @return - the polygon (sand clock)
     */
    private Polygon createSandClock(int xStart, int yStart) {
        double size = 0.3;
        int[] xPoint = new int[19];
        int[] yPoint = new int[19];
        double[] xPointD = {51 * size + xStart, 54  * size + xStart, 62  * size + xStart,
                            94  * size + xStart, 94  * size + xStart,
                            77  * size + xStart, 61  * size + xStart, 56  * size + xStart,
                            54  * size + xStart, 105  * size + xStart, 157  * size + xStart,
                            153  * size + xStart, 147  * size + xStart, 131  * size + xStart,
                            111  * size + xStart, 115  * size + xStart, 148  * size + xStart,
                            155  * size + xStart, 158  * size + xStart};

        double[ ] yPointD = {66  * size + yStart, 95  * size + yStart, 113  * size + yStart, 130  * size + yStart,
                            143  * size + yStart, 157  * size + yStart,
                            177  * size + yStart, 199  * size + yStart,
                            230  * size + yStart, 230  * size + yStart, 230  * size + yStart,
                            199  * size + yStart, 177  * size + yStart, 157  * size + yStart,
                            143  * size + yStart, 130  * size + yStart, 113  * size + yStart,
                            95  * size + yStart, 66  * size + yStart};
        for (int i = 0; i < 19; i++) {
            xPoint[i] = (int) xPointD[i];
            yPoint[i] = (int) yPointD[i];
        }
        return new Polygon(xPoint, yPoint, 19);
    }

}
