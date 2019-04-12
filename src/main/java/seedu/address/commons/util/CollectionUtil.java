package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {
    /**
     * This is a static-methods-only (utility) class which should not be instantiated.
     * Note that this is not a singleton class given that not even a single instance is allowed.
     *
     * Throws an {@link InstantiationError} when accessed to prevent instantiation
     * via new, clone(), reflection and serialization.
     */
    private CollectionUtil() {
        // Prevents instantiation via new, clone(), reflection and serialization.
        throw new InstantiationError(
                "This is a static-methods-only (utility) class which should not be instantiated.");
    }

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
}
