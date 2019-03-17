package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Tests that a {@code Review}'s {@code Title} matches all of the keywords given.
 */
public class ReviewNameContainsExactKeywordsPredicate implements Predicate<Review> {
    private final String keywords;

    public ReviewNameContainsExactKeywordsPredicate(ReviewTitle reviewTitle) {
        requireNonNull(reviewTitle);
        this.keywords = reviewTitle.fullName;
    }

    @Override
    public boolean test(Review review) {
        return keywords.equalsIgnoreCase(review.getTitle().fullName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReviewNameContainsExactKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ReviewNameContainsExactKeywordsPredicate) other).keywords)); // state check
    }
}
