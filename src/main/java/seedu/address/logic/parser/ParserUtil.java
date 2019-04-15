package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT_FACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUCCESS_RATE_RANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.FlashcardPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final double MIN_BOUND = 0;
    public static final double MAX_BOUND = 100;

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_RANGE_FORMAT = "The success rate range must be two numbers separated"
            + " by a space.";
    public static final String MESSAGE_INVALID_RANGE = "The success rate range must be between 0 to 100 (inclusive)."
            + " The first number must not be greater than the second.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
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
     * Parses a {@code String face} into an {@code Face}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code face} is invalid.
     */
    public static Face parseFace(String face) throws ParseException {
        requireNonNull(face);
        String trimmedFace = face.trim();
        if (!Face.isValidFace(trimmedFace)) {
            throw new ParseException(Face.MESSAGE_CONSTRAINTS);
        }
        return new Face(trimmedFace);
    }

    /**
     * Parses {@code Collection<String> faces} into a {@code Set<Face>}.
     */
    public static Set<Face> parseFaces(Collection<String> faces) throws ParseException {
        requireNonNull(faces);
        final Set<Face> faceSet = new HashSet<>();
        for (String faceText : faces) {
            faceSet.add(parseFace(faceText));
        }
        return faceSet;
    }

    /**
     * Parses a {@code String range} into an {@code Array}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code range} is invalid.
     */
    public static double[] parseStatRange(String range) throws ParseException {
        requireNonNull(range);
        String trimmedRange = range.trim();
        double lowerBound;
        double upperBound;
        if (trimmedRange.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_RANGE_FORMAT);
        }

        String[] rangeBounds = trimmedRange.split("\\s+");
        if (rangeBounds.length != 2) {
            throw new ParseException(MESSAGE_INVALID_RANGE_FORMAT);
        } else if (!rangeBounds[0].matches("-?\\d+(\\.\\d+)?")
                || !rangeBounds[1].matches("-?\\d+(\\.\\d+)?")) {
            throw new ParseException(MESSAGE_INVALID_RANGE_FORMAT);
        } else {
            lowerBound = Double.parseDouble(rangeBounds[0]);
            upperBound = Double.parseDouble(rangeBounds[1]);
            if (lowerBound < MIN_BOUND || upperBound > MAX_BOUND || lowerBound > upperBound) {
                throw new ParseException(MESSAGE_INVALID_RANGE);
            }
        }

        double[] parsedRange = {lowerBound, upperBound};
        return parsedRange;
    }

    /**
     * Parses the given {@code String} of arguments and returns a FlashcardPredicate
     * object.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    static FlashcardPredicate filterByKeyword(String args, String messageUsage) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FRONT_FACE, PREFIX_BACK_FACE, PREFIX_TAG,
                PREFIX_SUCCESS_RATE_RANGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_FRONT_FACE) && !arePrefixesPresent(argMultimap, PREFIX_BACK_FACE)
                && !arePrefixesPresent(argMultimap, PREFIX_TAG)
                && !arePrefixesPresent(argMultimap, PREFIX_SUCCESS_RATE_RANGE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }

        Set<Face> frontFaceKeywordSet = ParserUtil.parseFaces(argMultimap.getAllValues(PREFIX_FRONT_FACE));
        Set<Face> backFaceKeywordSet = ParserUtil.parseFaces(argMultimap.getAllValues(PREFIX_BACK_FACE));
        Set<Tag> tagKeywordSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        double[] statRange = ParserUtil.parseStatRange(argMultimap.getValue(PREFIX_SUCCESS_RATE_RANGE).isPresent()
                ? argMultimap.getValue(PREFIX_SUCCESS_RATE_RANGE).get() : MIN_BOUND + " " + MAX_BOUND);

        ArrayList<String> frontFaceKeywords = new ArrayList<>();
        ArrayList<String> backFaceKeywords = new ArrayList<>();
        ArrayList<String> tagKeywords = new ArrayList<>();

        for (Face frontFace : frontFaceKeywordSet) {
            String[] frontFaceTextSplit = frontFace.text.split("\\s+");
            frontFaceKeywords.addAll(Arrays.asList(frontFaceTextSplit));
        }

        for (Face backFace : backFaceKeywordSet) {
            String[] backFaceTextSplit = backFace.text.split("\\s+");
            backFaceKeywords.addAll(Arrays.asList(backFaceTextSplit));
        }

        for (Tag tag : tagKeywordSet) {
            tagKeywords.add(tag.tagName);
        }

        return new FlashcardPredicate(frontFaceKeywords, backFaceKeywords, tagKeywords, statRange);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
