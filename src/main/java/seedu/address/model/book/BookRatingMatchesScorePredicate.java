package seedu.address.model.book;

import java.util.function.Predicate;

public class BookRatingMatchesScorePredicate implements Predicate<Book> {
    private final Rating rating;

    public BookRatingMatchesScorePredicate(String rating) {
        this.rating = new Rating(rating);
    }

    @Override
    public boolean test(Book book) {
        return book.getRating().equals(rating);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookRatingMatchesScorePredicate // instanceof handles nulls
                && rating.equals(((BookRatingMatchesScorePredicate) other).rating)); // state check
    }
}
