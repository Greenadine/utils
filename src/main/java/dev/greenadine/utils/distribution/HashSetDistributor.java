package dev.greenadine.utils.distribution;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

/**
 * A {@link Distributor} that uses {@link HashSet}s as the containers for each group.
 *
 * @param <E> the type of elements distributed by the distributor.
 *
 * @see Distributor
 * @see AbstractDistributor
 * @see DistributionMethod
 *
 * @author Kevin Zuman
 * @since 1.0
 */
public class HashSetDistributor<E>
        extends AbstractDistributor<E, HashSet<E>>
        implements Distributor<E, HashSet<E>> {

    /* TODO
        - implement distribute()
     */

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull HashSet<E>[] distribute(HashSet<E> elements) {
        Preconditions.checkArgument(!elements.isEmpty(), "HashSet cannot be empty.");

        switch (distributionMethod) {
            default -> throw new UnsupportedOperationException(String.format("Unknown/unsupported distribution method '%s'.", distributionMethod.name()));

            // Distribute the elements as evenly as possible over all the groups
            case BEST_EFFORT -> {

            }

            // Distribute the elements evenly over all the groups
            case EVEN -> {

            }

            // Distribute the items into the first group until it reaches the configured maximum group size before
            // moving over to the next group
            case SEQUENTIAL -> {

            }

            // Distribute the items into the first group until it reaches the configured maximum group size before
            // moving over to the next group, while ensuring that each group contains at least a specific amount
            // of items
            case PARTIAL -> {
                return (HashSet<E>[]) new HashSet[0];
            }
        }
        return new HashSet[0];
    }

    @SuppressWarnings("unchecked")
    private static <E> HashSet<E>[] createGroups(final int groupsAmount) {
        final HashSet<E>[] result = (HashSet<E>[]) new HashSet[groupsAmount];

        for (int i = 0; i < groupsAmount; i++) {
            result[i] = new HashSet<>();
        }
        return result;
    }
}
