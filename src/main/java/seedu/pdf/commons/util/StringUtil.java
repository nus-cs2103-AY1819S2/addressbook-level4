package seedu.pdf.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.commons.util.AppUtil.checkArgument;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import seedu.pdf.model.pdf.Pdf;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, and a partial word match is sufficient.
     *   <br>examples:<pre>
     *       containsPartialWordIgnoreCase("ABc def", "abc") == true
     *       containsPartialWordIgnoreCase("ABc def", "DEF") == true
     *       containsPartialWordIgnoreCase("ABc def", "AB") == true
     *       containsPartialWordIgnoreCase("ABc def", "g") == false
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsPartialWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence.toLowerCase();
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(w-> w.contains(preppedWord));
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Check is case-sensitive, and a full word match is necessary.
     *   <br>examples:<pre>
     *       containsFullWordSameCase("ABc def", "abc") == false
     *       containsFullWordSameCase("ABc def", "abc def") == false
     *       containsFullWordSameCase("ABc def", "ABc def") == true
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsFullWordSameCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equals);
    }

    /**
     * Returns true if the {@code pdf} contains the {@code word}.
     *   Uses apache.pdfbox to access the contents of pdf and
     *   extract as a String.
     * @param pdf cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordInContent(Pdf pdf, String word) {
        requireNonNull(pdf);
        requireNonNull(word);

        String preppedWord = word.trim().toLowerCase();
        try {
            PDDocument document = PDDocument.load(
                    Paths.get(pdf.getDirectory().getDirectory(), pdf.getName().getFullName()).toFile());
            String preppedContent = new PDFTextStripper().getText(document).trim().toLowerCase();
            document.close();
            return preppedContent.contains(preppedWord);
        } catch (IOException e) {
            // Unable to open document or other parser error
            return false;
        }
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
}
