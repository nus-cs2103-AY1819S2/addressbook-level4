package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Book}'s {@code BookRating} match the given score.
 */
public class BookRatingMatchesScorePredicate implements Predicate<Book> {
    private final List<String> keywords;

    public BookRatingMatchesScorePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Book book) {
        if (keywords.isEmpty()) {
            return true;
        }
        return keywords.stream()
                .anyMatch(x -> x.equals(book.getRating().value));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookRatingMatchesScorePredicate // instanceof handles nulls
                && keywords.containsAll(((BookRatingMatchesScorePredicate) other).keywords)
                && ((BookRatingMatchesScorePredicate) other).keywords.containsAll(keywords)); // state check
    }
}
