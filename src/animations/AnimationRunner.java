package animations;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
/**
 * Animation runner, get gui and can run animation in the gui.
 * @author Omry Zur
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * Constructor of animation runner.
     * @param gui - the gui we want to show the animation in it
     */
    public  AnimationRunner(GUI  gui) {
        this.gui = gui;
        this.framesPerSecond = 60;
        this.sleeper = new Sleeper();
    }

    /**
     * Run animation.
     * @param animation - the animation we want to run
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;

        while (!animation.shouldStop()) {
            //Save the current time in milliseconds
            long startTime = System.currentTimeMillis();

          //get a surface
            DrawSurface d = gui.getDrawSurface();
          //Draw the frame
            animation.doOneFrame(d, (double) millisecondsPerFrame / 1000);
          //Show the GUI
            gui.show(d);

          //Timing - check the time was past and if we have time space wait it
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
