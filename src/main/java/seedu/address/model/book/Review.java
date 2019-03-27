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
    public final BookName bookName;
    public final String reviewMessage;
    public final String dateCreated;

    /**
     * Constructs a {@code Review}.
     *
     * @param title the title of the review.
     * @param bookname the name of the reviewed book.
     * @param message the content of the review.
     */
    public Review(ReviewTitle title, BookName bookname, String message) {
        requireNonNull(bookname);
        requireNonNull(message);
        requireNonNull(title);
        this.title = title;
        this.bookName = bookname;
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
    public Review(ReviewTitle title, BookName bookName, String date, String message) {
        requireNonNull(bookName);
        requireNonNull(message);
        requireNonNull(title);
        requireNonNull(date);
        this.title = title;
        this.bookName = bookName;
        reviewMessage = message;
        dateCreated = date;
    }

    public ReviewTitle getTitle() {
        return title;
    }


    public BookName getBookName() {
        return bookName;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return title.fullName + "\r\nBook: " + bookName.fullName + ": \r\n" + dateCreated + ": \r\n" + reviewMessage;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Review // instanceof handles nulls
                && title.equals(((Review) other).title)
                && bookName.equals(((Review) other).bookName)
                && reviewMessage.equals(((Review) other).reviewMessage)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.reviewMessage, this.dateCreated);
    }
}
