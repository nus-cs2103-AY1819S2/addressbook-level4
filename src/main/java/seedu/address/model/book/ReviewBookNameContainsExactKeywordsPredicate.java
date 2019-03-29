package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Tests that a {@code Review}'s {@code Title} matches all of the keywords given.
 */
public class ReviewBookNameContainsExactKeywordsPredicate implements Predicate<Review> {
    private final String keywords;

    public ReviewBookNameContainsExactKeywordsPredicate(BookName bookName) {
        requireNonNull(bookName);
        this.keywords = bookName.fullName;
    }

    @Override
    public boolean test(Review review) {
        return keywords.equalsIgnoreCase(review.getBookName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReviewBookNameContainsExactKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ReviewBookNameContainsExactKeywordsPredicate) other).keywords)); // state check
    }
}
