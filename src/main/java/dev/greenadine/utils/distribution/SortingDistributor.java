package dev.greenadine.utils.distribution;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Comparator;

/**
 * The {@code SortedDistributor} interface and adds support to the {@link Distributor} interface for specifying a
 * sorting method used for distributing elements. This interface provides a way to distribute elements into an array of
 * sorted collections.
 *
 * @param <E> the type of elements distributed by the distributor.
 * @param <T> the type of {@code Collection} used for distributing the elements.
 *
 * @see Distributor
 * @see SortingMethod
 * @see AbstractSortingDistributor
 *
 * @author Kevin Zuman
 * @since 1.0
 */
public interface SortingDistributor<E, T extends Collection<E>>
        extends Distributor<E, T> {

    /**
     * Returns the sorting method used by this sorted distributor instance.
     *
     * @return the sorting method used by this sorted distributor instance.
     */
    @NotNull
    SortingMethod getSortingMethod();

    /**
     * Returns the comparator used for sorting elements in this sorted distributor instance. This method is only applicable
     * when the sorting method used is {@link SortingMethod#CUSTOM}.
     *
     * @return the comparator used for sorting elements in this sorted distributor instance.
     *
     * @throws UnsupportedOperationException if the sorting method used by this sorted distributor instance is not
     *                                       {@code CUSTOM}.
     */
    @NotNull
    Comparator<E> getComparator() throws UnsupportedOperationException;
}
