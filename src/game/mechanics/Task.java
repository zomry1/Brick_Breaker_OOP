package game.mechanics;
/**
 * Task have run function and return something.
 * @author Omry Zur
 *
 * @param <T> - the type that returned
 */
public interface Task<T> {

    /**
     * run something and return something.
     * @return something
     */
    T run();
}
