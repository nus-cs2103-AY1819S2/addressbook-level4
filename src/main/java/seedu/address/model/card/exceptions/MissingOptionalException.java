package seedu.address.model.card.exceptions;

/**
 * Signals that the operation is unable to find the specified optional value.
 */
public class MissingOptionalException extends Exception {
    private static final String MISSING_OPTIONAL_PREFIX = "There is no optional at index: ";
    private static final String MISSING_OPTIONAL_SUFFIX = ".";

    /**
     * Constructs a {@code MissingOptionalException} with a custom detail message specifying the missing
     * optional's index.
     *
     * @param missingOptionalIndex the index of the missing optional in the list
     */
    public MissingOptionalException(int missingOptionalIndex) {
        super(MISSING_OPTIONAL_PREFIX + missingOptionalIndex + MISSING_OPTIONAL_SUFFIX);
    }

    /**
     * Generates the custom detail message which will be generated when the exception is constructed.
     *
     * @param missingOptionalIndex the index of the missing optional in the list
     * @return the message which will be generated if a {@code MissingOptionalException} occurs for a specified index.
     */
    public static String generateMessage(int missingOptionalIndex) {
        final StringBuilder builder = new StringBuilder();
        builder.append(MISSING_OPTIONAL_PREFIX)
                .append(missingOptionalIndex)
                .append(MISSING_OPTIONAL_SUFFIX);
        return builder.toString();
    }
}
