package seedu.address.testutil;

import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;

/**
 * A utility class to help with building Review objects.
 */
public class ReviewBuilder {
    public static final String DEFAULT_TITLE = "A send-off fit for a wizard";
    public static final String DEFAULT_DATE = "2019/03/16 18:20:00";
    public static final String DEFAULT_MESSAGE = "There are still one or two questions left unanswered "
            + "at the end of Harry Potter's last adventure...";

    public Review build() {
        return new Review(new ReviewTitle(DEFAULT_TITLE), DEFAULT_DATE, DEFAULT_MESSAGE);
    }
}
