package dev.greenadine.utils.exception;

import dev.greenadine.utils.distribution.DistributionMethod;
import dev.greenadine.utils.distribution.Distributor;

/**
 * Thrown when a {@link Distributor} is unable to evenly distribute items
 * over a set of groups by {@link DistributionMethod#EVEN}.
 *
 * @since 0.1
 * @author Greenadine
 */
public class UnevenDistributionException extends RuntimeException {

    public UnevenDistributionException(String message) {
        super(message);
    }
}
