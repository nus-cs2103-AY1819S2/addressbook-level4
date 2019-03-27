package seedu.address.testutil;

import seedu.address.model.book.BookName;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;

/**
 * A utility class to help with building Review objects.
 */
public class ReviewBuilder {
    public static final String DEFAULT_TITLE = "A send off fit for a wizard";
    public static final String DEFAULT_BOOKNAME = "Harry Potter and the Sorcerer's Stone";
    public static final String DEFAULT_DATE = "2019/03/16 18:20:00";
    public static final String DEFAULT_MESSAGE = "There are still one or two questions left unanswered "
            + "at the end of Harry Potter's last adventure...";

    /**
     * Builds a review.
     */
    public static Review build() {
        return new Review(
                new ReviewTitle(DEFAULT_TITLE), new BookName(DEFAULT_BOOKNAME), DEFAULT_DATE, DEFAULT_MESSAGE);
    }
}
