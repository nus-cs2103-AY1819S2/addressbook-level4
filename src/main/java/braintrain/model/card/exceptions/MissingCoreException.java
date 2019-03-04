package braintrain.model.card.exceptions;

/**
 * Signals that the operation is unable to find the specified core value.
 */
public class MissingCoreException extends Exception {
    private static final String MISSING_CORE_PREFIX = "There is no core at index: ";
    private static final String MISSING_CORE_SUFFIX = ".";

    /**
     * Constructs a {@code MissingCoreException} with a custom detail message specifying the missing core's index.
     *
     * @param missingCoreIndex the index of the missing core in the list
     */
    public MissingCoreException(int missingCoreIndex) {
        super(MISSING_CORE_PREFIX + missingCoreIndex + MISSING_CORE_SUFFIX);
    }

    /**
     * Generates the custom detail message which will be generated when the exception is constructed.
     *
     * @param missingCoreIndex the index of the missing core in the list
     * @return the message which will be generated if a {@code MissingCoreException} occurs for a specified index.
     */
    public static String generateMessage(int missingCoreIndex) {
        final StringBuilder builder = new StringBuilder();
        builder.append(MISSING_CORE_PREFIX)
                .append(missingCoreIndex)
                .append(MISSING_CORE_SUFFIX);
        return builder.toString();
    }
}
