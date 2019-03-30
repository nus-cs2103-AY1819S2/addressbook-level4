package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedReview.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalBooks.CS_REVIEW;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.BookName;
import seedu.address.model.book.ReviewTitle;
import seedu.address.testutil.Assert;

public class JsonAdaptedReviewTest {
    private static final String INVALID_BOOKNAME = "The K&d";
    private static final String INVALID_REVIEW_TITLE = "100%";

    private static final String VALID_REVIEW_TITLE = CS_REVIEW.getTitle().toString();
    private static final String VALID_BOOKNAME = CS_REVIEW.getBookName().toString();
    private static final String VALID_DATE = CS_REVIEW.getDateCreated();
    private static final String VALID_MESSAGE = CS_REVIEW.getReviewMessage();

    @Test
    public void toModelType_validReviewDetails_returnsreview() throws Exception {
        JsonAdaptedReview review = new JsonAdaptedReview(CS_REVIEW);
        assertEquals(CS_REVIEW, review.toModelType());
    }

    @Test
    public void toModelType_invalidReviewTitle_throwsIllegalValueException() {
        JsonAdaptedReview review =
                new JsonAdaptedReview(INVALID_REVIEW_TITLE, VALID_BOOKNAME, VALID_DATE, VALID_MESSAGE);
        String expectedMessage = ReviewTitle.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, review::toModelType);
    }

    @Test
    public void toModelType_nullReviewTitle_throwsIllegalValueException() {
        JsonAdaptedReview review = new JsonAdaptedReview(null, VALID_BOOKNAME, VALID_DATE, VALID_MESSAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ReviewTitle.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, review::toModelType);
    }

    @Test
    public void toModelType_invalidBookName_throwsIllegalValueException() {
        JsonAdaptedReview review =
                new JsonAdaptedReview(VALID_REVIEW_TITLE, INVALID_BOOKNAME, VALID_DATE, VALID_MESSAGE);
        String expectedMessage = BookName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, review::toModelType);
    }

    @Test
    public void toModelType_nullBookName_throwsIllegalValueException() {
        JsonAdaptedReview review = new JsonAdaptedReview(VALID_REVIEW_TITLE, null, VALID_DATE, VALID_MESSAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, BookName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, review::toModelType);
    }

    @Test
    public void toModelType_nullReviewMessage_throwsIllegalValueException() {
        JsonAdaptedReview review = new JsonAdaptedReview(VALID_REVIEW_TITLE, VALID_BOOKNAME, VALID_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Review Content");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, review::toModelType);
    }
}
