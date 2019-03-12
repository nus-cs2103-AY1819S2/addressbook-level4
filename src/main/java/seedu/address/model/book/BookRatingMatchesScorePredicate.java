package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Book}'s {@code BookRating} match the given score.
 */
public class BookRatingMatchesScorePredicate implements Predicate<Book> {
    private final List<String> keyWords;

    public BookRatingMatchesScorePredicate(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    @Override
    public boolean test(Book book) {
        return keyWords.stream()
                .anyMatch(x -> x.equals(book.getRating().value));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookRatingMatchesScorePredicate // instanceof handles nulls
                && keyWords.equals(((BookRatingMatchesScorePredicate) other).keyWords)); // state check
    }
}
