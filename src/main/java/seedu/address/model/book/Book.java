package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.tag.Tag;

/**
 * Represents a Book in the bookShelf.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {

    // Identity fields
    private final BookName bookName;
    private final Author author;

    // Data fields
    private final Rating rating;
    private final Set<Tag> tags = new HashSet<>();
    private Set<Review> reviews = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Book(BookName bookName, Author author, Rating rating, Set<Tag> tags) {
        requireAllNonNull(bookName, author, rating, tags);
        this.bookName = bookName;
        this.author = author;
        this.rating = rating;
        this.tags.addAll(tags);
    }

    /**
     * An alternative constructor with the review field.
     */
    public Book(BookName bookName, Author author, Rating rating, Set<Tag> tags, Set<Review> reviews) {
        requireAllNonNull(bookName, author, rating, tags);
        this.bookName = bookName;
        this.author = author;
        this.rating = rating;
        this.tags.addAll(tags);
        this.reviews.addAll(reviews);
    }

    public BookName getBookName() {
        return bookName;
    }

    public Author getAuthor() {
        return author;
    }

    public Rating getRating() {
        return rating;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable review set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Review> getReviews() {
        return Collections.unmodifiableSet(reviews);
    }

    public Set<Review> getModifiableReviews() {
        return reviews.stream().collect(Collectors.toSet());
    }

    /**
     * Returns true if both books of the same book name.
     * This defines a weaker notion of equality between two books.
     */
    public boolean isSameBook(Book otherBook) {
        if (otherBook == this) {
            return true;
        }

        return otherBook != null
                && otherBook.getBookName().equals(getBookName());
    }

    /**
     * Returns true if both books have the same identity and data fields.
     * This defines a stronger notion of equality between two books.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Book)) {
            return false;
        }

        Book otherBook = (Book) other;
        return otherBook.getBookName().equals(getBookName())
                && otherBook.getAuthor().equals(getAuthor())
                && otherBook.getRating().equals(getRating())
                && otherBook.getTags().equals(getTags())
                && otherBook.getReviews().equals(getReviews());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(bookName, author, rating, tags, reviews);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getBookName())
                .append(" Author: ")
                .append(getAuthor())
                .append(" Rating: ")
                .append(getRating())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
