package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.CourseName;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Hour;
import seedu.address.model.moduletaken.Semester;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String FINISHED_STATUS_TRUE = "y";

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
     * Parses a {@code String moduleInfoCode} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleInfoCode} is invalid.
     */
    public static ModuleInfoCode moduleInfoCode(String moduleInfoCode) throws ParseException {
        requireNonNull(moduleInfoCode);
        String trimmedName = moduleInfoCode.trim().toUpperCase();
        if (!ModuleInfoCode.isValidModuleInfoCode(trimmedName)) {
            throw new ParseException(ModuleInfoCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleInfoCode(trimmedName);
    }

    /**
     * Parses a {@code String courseName} into a {@code CourseName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code courseName} is invalid.
     */
    public static CourseName parseCourseName(String courseName) throws ParseException {
        requireNonNull(courseName);
        String trimmedCourseName = courseName.trim();
        if (!CourseName.isValidCourseName(trimmedCourseName)) {
            throw new ParseException(CourseName.MESSAGE_CONSTRAINTS);
        }
        return new CourseName(trimmedCourseName);
    }

    /**
     * Parses a {@code String semester} into a {@code Semester}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code semester} is invalid.
     */
    public static Semester parseSemester(String semester) throws ParseException {
        requireNonNull(semester);
        String trimmedSemester = semester.trim().toUpperCase();
        if (!Semester.isValidSemesterForTakingModules(trimmedSemester)) {
            throw new ParseException(Semester.MESSAGE_CONSTRAINTS);
        }
        return Semester.valueOf(trimmedSemester);
    }

    /**
     * Parses a {@code String grade} into an {@code Grade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static Grade parseGrade(String grade) throws ParseException {
        requireNonNull(grade);
        String trimmedGrade = grade.trim().toUpperCase();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new ParseException(Grade.MESSAGE_CONSTRAINTS);
        }
        return Grade.getGrade(trimmedGrade);
    }

    /**
     * Parses a {@code String hour} into an {@code Hour}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Hour} is invalid.
     */
    public static Hour parseHour(String hour) throws ParseException {
        requireNonNull(hour);
        String trimmedHour = hour.trim();
        if (!Hour.isValidHour(trimmedHour)) {
            throw new ParseException(Hour.MESSAGE_CONSTRAINTS);
        }
        return new Hour(trimmedHour);
    }

    /**
     * Parses a {@code String cap} into an {@code CapAverage}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code CapAverage} is invalid.
     */
    public static CapAverage parseCap(String cap) throws ParseException {
        requireNonNull(cap);
        String trimmedCap = cap.trim();
        if (!CapAverage.isValidCapAverage(trimmedCap)) {
            throw new ParseException(CapAverage.MESSAGE_CONSTRAINTS);
        }
        return new CapAverage(Double.parseDouble(trimmedCap));
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
     * Parses a {@code finishedStatus} string into a boolean.
     * @param finishedStatus Any string.
     * @return true if the string is "y" (case-insensitive), false otherwise.
     */
    public static boolean parseFinishedStatus(String finishedStatus) {
        requireNonNull(finishedStatus);

        return finishedStatus.trim().toLowerCase().equals(FINISHED_STATUS_TRUE);
    }

    /**
     * Converts a boolean to a String representing the corresponding finished status.
     * @param isFinished The finished status.
     * @return "y" if isFinished, "n" otherwise.
     */
    public static String booleanToFinishedStatus(boolean isFinished) {
        if (isFinished) {
            return FINISHED_STATUS_TRUE;
        }
        return "n"; // can be any string other than "y"
    }
}
