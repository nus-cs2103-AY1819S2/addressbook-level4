package braintrain.model.card.exceptions;

/**
 * Signals that the operation is unable to find the specified core value.
 * Used by {@code Card} and {@code Lesson} when a List input is provided and there is no cores in the list.
 */
public class MissingCoreException extends RuntimeException {
    private static String MISSING_CORE_PREFIX = "Core value: "; // Using by MissingCoreException(int coreIndex)
    private static String MISSING_CORE_SUFFIX = " is missing."; // Using by MissingCoreException(int coreIndex)

    /**
     * Constructs a MissingCoreException with a custom detail message.
     *
     * @param message the detail message to append to the RunTimeException message constructor
     */
    public MissingCoreException(String message) {
        super(message);
    }

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
     * @param missingCoreIndex The index of the missing core in the list
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
