package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * A book review.
 */
public class Review {
    public final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public final ReviewTitle title;
    public final String reviewMessage;
    public final String dateCreated;

    /**
     * Constructs a {@code Review}.
     *
     * @param title the title of the review.
     * @param message the content of the review.
     */
    public Review(ReviewTitle title, String message) {
        requireNonNull(message);
        requireNonNull(title);
        this.title = title;
        reviewMessage = message;
        dateCreated = dateFormat.format(new Date());
    }

    /**
     * Constructs a {@code Review}.
     *
     * @param title the title of the review.
     * @param date the date of the review.
     * @param message the content of the review.
     */
    public Review(ReviewTitle title, String date, String message) {
        requireNonNull(message);
        requireNonNull(title);
        this.title = title;
        reviewMessage = message;
        dateCreated = date;
    }

    public ReviewTitle getTitle() {
        return title;
    }

    public String getContent() {
        return reviewMessage;
    }

    public String getDate() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return title.fullName + "\r\n" + dateCreated + ": \r\n" + reviewMessage;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Review // instanceof handles nulls
                && title.equals(((Review) other).title)
                && reviewMessage.equals(((Review) other).reviewMessage)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.reviewMessage, this.dateCreated);
    }
}
