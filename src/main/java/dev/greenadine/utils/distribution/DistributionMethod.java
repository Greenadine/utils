package dev.greenadine.utils.distribution;

/**
 * An enum representing different possible methods of distributing items over a number of groups.
 *
 * @since 0.1
 * @author Greenadine
 */
public enum DistributionMethod {

    /**
     * Distributes the elements evenly over all the groups, without guarantee that each group will contain the same number
     * of elements.
     */
    BEST_EFFORT,
    /**
     * Distributes the elements evenly over all the groups, or throws an exception if it is not possible to do so (if
     * {@code Collection.size() % amountOfGroups != 0}).
     */
    EVEN,
    /**
     * Distributes the elements in a "first come, first served" manner, where a group is filled up before they are
     * distributed over the next group(s).
     */
    SEQUENTIAL,
    /**
     * Distributes the elements over a predetermined amount of groups in a "first come, first served" manner, where a
     * group is filled up before they are distributed over the next group(s), while ensuring that each group contains
     * at least one element.
     */
    PARTIAL
}
