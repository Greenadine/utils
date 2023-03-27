package dev.greenadine.utils.distribution;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * This class provides a skeletal implementation of the {@link Distributor} interface, to minimize the effort required
 * to implement the interface. This implementation allows defining a {@link DistributionMethod}, predetermining the
 * amount of groups to distribute elements over, and to define the desired minimum and/or maximum size for each
 * individual group.
 *
 * @param <E> the type of elements distributed by the distributor.
 * @param <T> the type of {@code Collection} used for distributing the elements.
 *
 * @see Distributor
 * @see ArrayListDistributor
 * @see DistributionMethod
 *
 * @author Kevin Zuman
 * @since 1.0
 */
public abstract class AbstractDistributor<E, T extends Collection<E>>
        implements Distributor<E, T> {

    protected DistributionMethod distributionMethod;
    protected int amountOfGroups;
    protected int minSizePerGroup;
    protected int maxSizePerGroup;

    protected AbstractDistributor() {
        this.distributionMethod = DistributionMethod.BEST_EFFORT;
        this.amountOfGroups = 0;
        this.minSizePerGroup = 0;
        this.maxSizePerGroup = 0;
    }

    @Override
    @NotNull
    public DistributionMethod getDistributionMethod() {
        return distributionMethod;
    }

    /**
     * Sets the method of distributing the items. <br/>
     * Default = {@link DistributionMethod#BEST_EFFORT}.
     *
     * @param distributionMethod the {@link DistributionMethod}.
     *
     * @return The current {@code DistributionBuilder} instance to chain methods.
     */
    @NotNull
    public AbstractDistributor<E, T> useDistributionMethod(@NotNull DistributionMethod distributionMethod) {
        this.distributionMethod = distributionMethod;
        return this;
    }

    /**
     * Sets the amount of groups to distribute the items over.
     *
     * @param amountOfGroups the amount of groups to distribute the items over.
     *
     * @return The current {@code AbstractDistributor} instance to chain methods.
     */
    @NotNull
    public AbstractDistributor<E, T> forAmountOfGroups(int amountOfGroups) {
        Preconditions.checkArgument(amountOfGroups > 0, "Groups amount has to be above 0.");
        this.amountOfGroups = amountOfGroups;
        return this;
    }

    /**
     * Sets the minimum amount of items to be in a single group.
     *
     * @param minSizePerGroup the minimum amount of items per group.
     *
     * @return The current {@code AbstractDistributor} instance to chain methods.
     */
    @NotNull
    public AbstractDistributor<E, T> withMinGroupSize(int minSizePerGroup) {
        this.minSizePerGroup = minSizePerGroup;
        return this;
    }

    /**
     * Sets the maximum amount of items to be in a single group.
     *
     * @param maxSizePerGroup the maximum amount of items per group.
     *
     * @return The current {@code AbstractDistributor} instance to chain methods.
     */
    @NotNull
    public AbstractDistributor<E, T> withMaxGroupSize(int maxSizePerGroup) {
        this.maxSizePerGroup = maxSizePerGroup;
        return this;
    }
}
