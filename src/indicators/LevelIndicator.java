package indicators;
import biuoop.DrawSurface;
import game.body.GameLevel;
import game.mechanics.Sprite;
import geometry.Rectangle;

/**
 * Indicator - show the level name.
 * implement sprite
 * @author Omry Zur
 *
 */
public class LevelIndicator implements Sprite {
    private Rectangle rect;
    private String levelName;

    /**
     * Constructor.
     * @param rect - the top bar of the screen (where to draw the indicator)
     * @param levelName - the level name
     */
    public LevelIndicator(Rectangle rect, String levelName) {
        this.rect = rect;
        this.levelName = levelName;
    }

    /**
     * draw the indicator of the level name.
     * @param d - the surface we want to draw on
     */
    public void drawOn(DrawSurface d) {
        //Get the location to draw the text
        int topLeftCornerX = (int) this.rect.getUpperLeft().getX();
        int topLeftCornerY = (int) this.rect.getUpperLeft().getY();
        int width = (int)  this.rect.getWidth();
        int height = (int) this.rect.getHeight();
        int xText = (int) (topLeftCornerX + (width / 2) + 150);
        int yText = (int) (topLeftCornerY + (height / 2) + 5);

      //Draw the text
        d.setColor(java.awt.Color.BLUE);
        d.drawText(xText, yText, "Level: " + this.levelName, 15);

    }

    /**
     * Add the Indicator to the  Sprite's lists of a Game.
     * @param g - The Game that we want to add our Indicator to this lists.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Nothing.
     * @param dt - relative time
     */
    public void timePassed(double dt) {
    }

}
