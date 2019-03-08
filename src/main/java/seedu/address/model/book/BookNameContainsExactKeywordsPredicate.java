package seedu.address.model.book;

import java.util.function.Predicate;

/**
 * Tests that a {@code Book}'s {@code BookName} matches any of the keywords given.
 */
public class BookNameContainsExactKeywordsPredicate implements Predicate<Book> {
    private final String keywords;

    public BookNameContainsExactKeywordsPredicate(String keywords) {
        this.keywords = keywords;
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
}
