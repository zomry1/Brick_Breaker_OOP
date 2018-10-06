package game.mechanics;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.body.GameLevel;
import geometry.Point;
import geometry.Rectangle;
/**
 * The Paddle - the "Block" moves by the user input.
 * @author Omry
 *
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle rect;
    private biuoop.KeyboardSensor keyboard;
    private double lScreenBorder;
    private double rScreenBorder;
    private int speed;
    private int dSpeed;

    /**
     * Constructor.
     * @param rect - the Rectangle of the Paddle
     * @param keyboard - the user input sensor
     * @param rScreenBorder - the right border of the screen
     * @param speed - the speed of the paddle
     */
    public Paddle(Rectangle rect, biuoop.KeyboardSensor keyboard, double rScreenBorder, int speed) {
        this.rect = rect;
        this.keyboard = keyboard;
        this.lScreenBorder = 20; //the width of the sides block
        this.rScreenBorder = rScreenBorder;
        this.speed = speed;
        this.dSpeed = speed;
    }

    /**
     * Move the Paddle left.
     */
    public void moveLeft() {
        if (rect.getUpperLeft().getX() < this.lScreenBorder + 10) {
            return;
        }
        this.rect.moveRectangle(this.dSpeed * -1);
    }
    /**
     * Move the Paddle to the right.
     */
    public void moveRight() {
        if (rect.getUpperRight().getX() > this.rScreenBorder - 10) {
              return;
         }
         this.rect.moveRectangle(this.dSpeed);
    }

    /**
     * Check if the right or the left key is Pressed if so move the Paddle to the correct place.
     * @param dt - relative time.
     */
    public void timePassed(double dt) {
        this.dSpeed = (int) (this.speed * dt);
        //Check if the keys are pressed and move the Paddle
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
    }

    /**
     * Draw the Paddle on the surface.
     * @param d - the surface we want to draw on it
     */
    public void drawOn(DrawSurface d) {
        //Get the coordinate's for easy use
        int topLeftCornerX = (int) this.rect.getUpperLeft().getX();
        int topLeftCornerY = (int) this.rect.getUpperLeft().getY();
        int width = (int)  this.rect.getWidth();
        int height = (int) this.rect.getHeight();
        //Draw the Paddle
        d.setColor(java.awt.Color.YELLOW);
        d.fillRectangle(topLeftCornerX, topLeftCornerY, width, height);
        //Draw the frame of the Paddle
        d.setColor(java.awt.Color.BLACK);
        d.drawRectangle(topLeftCornerX, topLeftCornerY, width, height);
    }

    //Collidable implements
    /**
     * Return the Rectangle of the Paddle.
     * @return return the Rectangle of the Paddle
     */
    public Rectangle getCollistionRectangle() {
        return this.rect;
    }

    /**
     * Return the new Velocity after hitting the Paddle.
     * the paddle have 5 parts on the top - each of them return other angle of Velocity
     * and 1 part each side - return the Velocity with negative dx
     * @param collisionPoint - the Collision point of the Ball with the Paddle
     * @param currentVelocity - the current Velocity of the Ball
     * @param hitter - the ball that hit the paddle
     * @return the new Velocity after hitting the Paddle
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //Copy the current Velocity of the
        Velocity newVel = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        //Get the speed of the Velocity
        double currSpeed = currentVelocity.getSpeed();
        //the x of the collision point
        double xCPoint = collisionPoint.getX();
        //The x start of the paddle
        double startPaddleX = this.rect.getUpperLeft().getX();
        //Check what is the width of 1/5 of the paddle
        double paddlePart = (this.rect.getUpperRight().getX() - startPaddleX) / 5;
        //Check what to do with =
        //Zone 1 - the first left part of the paddle
        if (xCPoint >= startPaddleX &&  xCPoint < startPaddleX + paddlePart) {
            newVel = Velocity.fromAngleAndSpeed(300, currSpeed);
        //Zone 2 - the second left part of the paddle
        } else if (xCPoint >= startPaddleX + paddlePart &&  xCPoint <= startPaddleX + paddlePart * 2) {
            newVel = Velocity.fromAngleAndSpeed(330, currSpeed);
        //Zone 3 - the middle part of the paddle
        } else if (xCPoint > startPaddleX + paddlePart * 2 &&  xCPoint <= startPaddleX + paddlePart * 3) {
            newVel.setDy(newVel.getDy() * -1);
        //Zone 4 - the second right part of the paddle
        } else if (xCPoint > startPaddleX + paddlePart * 3 &&  xCPoint < startPaddleX + paddlePart * 4) {
            newVel = Velocity.fromAngleAndSpeed(30, currSpeed);
        //Zone 5 - the first right part of the paddle
        } else if (xCPoint >= startPaddleX + paddlePart * 4 &&  xCPoint <= startPaddleX + paddlePart * 5) {
            newVel = Velocity.fromAngleAndSpeed(60, currSpeed);
        //Else its the 2 sides of the Paddle
        } else {
            newVel.setDx(newVel.getDx() * -1);
        }
        return newVel;

    }

    /**
     * Add the Paddle to the Collidable's and Sprite's lists belongs to the Game.
     * @param g - the Game thats the lists are belongs to
     */
    public void addToGame(GameLevel g) {
        g.addCollidable((Collidable) this);
        g.addSprite((Sprite) this);
    }

    /**
     * Change the location of the paddle to the middle of the screen.
     */
    public void middlePaddle() {
        this.rect = new Rectangle(new Point(400 - (this.rect.getWidth() / 2), 560),
                    this.rect.getWidth(), this.rect.getHeight());
    }
}
