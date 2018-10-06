package indicators;
import biuoop.DrawSurface;
import game.body.GameLevel;
import game.mechanics.Counter;
import game.mechanics.Sprite;
import geometry.Rectangle;

/**
 * Indicator - show the score of the player.
 * implement sprite
 * @author Omry Zur
 *
 */
public class ScoreIndicator implements Sprite {
    private Rectangle rect;
    private Counter score;

    /**
     * Constructor.
     * @param rect - the top bar of the screen (where to draw the indicator)
     * @param score - counter of the score
     */
    public ScoreIndicator(Rectangle rect, Counter score) {
        this.rect = rect;
        this.score = score;
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
        int xText = (int) (topLeftCornerX + (width / 2));
        int yText = (int) (topLeftCornerY + (height / 2) + 5);

        //Draw the bar
        d.setColor(java.awt.Color.WHITE);
        d.fillRectangle(topLeftCornerX, topLeftCornerY, width, height);

        //Draw the text
        d.setColor(java.awt.Color.BLACK);
        d.drawText(xText, yText, "Score: " + this.score.getValue(), 15);
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
        // TODO Auto-generated method stub

    }

}
