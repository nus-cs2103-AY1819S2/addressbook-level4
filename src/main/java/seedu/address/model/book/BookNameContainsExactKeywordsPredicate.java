package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Tests that a {@code Book}'s {@code BookName} matches any of the keywords given.
 */
public class BookNameContainsExactKeywordsPredicate implements Predicate<Book> {
    private final String keywords;

    public BookNameContainsExactKeywordsPredicate(BookName bookName) {
        requireNonNull(bookName);
        this.keywords = bookName.fullName;
    }

    @Override
    public boolean test(Book book) {
        return keywords.equalsIgnoreCase(book.getBookName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookNameContainsExactKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((BookNameContainsExactKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public String toString() {
        return keywords;
    }
}
