package seedu.address.testutil;

import seedu.address.model.book.BookName;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;

/**
 * A utility class to help with building Review objects.
 */
public class ReviewBuilder {
    public static final String DEFAULT_TITLE = "A send off fit for a wizard";
    public static final String DEFAULT_BOOKNAME = "Harry Potter and the Sorcerers Stone";
    public static final String DEFAULT_DATE = "2019/03/16 18:20:00";
    public static final String DEFAULT_MESSAGE = "There are still one or two questions left unanswered "
            + "at the end of Harry Potter's last adventure...";
    private ReviewTitle reviewTitle;
    private BookName bookName;
    private String dateCreated;
    private String reviewMessage;


    public ReviewBuilder() {
        reviewTitle = new ReviewTitle(DEFAULT_TITLE);
        bookName = new BookName(DEFAULT_BOOKNAME);
        dateCreated = DEFAULT_DATE;
        reviewMessage = DEFAULT_MESSAGE;
    }

    /**
     * Initializes the ReviewBuilder with the data of {@code reviewToCopy}.
     */
    public ReviewBuilder(Review reviewToCopy) {
        reviewTitle = reviewToCopy.getTitle();
        bookName = reviewToCopy.getBookName();
        dateCreated = reviewToCopy.getDateCreated();
        reviewMessage = reviewToCopy.getReviewMessage();
    }

    /**
     * Sets the {@code ReviewTitle} of the {@code Review} that we are building.
     */
    public ReviewBuilder withReviewTitle(String reviewTitle) {
        this.reviewTitle = new ReviewTitle(reviewTitle);
        return this;
    }

    /**
     * Sets the {@code BookName} of the {@code Review} that we are building.
     */
    public ReviewBuilder withBookName(String bookName) {
        this.bookName = new BookName(bookName);
        return this;
    }

    /**
     * Sets the {@code String} of the {@code Review} that we are building.
     */
    public ReviewBuilder withDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    /**
     * Sets the {@code String} of the {@code Review} that we are building.
     */
    public ReviewBuilder withReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
        return this;
    }

    /**
     * Builds a book.
     */
    public Review build() {
        return new Review(reviewTitle, bookName, dateCreated, reviewMessage);
    }
}
