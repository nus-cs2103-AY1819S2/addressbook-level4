package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    /**
     * This is a static-methods-only (utility) class which should not be instantiated.
     * Note that this is not a singleton class given that not even a single instance is allowed.
     *
     * Throws an {@link InstantiationError} when accessed to prevent instantiation
     * via new, clone(), reflection and serialization.
     */
    private StringUtil() { }


    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Checks if a {@code List} of {@code String} has empty strings.
     * @param strings the {@code List} of {@code String} to check
     * @return true if there is one or more empty strings; false if all are non-empty
     */
    public static boolean hasEmptyStrings(List<String> strings) {
        for (String s: strings) {
            if (s.isEmpty()) {
                return true;
            }
        }

        return false;
    }


    /**
     * Truncates {@code s} if it exceed {@code maxLength} and returns it.
     *
     * @param s the string to truncate if exceeding {@code maxLength}
     * @param maxLength the maximum length of the string to be returned, if {@code s} exceeds
     * {@code maxLength}, return a {@code substring(0, maxLength -3)} of {@code s} with "..."
     * appended.
     *
     * @return the string if s < maxLength, otherwise the substring of the string of maxLength length
     */
    public static String truncateString(String s, int maxLength) {
        String output = s;
        if (s.length() > maxLength) {
            output = s.substring(0, maxLength - 3) + "...";
        }

        return output;
    }
}
