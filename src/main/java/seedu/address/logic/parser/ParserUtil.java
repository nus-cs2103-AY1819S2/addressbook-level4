package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.pdf.Deadline;
import seedu.address.model.pdf.Directory;
import seedu.address.model.pdf.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String filePath} into an {@code File}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code File} is invalid.
     */
    public static File parseFile(String filePath) throws ParseException {
        requireNonNull(filePath);
        String trimmedFilePath = filePath.trim();
        File file = new File(trimmedFilePath);
        if (!file.exists()) {
            throw new ParseException("File does not exist.", new FileNotFoundException());
        }
        return file;
    }

    /**
     * Parses a {@code String directory} into a {@code Directory}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code directory} is invalid.
     */
    public static Directory parseDirectory(String directory) throws ParseException {
        requireNonNull(directory);
        String trimmedDirectory = directory.trim();
        File file = new File(trimmedDirectory);
        if (!file.isDirectory()) {
            throw new ParseException("Directory unavailable.", new FileNotFoundException());
        }
        return new Directory(trimmedDirectory);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a DD-MM-YYYY string input into a Deadline Object
     *
     * @param deadline = String in DD-MM-YYYY format.
     * @return Constructed valid Deadline Object
     * @throws ParseException - If input does not match requirements.
     */
    public static Deadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);

        final int positionDay = 0;
        final int positionMonth = 1;
        final int positionYear = 2;
        final String parameterSeperator = "-";
        final String dateError = "Invalid Date Format/Value";

        String[] dates = deadline.split(parameterSeperator);

        for (String s : dates) {
            if (s.length() == 0 || s.length() > 4) {
                throw new ParseException(dateError);
            }
        }

        if (dates[positionDay].length() > 2 || dates[positionMonth].length() > 2
                || dates[positionYear].length() > 4) {
            throw new ParseException(dateError);
        }

        try {
            return new Deadline(Integer.parseInt(dates[positionDay]), Integer.parseInt(dates[positionMonth]),
                    Integer.parseInt(dates[positionYear]));
        } catch (DateTimeException e) {
            throw new ParseException(dateError);
        }

    }

}
