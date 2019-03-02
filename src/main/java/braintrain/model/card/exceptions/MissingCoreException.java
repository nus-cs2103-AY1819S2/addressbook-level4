package braintrain.model.card.exceptions;

/**
 * Signals that the operation is unable to find the specified core value.
 * Used by {@code Card} and {@code Lesson}.
 */
public class MissingCoreException extends RuntimeException {
    private static String MISSING_CORE_PREFIX = "Core value: "; // Used by MissingCoreException(int missingCoreIndex)
    private static String MISSING_CORE_SUFFIX = " is missing."; // Used by MissingCoreException(int missingCoreIndex)

    /**
     * Constructs a MissingCoreException with a custom detail message specifying the missing core's index.
     *
     * @param missingCoreIndex the index of the missing core in the list
     */
    public MissingCoreException(int missingCoreIndex) {
        super(MISSING_CORE_PREFIX + missingCoreIndex + MISSING_CORE_SUFFIX);
    }

    /**
     * Constructs the custom detail message specifying the missing core's index for testing by {@code LessonTest}.
     *
     * @param missingCoreIndex the index of the missing core in the list
     * @return the detail message which will be generated if a MissingCoreException occurs for a specified index.
     */
    public static String generateMessage(int missingCoreIndex) {
        final StringBuilder builder = new StringBuilder();
        builder.append(MISSING_CORE_PREFIX)
                .append(missingCoreIndex)
                .append(MISSING_CORE_SUFFIX);
        return builder.toString();
    }
}
