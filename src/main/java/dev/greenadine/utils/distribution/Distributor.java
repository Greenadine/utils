package dev.greenadine.utils.distribution;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * The root interface in the distribution framework. A {@code Distributor} represents a utility class meant to
 * distribute a {@code Collection} of elements to distribute over an array of {@code Collection} (referred within the
 * framework to as 'groups'). This is normally done according to a specified {@link DistributionMethod}.
 *
 * @param <E> the type of elements distributed by the {@code Distributor}.
 * @param <T> the type of {@code Collection} used for distributing the elements.
 *
 * @see AbstractDistributor
 * @see ArrayListDistributor
 * @see SortingDistributor
 * @see AbstractSortingDistributor
 * @see DistributionMethod
 *
 * @author Kevin Zuman
 * @since 1.0
 */
public interface Distributor<E, T extends Collection<E>> {

    /**
     * Returns the {@link DistributionMethod} used by this {@code Distributor}.
     *
     * @return The {@code DistributionMethod} used by this {@code Distributor}.
     */
    @Nullable
    DistributionMethod getDistributionMethod();

    /**
     * Distributes the elements in the input collection to an array of collections (referred within the framework as 'groups'),
     * according to a specified {@link DistributionMethod}. The resulting array of collections is returned.
     *
     * @param elements the {@code Collection} of elements to distribute among the groups.
     * @return An array of {@code Collection}s that contain the distributed elements.
     *
     * @throws IllegalArgumentException if the input collection is null or empty.
     * @throws UnsupportedOperationException if the distribution method is not supported by the distributor implementation.
     */
    @NotNull
    T[] distribute(T elements);
}
