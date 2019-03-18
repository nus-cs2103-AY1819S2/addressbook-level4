package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.model.modelmanager.quiz.Quiz;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * //TODO: exception
 */
public class ParserUtil {

    /**
     * Parses a {@code String name} into a {@code String name}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static String parseName(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        return trimmedName;
    }

    /**
     * Parses a {@code String count} into a {@code int count}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static int parseCount(String count) {
        requireNonNull(count);
        String trimmedCount = count.trim();
        return Integer.parseInt(trimmedCount);
    }

    /**
     * Parses a {@code String mode} into an {@code Quiz.mode mode}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Quiz.Mode parseMode(String mode) {
        requireNonNull(mode);
        String trimmedMode = mode.trim().toUpperCase();
        return Quiz.Mode.valueOf(trimmedMode);
    }
}
