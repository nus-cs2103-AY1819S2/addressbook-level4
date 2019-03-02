package braintrain.model.card.exceptions;

/**
 * Signals that the operation is unable to find the specified optional value.
 * Used by {@code Card}.
 */
public class MissingOptionalException extends RuntimeException {
    private static String MISSING_OPTIONAL_PREFIX = "Optional value: ";
    // Used by MissingOptionalException(int missingOptionalIndex)
    private static String MISSING_OPTIONAL_SUFFIX = " is missing.";
    // Used by MissingOptionalException(int missingOptionalIndex)

    /**
     * Constructs a MissingOptionalException with a custom detail message.
     *
     * @param message the detail message to append to the RunTimeException message constructor
     */
    public MissingOptionalException(String message) {
        super(message);
    }

    /**
     * Constructs a MissingOptionalException with a custom detail message specifying the missing optional's index.
     *
     * @param missingOptionalIndex the index of the missing optional in the list
     */
    public MissingOptionalException(int missingOptionalIndex) {
        super(MISSING_OPTIONAL_PREFIX + missingOptionalIndex + MISSING_OPTIONAL_SUFFIX);
    }

    /**
     * Constructs the custom detail message specifying the missing optional's index for testing by {@code LessonTest}.
     *
     * @param missingOptionalIndex the index of the missing optional in the list
     * @return the detail message which will be generated if a MissingOptionalException occurs for a specified index.
     */
    public static String generateMessage(int missingOptionalIndex) {
        final StringBuilder builder = new StringBuilder();
        builder.append(MISSING_OPTIONAL_PREFIX)
                .append(missingOptionalIndex)
                .append(MISSING_OPTIONAL_SUFFIX);
        return builder.toString();
    }
}
