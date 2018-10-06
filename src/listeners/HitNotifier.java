package listeners;
/**
 * Hit Notifier interface.
 * @author Omry Zur
 *
 */
public interface HitNotifier {
    /**
     * Add listener to the hit notifier.
     * @param hl - the listener we want to add
     */
       void addHitListener(HitListener hl);

        /**
         * Remove listener from the hit notifier.
         * @param hl - the listener we want to remove
         */
       void removeHitListener(HitListener hl);
}
