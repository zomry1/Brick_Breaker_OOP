package animations;
import java.awt.Color;

import biuoop.DrawSurface;

/**
 * Pasue Animation - stop the game until spacebar is pressed.
 * implement Animation
 * @author Omry Zur
 */
public class PauseScreen implements Animation {
    /**
     * Constructor.
     */
    public  PauseScreen() {
    }

    /**
     * Draw the frame and the pause screen.
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

        //Pause sign
        d.setColor(new Color(245, 245, 245));
        d.fillRectangle(d.getWidth() / 2 - 40, d.getHeight() / 3 - 50, 20, 100);
        d.fillRectangle(d.getWidth() / 2 + 10, d.getHeight() / 3 - 50, 20, 100);
        d.setColor(new Color(0, 0, 0));
        for (int i = 20; i > 0; i--) {
            d.setColor(new Color(7 * i, 7 * i, 11 * i));
            d.drawCircle(d.getWidth() / 2 - 5, d.getHeight() / 3, 100 - i);
        }

        //Text paused
        d.setColor(new Color(139, 0, 0));
        d.drawText(d.getWidth() / 2 - 100, d.getHeight() / 2 + 50, "Pause", 70);
        d.setColor(new Color(255, 245, 238));
        d.drawText(d.getWidth() / 2 - 98, d.getHeight() / 2 + 50, "Pause", 70);

        //Text press spacebar to resume
        d.setColor(new Color(205, 92, 92));
        d.drawRectangle(d.getWidth() / 2 - 200, d.getHeight() / 2 + 80,  420, 40);
        d.setColor(new Color(255, 160, 122));
        d.drawText(d.getWidth() / 2 - 180, d.getHeight() / 2 + 110, "- Press spacebar to resume -", 30);
    }

    /**
     * Stop the pasue screen.
     * @return - true - stop the animation. false - continue the animation
     */
    public boolean shouldStop() {
        return true;
    }

}
