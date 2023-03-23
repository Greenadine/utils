package dev.greenadine.utils.distribution;

/**
 * Represents the different possible methods of distributing items over an amount of groups.
 *
 * @since 0.1
 * @author Greenadine
 */
public enum DistributionMethod {

    /**
     * Distributes the items evenly over all the sets, without guarantee that each group will contain the same number
     * of items.
     */
    BEST_EFFORT,
    /**
     * Distributes the items evenly over all the sets, or throws an exception if it is not possible to do so (if
     * {@code items.size() % setsAmount != 0}).
     */
    EVEN,
    /**
     * Distributes the items in a "first come, first served" manner, where a group is filled up before they are
     * distributed over the next group(s).
     */
    SEQUENTIAL,
    /**
     * Distributes the items over a predetermined amount of groups in a "first come, first served" manner, where a
     * group is filled up before they are distributed over the next group(s), while ensuring that each group contains
     * at least one item.
     */
    PARTIAL
}
