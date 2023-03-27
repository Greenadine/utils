package dev.greenadine.utils.distribution;

/**
 * An enumeration of sorting methods used by certain {@link Distributor}s.
 *
 * @see Distributor
 * @see ArrayListDistributor
 *
 * @author Kevin Zuman
 * @since 0.1
 */
public enum SortingMethod {

    /**
     * The elements' order will not be directly influenced by the distributor.
     */
    RETAIN_ORDER,

    /**
     * The elements will be ordered by their natural ordering, which is determined by the element's class
     * implementing the {@link java.lang.Comparable} interface.
     */
    NATURAL_ORDER,

    /**
     * The elements will be ordered by their natural ordering, but in reverse order.
     *
     * @see #NATURAL_ORDER
     */
    NATURAL_ORDER_REVERSED,

    /**
     * The elements will be ordered by a provided {@link java.util.Comparator}.
     */
    CUSTOM,
}
