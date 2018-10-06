package indicators;
import biuoop.DrawSurface;
import game.body.GameLevel;
import game.mechanics.Counter;
import game.mechanics.Sprite;
import geometry.Rectangle;

/**
 * Indicator - show the lives of the player.
 * implement sprite
 * @author Omry Zur
 *
 */
public class LivesIndicator implements Sprite {
    private Rectangle rect;
    private Counter lives;

    /**
     * Constructor.
     * @param rect - the top bar of the screen (where to draw the indicator)
     * @param lives - counter of the lives
     */
    public LivesIndicator(Rectangle rect, Counter lives) {
        this.rect = rect;
        this.lives = lives;
    }

    /**
     * draw the indicator of the lives.
     * @param d - the surface we want to draw on
     */
    public void drawOn(DrawSurface d) {
        //Get the location to draw the text
        int topLeftCornerX = (int) this.rect.getUpperLeft().getX();
        int topLeftCornerY = (int) this.rect.getUpperLeft().getY();
        int width = (int)  this.rect.getWidth();
        int height = (int) this.rect.getHeight();
        int xText = (int) (topLeftCornerX + (width / 2) - 300);
        int yText = (int) (topLeftCornerY + (height / 2) + 5);

        //Draw the text
        String hearts = " - ";
        String sHeart = Character.toString((char) 164);
        for (int i = 0; i < this.lives.getValue(); i++) {
            hearts += sHeart;
        }
        d.setColor(java.awt.Color.RED);
        d.drawText(xText, yText, "Lives: " + this.lives.getValue() + hearts, 15);

    }

    /**
     * Add the Indicator to the  Sprite's lists of a Game.
     * @param g - The Game that we want to add our Indicator to this lists.
     */
    public void addToGame(GameLevel g) {
        g.addSprite((Sprite) this);
    }

    /**
     * Nothing.
     * @param dt - relative time
     */
    public void timePassed(double dt) {
    }

}
