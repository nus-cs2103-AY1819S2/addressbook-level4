package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Author;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Rating;
import seedu.address.model.book.ReviewTitle;
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
     * Parses a {@code String name} into a {@code BookName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code BookName} is invalid.
     */
    public static BookName parseBookName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!BookName.isValidBookName(trimmedName)) {
            throw new ParseException(BookName.MESSAGE_CONSTRAINTS);
        }
        return new BookName(trimmedName);
    }

    /**
     * * Parses a {@code String name} into a {@code Author}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code BookName} is invalid.
     */
    public static Author parseAuthor(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Author.isValidAuthor(trimmedName)) {
            throw new ParseException(Author.MESSAGE_CONSTRAINTS);
        }
        return new Author(trimmedName);
    }

    /**
     * Parses a {@code String reviewMessage} into a {@code String parsedReviewMessage}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code review} is invalid.
     */
    public static String parseReview(String reviewMessage) throws ParseException {
        requireNonNull(reviewMessage);
        String trimmedReview = reviewMessage.trim();
        return trimmedReview;
    }

    /**
     * Parses a {@code String name} into a {@code ReviewTitle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ReviewTitle} is invalid.
     */
    public static ReviewTitle parseReviewTitle(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!ReviewTitle.isValidReviewTitle(trimmedName)) {
            throw new ParseException(ReviewTitle.MESSAGE_CONSTRAINTS);
        }
        return new ReviewTitle(trimmedName);
    }

    /**
     * Parses a {@code String rating} into a {@code Rating}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Rating} is invalid.
     */
    public static Rating parseRating(String rating) throws ParseException {
        requireNonNull(rating);
        String trimmedRating = rating.trim();
        if (!Rating.isValidRating(trimmedRating)) {
            throw new ParseException(Rating.MESSAGE_CONSTRAINTS);
        }
        return new Rating(trimmedRating);
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
}
