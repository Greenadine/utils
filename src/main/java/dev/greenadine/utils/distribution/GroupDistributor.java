package dev.greenadine.utils.distribution;

import com.google.common.base.Preconditions;
import dev.greenadine.utils.exception.NotImplementedException;
import dev.greenadine.utils.exception.UnevenDistributionException;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * A utility class which distributes a {@code Collection} of items over a predetermined amount of groups.
 * <p>
 * This is done with one of the following 3 distribution methods:<br/>
 * - {@link DistributionMethod#BEST_EFFORT} (default),<br/>
 * - {@link DistributionMethod#EVEN},<br/>
 * - {@link DistributionMethod#SEQUENTIAL},<br/>
 * - {@link DistributionMethod#PARTIAL}.
 *</p>
 *
 * @param <T> the type of the object that will be distributed by the {@code GroupDistributor}.
 *
 * @since 0.1
 * @author Greenadine
 */
public class GroupDistributor<T> {

    private final Collection<T> items;

    // Settings variables
    private DistributionMethod distributionMethod;
    private int groupsAmount;
    private int minSizePerGroup; // TODO implement
    private int maxSizePerGroup;

    private GroupDistributor(@NotNull final Collection<T> items) {
        this.items = items;
        this.distributionMethod = DistributionMethod.BEST_EFFORT;
        this.groupsAmount = 0;
        this.minSizePerGroup = 0;
        this.maxSizePerGroup = 0;
    }

    /**
     * Creates and returns a new {@link GroupDistributor} instance for the provided {@code Collection} of items.
     *
     * @param items the {@code Collection} of items to distribute.
     *
     * @return the newly created {@code DistributionBuilder} for the provided {@code Collection} of items.
     *
     * @throws IllegalArgumentException if the provided collection is empty.
     */
    @NotNull
    public static <R> GroupDistributor<R> forItems(@NotNull final Collection<R> items) {
        Preconditions.checkArgument(!items.isEmpty(), "Items cannot be empty.");
        return new GroupDistributor<>(items);
    }

    /**
     * Sets the method of distributing the items. <br/>
     * Default = {@link DistributionMethod#BEST_EFFORT}.
     *
     * @param distributionMethod the {@link DistributionMethod}.
     *
     * @return the current {@code DistributionBuilder} instance to chain methods.
     */
    @NotNull
    public GroupDistributor<T> useDistributionMethod(@NotNull final DistributionMethod distributionMethod) {
        this.distributionMethod = distributionMethod;
        return this;
    }

    /**
     * Sets the amount of groups to distribute the items over.
     *
     * @param groupsAmount the amount of groups to distribute the items over.
     *
     * @return the current {@code DistributionBuilder} instance to chain methods.
     */
    @NotNull
    public GroupDistributor<T> withAmountOfGroups(final int groupsAmount) {
        Preconditions.checkArgument(groupsAmount > 0, "Groups amount has to be above 0.");
        this.groupsAmount = groupsAmount;
        return this;
    }

    /**
     * Sets the minimum amount of items to be in a single group.
     *
     * @param minSizePerGroup the minimum amount of items per group.
     *
     * @return the current {@code DistributionBuilder} instance to chain methods.
     */
    @NotNull
    public GroupDistributor<T> withMinGroupSize(final int minSizePerGroup) {
        this.minSizePerGroup = minSizePerGroup;
        return this;
    }

    /**
     * Sets the maximum amount of items to be in a single group.
     *
     * @param maxSizePerGroup the maximum amount of items per group.
     *
     * @return the current {@code DistributionBuilder} instance to chain methods.
     */
    @NotNull
    public GroupDistributor<T> withMaxGroupSize(final int maxSizePerGroup) {
        this.maxSizePerGroup = maxSizePerGroup;
        return this;
    }

    /**
     * Distributes the items over the determined amount of groups with the set {@link DistributionMethod}, and returns the result.
     *
     * @return an array of {@code Set}s representing the groups containing the distributed items.
     *
     * @throws IllegalArgumentException if a necessary value for the applying the chosen distribution method was not configured.
     * @throws UnevenDistributionException if it was not possible to evenly distribute the items by {@link DistributionMethod#EVEN}.
     * @throws NotImplementedException if an unknown or not-implemented distribution method was used.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public Set<T>[] distribute() {
        switch (distributionMethod) {
            // Distribute the items as evenly as possible over all the groups
            case BEST_EFFORT -> {
                // Ensure the desired amount of groups has been set
                Preconditions.checkArgument(groupsAmount != 0, "Amount of groups has to be configured when using DistributionMethod.BEST_EFFORT.");

                final Set<T>[] result = (Set<T>[]) new Set[groupsAmount];
                final int itemsPerGroup = items.size() / groupsAmount;

                // If the determined items per group exceeds the configured max amount of items per group
                if (maxSizePerGroup > 0 && itemsPerGroup > maxSizePerGroup) {
                    throw new IllegalArgumentException("Items per group exceeds configured max group size.");
                }

                // Some groups might require 1 more item than the lowest occurring group size, in order to retain
                // original collection order
                final int itemsRemaining = items.size() % groupsAmount;

                // Iterate over the items to distribute them until none are left
                int i = 0;
                final Iterator<T> iterator = items.iterator();
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

            // Distribute the items evenly over all the groups
            case EVEN -> {
                // Ensure the desired amount of groups has been set
                Preconditions.checkArgument(groupsAmount != 0, "Amount of groups has to be configured when using DistributionMethod.BEST_EFFORT.");

                // Ensure the items can be perfectly evenly distributed over all groups
                if (items.size() % groupsAmount != 0) {
                    throw new UnevenDistributionException("Items cannot be evenly distributed over the determined amount of groups.");
                }

                final int itemsPerGroup = items.size() / groupsAmount;

                // If the determined items per group exceeds the configured max amount of items per group
                if (maxSizePerGroup > 0 && itemsPerGroup > maxSizePerGroup) {
                    throw new IllegalArgumentException("Items per group exceeds configured max group size.");
                }

                final Set<T>[] result = (Set<T>[]) new Set[groupsAmount];

                // Iterate over the items to distribute them until none are left
                int i = 0;
                final Iterator<T> iterator = items.iterator();
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
                // Ensure the desired maximum size per group has been set
                Preconditions.checkArgument(maxSizePerGroup != 0, "Max group size has to be configured when using DistributionMethod.SEQUENTIAL.");

                groupsAmount = (int) Math.ceil((float) (items.size() / maxSizePerGroup));
                final Set<T>[] result = (Set<T>[]) new Set[groupsAmount];

                // Iterate over the items to distribute them until none are left
                int i = 0;
                final Iterator<T> iterator = items.iterator();
                while (iterator.hasNext()) {
                    for (int j = 0; j < maxSizePerGroup; j++) {
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
                // TODO

                return (Set<T>[]) new Set[0];
            }
            default -> throw new NotImplementedException(String.format("Unknown/unimplemented distribution method '%s'.", distributionMethod.name()));
        }
    }
}
