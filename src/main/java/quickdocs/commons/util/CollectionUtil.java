package quickdocs.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    /** @see #requireAllNonNull(Collection) */
    public static void requireAllNonNull(Object... items) {
        requireNonNull(items);
        Stream.of(items).forEach(Objects::requireNonNull);
    }

    /**
     * Throws NullPointerException if {@code items} or any element of {@code items} is null.
     */
    public static void requireAllNonNull(Collection<?> items) {
        requireNonNull(items);
        items.forEach(Objects::requireNonNull);
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }


    /**
     * A supporting function to find an element with the special key in a sorted arraylist
     * @param list the list to search the element with the key in
     * @param test a function that gives -1 if the operand's field of interest is less than the key;
     *            0 if equal; 1 if larger than
     * @param <T> the type of the element of the list
     * @return if the element with key exists in the list, return Optional.of that element; else return Optional.empty()
     */
    public static <T> Optional<T> binarySearch(ArrayList<T> list, Function<T, Integer> test) {
        int left = 0;
        int right = list.size() - 1;
        while (true) {
            if (left > right) {
                return Optional.empty();
            }
            if (left == right) {
                if (test.apply(list.get(left)) == 0) {
                    return Optional.of(list.get(left));
                } else {
                    return Optional.empty();
                }
            }
            int mid = (left + right) / 2;
            int testResult = test.apply(list.get(mid));
            if (testResult < 0) {
                left = mid + 1;
            } else if (testResult == 0) {
                return Optional.of(list.get(mid));
            } else {
                right = mid - 1;
            }
        }
    }
}
