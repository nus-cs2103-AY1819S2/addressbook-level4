package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedBook.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalBooks.CS;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Author;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Rating;
import seedu.address.testutil.Assert;

public class JsonAdaptedBookTest {
    private static final String INVALID_BOOKNAME = "The K&d";
    private static final String INVALID_AUTHOR = "J.K";
    private static final String INVALID_RATING = "11";
    private static final String INVALID_TAG = "#good";

    private static final String VALID_BOOKNAME = CS.getBookName().toString();
    private static final String VALID_AUTHOR = CS.getAuthor().toString();
    private static final String VALID_RATING = CS.getRating().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = CS.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedReview> VALID_REVIEWS = CS.getReviews().stream()
            .map(JsonAdaptedReview::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validBookDetails_returnsbook() throws Exception {
        JsonAdaptedBook book = new JsonAdaptedBook(CS);
        assertEquals(CS, book.toModelType());
    }

    @Test
    public void toModelType_invalidBookName_throwsIllegalValueException() {
        JsonAdaptedBook book =
                new JsonAdaptedBook(INVALID_BOOKNAME, VALID_AUTHOR, VALID_RATING, VALID_TAGS);
        String expectedMessage = BookName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullBookName_throwsIllegalValueException() {
        JsonAdaptedBook book = new JsonAdaptedBook(null, VALID_AUTHOR, VALID_RATING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, BookName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidAuthor_throwsIllegalValueException() {
        JsonAdaptedBook book =
                new JsonAdaptedBook(VALID_BOOKNAME, INVALID_AUTHOR, VALID_RATING, VALID_TAGS);
        String expectedMessage = Author.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullAuthor_throwsIllegalValueException() {
        JsonAdaptedBook book = new JsonAdaptedBook(VALID_BOOKNAME, null, VALID_RATING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        JsonAdaptedBook book =
                new JsonAdaptedBook(VALID_BOOKNAME, VALID_AUTHOR, INVALID_RATING, VALID_TAGS);
        String expectedMessage = Rating.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullRating_throwsIllegalValueException() {
        JsonAdaptedBook book = new JsonAdaptedBook(VALID_BOOKNAME, VALID_AUTHOR, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedBook book =
                new JsonAdaptedBook(VALID_BOOKNAME, VALID_AUTHOR, VALID_RATING, invalidTags);
        Assert.assertThrows(IllegalValueException.class, book::toModelType);
    }

}
