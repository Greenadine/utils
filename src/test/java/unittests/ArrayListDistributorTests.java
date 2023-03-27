package unittests;

import dev.greenadine.utils.distribution.ArrayListDistributor;
import dev.greenadine.utils.distribution.DistributionMethod;
import dev.greenadine.utils.exception.UnevenDistributionException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArrayListDistributorTests {

    private final ArrayListDistributor<Integer> distributor = new ArrayListDistributor<>();

    /**
     * Tests whether the {@link DistributionMethod#BEST_EFFORT} works properly with an even amount of elements.
     */
    @Test
    void ArrayListDistributor_BestEffort_Even() {
        final ArrayList<Integer> elements = testElements(10);

        final ArrayList<Integer>[] groups = distributor
                .useDistributionMethod(DistributionMethod.BEST_EFFORT)
                .forAmountOfGroups(2)
                .distribute(elements);

        assertEquals(5, groups[0].size());
        assertEquals(5, groups[1].size());

        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> group = groups[i < 5 ? 0 : 1];
            assertEquals(i, (i < 5) ? group.get(i) : group.get(i - 5));
        }
    }

    /**
     * Tests whether the {@link DistributionMethod#BEST_EFFORT} works properly with an uneven amount of elements.
     */
    @Test
    void ArrayListDistributor_BestEffort_Uneven() {
        final ArrayList<Integer> elements = testElements(9);

        final ArrayList<Integer>[] groups = distributor
                .useDistributionMethod(DistributionMethod.BEST_EFFORT)
                .forAmountOfGroups(5)
                .distribute(elements);

        assertEquals(2, groups[0].size());
        assertEquals(2, groups[1].size());
        assertEquals(2, groups[2].size());
        assertEquals(2, groups[3].size());
        assertEquals(1, groups[4].size());
    }

    /**
     * Tests whether the {@link DistributionMethod#EVEN} works properly with an even amount of elements.
     */
    @Test
    void ArrayListDistributor_Even_Even() {
        final ArrayList<Integer> elements = testElements(10);

        final ArrayList<Integer>[] groups = distributor
                .useDistributionMethod(DistributionMethod.EVEN)
                .forAmountOfGroups(2)
                .distribute(elements);

        assertEquals(5, groups[0].size());
        assertEquals(5, groups[1].size());
    }

    /**
     * Tests whether the {@link DistributionMethod#EVEN} throws {@link UnevenDistributionException} when provided with
     * an uneven amount of elements.
     */
    @Test
    void ArrayListDistributor_Even_Uneven() {
        final ArrayList<Integer> elements = testElements(9);

        assertThrows(UnevenDistributionException.class, () ->
                distributor.useDistributionMethod(DistributionMethod.EVEN)
                        .forAmountOfGroups(2)
                        .distribute(elements));
    }

    /**
     * Tests whether the {@link DistributionMethod#SEQUENTIAL} works properly under ideal conditions.
     */
    @Test
    void ArrayListDistributor_Sequential() {
        final ArrayList<Integer> elements = testElements(10);

        final ArrayList<Integer>[] groups = distributor
                .useDistributionMethod(DistributionMethod.SEQUENTIAL)
                .withMaxGroupSize(3)
                .distribute(elements);

        assertEquals(4, groups.length);
        assertEquals(3, groups[0].size());
        assertEquals(3, groups[1].size());
        assertEquals(3, groups[2].size());
        assertEquals(1, groups[3].size());
    }

    private ArrayList<Integer> testElements(int size) {
        final ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            result.add(i);
        }
        return result;
    }
}
