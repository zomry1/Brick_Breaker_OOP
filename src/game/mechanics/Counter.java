package game.mechanics;
/**
 * Counter - start from 0 and can be increased and decreased.
 * @author Omry Zur
 *
 */
public class Counter {
    private int counter;

    /**
     * Constructor - zero the counter.
     */
    public Counter() {
        this.counter = 0;
    }

    /**
     * Increase the counter by the given number.
     * @param number - increase the counter by this number
     */
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * Decrease the counter by the given number.
     * @param number - decrease the counter by this number
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * @return - the value of the counter.
     */
    public int getValue() {
        return this.counter;
    }
}
