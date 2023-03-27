package dev.greenadine.utils.distribution;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Comparator;

/**
 * This class provides a skeletal implementation of the {@link SortingDistributor} interface, to minimize the effort
 * required to implement the interface. This implementation allows defining a {@link SortingMethod}, and a custom
 * {@link Comparator}.
 *
 * @param <E> the type of elements distributed by the distributor.
 * @param <T> the type of {@code Collection} used for distributing the elements.
 *
 * @author Kevin Zuman
 * @since 1.0
 */
public abstract class AbstractSortingDistributor<E, T extends Collection<E>>
        extends AbstractDistributor<E, T>
        implements SortingDistributor<E, T> {

    protected SortingMethod sortingMethod;
    protected @Nullable Comparator<E> comparator;

    protected AbstractSortingDistributor() {
        super();

        sortingMethod = SortingMethod.DEFAULT;
    }

    /**
     * Gets the {@link SortingMethod} currently defined for the {@code SortedDistributor}.
     *
     * @return The current {@code SortingMethod}.
     */
    @NotNull
    public SortingMethod getSortingMethod() {
        return sortingMethod;
    }

    /**
     * Gets the {@link Comparator} used for sorting the elements for the {@code SortedDistributor}.
     *
     * @return The {@code Comparator}.
     */
    @NotNull
    public Comparator<E> getComparator() throws UnsupportedOperationException {
        if (sortingMethod != SortingMethod.CUSTOM) {
            throw new UnsupportedOperationException("Sorting method is not SortingMethod.CUSTOM.");
        }
        assert comparator != null;
        return comparator;
    }

    /**
     * Sorts the elements in all groups into ascending order, according their {@linkplain Comparable natural order}.
     *
     * @return The current {@code AbstractSortedDistributor} instance to chain methods.
     */
    @NotNull
    public AbstractSortingDistributor<E, T> sortNaturally() {
        this.sortingMethod = SortingMethod.NATURAL_ORDER;
        return this;
    }

    /**
     * Sorts the elements in all groups into descending order, according their {@linkplain Comparable natural order}.
     *
     * @return The current {@code AbstractSortedDistributor} instance to chain methods.
     */
    public AbstractSortingDistributor<E, T> sortReversed() {
        this.sortingMethod = SortingMethod.NATURAL_ORDER_REVERSED;
        return this;
    }

    /**
     * Sorts the elements in all groups according to a custom {@link Comparator}.
     *
     * @param comparator the {@link Comparator}.
     *
     * @return The current {@code AbstractSortedDistributor} instance to chain methods.
     */
    @NotNull
    public AbstractSortingDistributor<E, T> sortBy(@NotNull Comparator<E> comparator) {
        this.sortingMethod = SortingMethod.CUSTOM;
        this.comparator = comparator;
        return this;
    }
}
