package seedu.address.model.book;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Book}'s {@code BookRating} match the given score.
 */
public class BookRatingMatchesScorePredicate implements Predicate<Book> {
    private final List<Rating> rating;

    public BookRatingMatchesScorePredicate(List<String> ratingStr) {
        rating = new ArrayList<>();
        for (String str : ratingStr) {
            rating.add(new Rating(str));
        }
    }

    @Override
    public boolean test(Book book) {
        return rating.contains(book.getRating());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookRatingMatchesScorePredicate // instanceof handles nulls
                && rating.equals(((BookRatingMatchesScorePredicate) other).rating)); // state check
    }
}
