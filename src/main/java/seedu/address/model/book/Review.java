package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * A review.
 */
public class Review {
    public final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public final String reviewMessage;
    public final String dateCreated;

    /**
     * Constructs a {@code Review}.
     *
     * @param message the content of the review.
     */
    public Review(String message) {
        requireNonNull(message);
        reviewMessage = message;
        dateCreated = dateFormat.format(new Date());
    }

    @Override
    public String toString() {
        return dateCreated + ": \r\n" + reviewMessage;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rating // instanceof handles nulls
                && dateCreated.equals(((Review) other).dateCreated)
                && reviewMessage.equals(((Review) other).reviewMessage)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.reviewMessage, this.dateCreated);
    }
}
