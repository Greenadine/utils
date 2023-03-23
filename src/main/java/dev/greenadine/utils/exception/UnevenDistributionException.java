package dev.greenadine.utils.exception;

/**
 * Thrown when a {@link dev.greenadine.utils.distribution.GroupDistributor} is unable to evenly distribute items
 * over a set of groups by {@link dev.greenadine.utils.distribution.DistributionMethod#EVEN}.
 *
 * @since 0.1
 * @author Greenadine
 */
public class UnevenDistributionException extends RuntimeException {

    public UnevenDistributionException(String message) {
        super(message);
    }
}
