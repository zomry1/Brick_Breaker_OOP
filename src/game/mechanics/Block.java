package game.mechanics;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import biuoop.DrawSurface;
import game.body.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import listeners.HitListener;
import listeners.HitNotifier;

/**
 * The Block - Collidable and Sprite. can collision with the ball and draw on
 * the screen
 *
 * its Build from Rectangle have a color and a number of hits left
 * @author Omry
 *
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private int hits = 0;
    private List<HitListener> hitListeners;
    private Map<Integer, Color> hitFillsColor;
    private Map<Integer, Image> hitFillsImage;
    private Color stroke;
    private Color fillColor;
    private Image fillImage;

    /**
     * Constructor.
     * @param rect - the Rectangle of the Block
     * @param hits - the number of hits the block left
     * @param fill - the fill of the block
     * @param stroke - the stroke of the ball
     * @param hitFills - the fill by hit points
     */
    public Block(Rectangle rect, int hits, String fill, String stroke, Map<Integer, String> hitFills) {
        this.rect = rect;
        this.hitListeners = new ArrayList<HitListener>();
        this.hits = hits;
        //Create maps to color and images
        this.hitFillsColor = new HashMap<Integer, Color>();
        this.hitFillsImage = new HashMap<Integer, Image>();
        //load the images and colors to the maps
        this.hitFillsToMap(hitFills);
        //load the color or image of the fill
        this.fillToValue(fill);
        //load the color
        this.stroke = ColorsMap.toColor(stroke);
    }

    /**
     * Copy constructor. with fillColor
     * @param rectC - rectangle
     * @param hitsC - hits
     * @param hitListenersC - hitsListener
     * @param hitFillsColorC - hitFillsColor
     * @param hitFillsImageC - hitFillsImage
     * @param strokeC - stroke
     * @param fillColorC - fillColor
     */
    public Block(Rectangle rectC, int hitsC, List<HitListener> hitListenersC, Map<Integer, Color> hitFillsColorC,
                        Map<Integer, Image> hitFillsImageC, Color strokeC,
                        Color fillColorC) {
        this.rect = rectC;
        this.hits = hitsC;
        this.hitListeners = hitListenersC;
        this.hitFillsColor = hitFillsColorC;
        this.hitFillsImage = hitFillsImageC;
        this.stroke = strokeC;
        this.fillColor = fillColorC;
    }

    /**
     * Copy constructor. with fillImage
     * @param rectC - rectangle
     * @param hitsC - hits
     * @param hitListenersC - hitsListener
     * @param hitFillsColorC - hitFillsColor
     * @param hitFillsImageC - hitFillsImage
     * @param strokeC - stroke
     * @param fillImageC - fillImage
     */
    public Block(Rectangle rectC, int hitsC, List<HitListener> hitListenersC, Map<Integer, Color> hitFillsColorC,
                        Map<Integer, Image> hitFillsImageC, Color strokeC, Image fillImageC) {
        this.rect = rectC;
        this.hits = hitsC;
        this.hitListeners = hitListenersC;
        this.hitFillsColor = hitFillsColorC;
        this.hitFillsImage = hitFillsImageC;
        this.stroke = strokeC;
        this.fillImage = fillImageC;
    }

    /**
     * Get block and create copy of it.
     * @return copy of the Block
     */
    public Block blockCopy() {
        Rectangle rectC = this.rect;
        List<HitListener> hitListenersC = this.hitListeners;
        Map<Integer, Color> hitFillsColorC = this.hitFillsColor;
        Map<Integer, Image> hitFillsImageC = this.hitFillsImage;
        int hitsC = this.hits;
        Color strokeC = this.stroke;
        Color fillColorC = this.fillColor;
        Image fillImageC = this.fillImage;
        if (fillColor != null) {
            return new Block(rectC, hitsC, hitListenersC, hitFillsColorC, hitFillsImageC, strokeC, fillColorC);
        } else {
            return new Block(rectC, hitsC, hitListenersC, hitFillsColorC, hitFillsImageC, strokeC, fillImageC);
        }
    }

    /**
     * Return the Rectangle the Block is.
     * @return the Rectangle of the Block
     */
    public Rectangle getCollistionRectangle() {
        return this.rect;
    }

    /**
     * Return the new Velocity the ball should have after hitting the Block.
     * @param collisionPoint
     *            - the collision point of the ball with the Block
     * @param currentVelocity
     *            - the Velocity of the Ball before hitting the Block
     * @param hitter
     *            - the ball that hit the block
     * @return the new Velocity the ball should have after hitting the Block
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(hitter);
        // Remove one from the hits left to the Block
        if (this.hits > 0) {
            this.hits--;
        }
        // Copy the Velocity of the Ball
        Velocity newVel = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        // Get 2 point of the Rectangle of the Block
        Point upperLeft = this.rect.getUpperLeft();
        Point bottomRight = this.rect.getBottomRight();
        // Check where the Ball hit the Rectangle the left or the right
        if (this.inRange(upperLeft.getX(), collisionPoint.getX())
                || this.inRange(bottomRight.getX(), collisionPoint.getX())) {
            newVel.setDx(newVel.getDx() * -1);
        }
        // Or/And the up or bottom
        if (this.inRange(upperLeft.getY(), collisionPoint.getY())
                || this.inRange(bottomRight.getY(), collisionPoint.getY())) {
            newVel.setDy(newVel.getDy() * -1);
        }
        return newVel;
    }

    /**
     * @return - Create the stroke sprite and return it.
     */
    private Sprite drowStroke() {

        int topLeftCornerX = (int) this.rect.getUpperLeft().getX();
        int topLeftCornerY = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();
        //If stroke is not null
        if (stroke != null) {
            //Create new stroke with stroke color
            return new Sprite() {
                public void drawOn(DrawSurface d) {
                    d.setColor(stroke);
                    d.drawRectangle(topLeftCornerX, topLeftCornerY, width, height);
                }

                public void timePassed(double dt) {
                }
            };
        }
        //Return empty sprite
        return new Sprite() {
            public void drawOn(DrawSurface d) {
            }
            public void timePassed(double dt) {
            }
        };
    }

    /**
     * @return - Create the block sprite and return it.
     */
    private Sprite drowBlock() {
        int topLeftCornerX = (int) this.rect.getUpperLeft().getX();
        int topLeftCornerY = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();

        //If there is fill color by hit point
        if (this.hitFillsColor.containsKey(this.hits)) {
            return new Sprite() {

                public void drawOn(DrawSurface d) {
                    d.setColor(hitFillsColor.get(hits));
                    d.fillRectangle(topLeftCornerX, topLeftCornerY, width, height);
                }

                public void timePassed(double dt) {
                }

            };
        } else if (this.hitFillsImage.containsKey(this.hits)) {
            //There is fill image by hit point
            return new Sprite() {

                public void drawOn(DrawSurface d) {
                    d.drawImage(topLeftCornerX, topLeftCornerY, hitFillsImage.get(hits));
                }

                public void timePassed(double dt) {

                }
            };
        } else if (this.fillColor != null) {
            //There is fill color
            return new Sprite() {

                public void drawOn(DrawSurface d) {
                    d.setColor(fillColor);
                    d.fillRectangle(topLeftCornerX, topLeftCornerY, width, height);
                }

                public void timePassed(double dt) {
                }

            };
        } else {
            return new Sprite() {
                //There is fill image
                public void drawOn(DrawSurface d) {
                    d.drawImage(topLeftCornerX, topLeftCornerY, fillImage);
                }

                public void timePassed(double dt) {

                }
            };
        }
    }

    /**
     * Draw the Block on the surface.
     * @param d - the surface we want to draw the Block on it
     */
    public void drawOn(DrawSurface d) {
        //Draw the Block
        this.drowBlock().drawOn(d);
        //Draw the frame
        this.drowStroke().drawOn(d);
    }

    /**
     * Time Passed to the Block - now its nothing. just here because the implements.
     * @param dt - the relative tine
     */
    public void timePassed(double dt) {

    }

    /**
     * Add the Block to the Collidable's and Sprite's lists of a Game.
     * @param g - The Game that we want to add our Block to this lists.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable((Collidable) this);
        g.addSprite((Sprite) this);
    }

    /**
     * Check if the point is in range of other point with 0.2. To fix Deviation in
     * big numbers calculation
     * @param side
     *            - the point that we want to check if the point is very close to
     * @param point
     *            - the point we want to check if the side is very close to
     * @return true if the point is very close to the side, otherwise - false
     */
    private boolean inRange(double side, double point) {
        if (side - 0.2 <= point && side + 0.2 >= point) {
            return true;
        }
        return false;
    }

    /**
     * Remove the block from the sprites and collidable lists of game.
     * @param game
     *            - the game we want to remove the block from his sprite's and
     *            collidable's lists
     */
    public void removeFromeGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Add a listener to the block.
     * @param hl
     *            - the listener we want to add to the block
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Remove a listener from the block.
     * @param hl
     *            - the listener we want to remove
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * Notify all the listener that there is a hit.
     * @param hitter
     *            - send the hitter ball to the listeners
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Get the current hit points of the block.
     * @return - the current hit points of the block
     */
    public int getHitPoints() {
        return hits;
    }

    /**
     * Load the fill by hits to the maps of the block.
     * @param hitFills - the string with the information what to load
     */
    private void hitFillsToMap(Map<Integer, String> hitFills) {
        //Foreach entry in the map
        for (Map.Entry<Integer, String> entry : hitFills.entrySet()) {
            //If the string is color
            if (ColorsMap.toColor(entry.getValue()) != null) {
                this.hitFillsColor.put(entry.getKey(), ColorsMap.toColor(entry.getValue()));
            } else {
                //the string is file location of image, load it
                Image img = null;
                try {
                    InputStream is = ClassLoader.getSystemClassLoader()
                            .getResourceAsStream(entry.getValue().substring(entry.getValue().indexOf("(") + 1,
                                    entry.getValue().length() - 1));
                    img = ImageIO.read(is);
                } catch (IOException e) {
                    // Should not happen beacuse validtion checked before
                    System.out.println("Error load image of block");
                    System.exit(0);
                }
                this.hitFillsImage.put(entry.getKey(), img);
            }
        }
    }

    /**
     * Load fill (image or color).
     * @param fill - the string with the information
     */
    private void fillToValue(String fill) {
        if (fill.isEmpty()) {
            return;
        } else if (ColorsMap.toColor(fill) != null) {
            //If the fill is color
            this.fillColor = ColorsMap.toColor(fill);
        } else {
            //The fill is image
            Image img = null;
            try {
                InputStream is = ClassLoader.getSystemClassLoader()
                        .getResourceAsStream(fill.substring(fill.indexOf("(") + 1, fill.length() - 1));
                img = ImageIO.read(is);
            } catch (IOException e) {
                // Should not happen beacuse validtion checked before
                System.out.println("Error load image of block");
                System.exit(0);
            }
            this.fillImage = img;
        }
    }
}
