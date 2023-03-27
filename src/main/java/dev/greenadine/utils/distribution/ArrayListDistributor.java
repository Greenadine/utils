package dev.greenadine.utils.distribution;

import com.google.common.base.Preconditions;
import dev.greenadine.utils.exception.UnevenDistributionException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * A {@link SortingDistributor} that uses {@link ArrayList}s as the containers for each group. This allows for
 * <i>total ordering</i> on its resulting groups, while also allowing duplicate elements.
 *
 * @param <E> the type of elements distributed by the distributor.
 *
 * @see Distributor
 * @see SortingDistributor
 * @see AbstractSortingDistributor
 * @see DistributionMethod
 * @see SortingMethod
 *
 * @author Kevin Zuman
 * @since 1.0
 */
public class ArrayListDistributor<E>
        extends AbstractSortingDistributor<E, ArrayList<E>>
        implements Distributor<E, ArrayList<E>> {

    /* TODO
        - implement DistributionMethod.PARTIAL in distribute()
        - implement functionality of 'minSizePerGroup' for DistributionMethod.SEQUENTIAL in distribute()
        - implement functionality of 'minSizePerGroup' for DistributionMethod.PARTIAL in distribute()
     */

    public ArrayListDistributor() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<E>[] distribute(ArrayList<E> elements) {
        Preconditions.checkArgument(!elements.isEmpty(), "ArrayList cannot be empty.");

        // Sort elements according to the specified sorting method
        if (sortingMethod != SortingMethod.DEFAULT) {
            switch (sortingMethod) {
                case NATURAL_ORDER -> {
                    Preconditions.checkArgument(elements.get(0) instanceof Comparable, "All elements in ArrayList must implement Comparable when using SortingMethod.NATURAL_ORDER.");
                    elements.sort((Comparator<? super E>) Comparator.naturalOrder());
                }
                case NATURAL_ORDER_REVERSED -> {
                    Preconditions.checkArgument(elements.get(0) instanceof Comparable, "All elements in ArrayList must implement Comparable when using SortingMethod.NATURAL_ORDER_REVERSED.");
                    elements.sort((Comparator<? super E>) Comparator.reverseOrder());
                }
                case CUSTOM ->
                        elements.sort(comparator);
            }
        }

        switch (distributionMethod) {
            default -> throw new UnsupportedOperationException(String.format("Unknown/unsupported distribution method '%s'.", distributionMethod.name()));

            // Distribute the elements as evenly as possible over all the groups
            case BEST_EFFORT -> {
                // Ensure the desired amount of groups has been set
                Preconditions.checkArgument(amountOfGroups > 0, "Amount of groups has to be configured and above 0 when using DistributionMethod.BEST_EFFORT.");

                // Silently notify defining unnecessary variables for the method
                if (minSizePerGroup > 0 || maxSizePerGroup > 0) {
                    throw new UnsupportedOperationException("Min/max size per group is not supported for DistributionMethod.BEST_EFFORT.");
                }

                final ArrayList<E>[] result = createGroups(amountOfGroups);
                final int itemsPerGroup = elements.size() / amountOfGroups;
                // Some groups might require 1 more element than the lowest occurring group size, in order to be able to
                // retain current elements order
                final int itemsRemaining = elements.size() % amountOfGroups;

                // Iterate over the elements to distribute them until none are left
                int i = 0;
                final Iterator<E> iterator = elements.iterator();
                while (iterator.hasNext()) {
                    int extra = (i < itemsRemaining) ? 1 : 0;

                    for (int j = 0; j < (itemsPerGroup + extra); j++) {
                        if (!iterator.hasNext()) {
                            break;
                        }
                        result[i].add(iterator.next());
                    }
                    i++;
                }
                return result;
            }

            // Distribute the elements evenly over all the groups
            case EVEN -> {
                // Ensure the desired amount of groups has been set
                Preconditions.checkArgument(amountOfGroups > 0, "Amount of groups has to be configured and above 0 when using DistributionMethod.BEST_EFFORT.");

                // Min and max size per group is not supported for this method
                if (minSizePerGroup > 0 || maxSizePerGroup > 0) {
                    throw new UnsupportedOperationException("Min/max size per group is not supported for DistributionMethod.BEST_EFFORT.");
                }

                // Ensure the elements can be perfectly evenly distributed over all groups
                if (elements.size() % amountOfGroups != 0) {
                    throw new UnevenDistributionException("Items cannot be evenly distributed over the determined amount of groups.");
                }

                final ArrayList<E>[] result = createGroups(amountOfGroups);
                final int itemsPerGroup = elements.size() / amountOfGroups;

                // Iterate over the items to distribute them until none are left
                int i = 0;
                final Iterator<E> iterator = elements.iterator();
                while (iterator.hasNext()) {
                    for (int j = 0; j < itemsPerGroup; j++) {
                        result[i].add(iterator.next());
                    }
                    i++;
                }
                return result;
            }

            // Distribute the items into the first group until it reaches the configured maximum group size before
            // moving over to the next group
            case SEQUENTIAL -> {
                // Ensure the max size per group has been set
                Preconditions.checkArgument(maxSizePerGroup > 0, "Max size per group has to be configured and above 0 when using DistributionMethod.SEQUENTIAL.");

                // Defining the exact desired amount of groups is not supported for this method
                if (amountOfGroups != 0) {
                    throw new UnsupportedOperationException("Defining the exact desired amount of groups is not supported for DistributionMethod.SEQUENTIAL.");
                }

                // Calculate the necessary amount of groups
                amountOfGroups = elements.size() / maxSizePerGroup;
                if (elements.size() % maxSizePerGroup != 0) {
                    amountOfGroups++;
                }

                final ArrayList<E>[] result = createGroups(amountOfGroups);

                // Iterate over the items to distribute them until none are left
                int i = 0;
                final Iterator<E> iterator = elements.iterator();
                while (iterator.hasNext()) {
                    for (int j = 0; j < maxSizePerGroup; j++) {
                        if (!iterator.hasNext()) {
                            break;
                        }
                        result[i].add(iterator.next());
                    }
                    i++;
                }
                return result;
            }

            // Distribute the items into the first group until it reaches the configured maximum group size before
            // moving over to the next group, while ensuring that each group contains at least a specific amount
            // of items
            case PARTIAL -> {
                return (ArrayList<E>[]) new ArrayList[0];
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <E> ArrayList<E>[] createGroups(final int groupsAmount) {
        final ArrayList<E>[] result = (ArrayList<E>[]) new ArrayList[groupsAmount];

        for (int i = 0; i < groupsAmount; i++) {
            result[i] = new ArrayList<>();
        }
        return result;
    }
}
