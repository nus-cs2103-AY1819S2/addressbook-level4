package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Book}'s {@code BookName, BookTag, BookRating} matches any of the keywords given.
 */
public class BookListFilterPredicate implements Predicate<Book> {
    private Predicate<Book> namePredicate;
    private Predicate<Book> authorPredicate;
    private Predicate<Book> tagPredicate;
    private Predicate<Book> ratingPredicate;

    public BookListFilterPredicate(List<String> nameStr,
            List<String> authorStr, List<String> tagStr, List<String> ratingStr) {
        namePredicate = new BookNameContainsKeywordsPredicate(nameStr);
        authorPredicate = new BookAuthorContainsKeywordPredicate(authorStr);
        tagPredicate = new BookTagsContainExactKeywordsPredicate(tagStr);
        ratingPredicate = new BookRatingMatchesScorePredicate(ratingStr);
    }

    @Override
    public boolean test(Book book) {
        return namePredicate.test(book)
                && authorPredicate.test(book)
                && tagPredicate.test(book)
                && ratingPredicate.test(book);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookListFilterPredicate // instanceof handles nulls
                && namePredicate.equals(((BookListFilterPredicate) other).namePredicate)
                && authorPredicate.equals(((BookListFilterPredicate) other).authorPredicate)
                && tagPredicate.equals(((BookListFilterPredicate) other).tagPredicate)
                && ratingPredicate.equals(((BookListFilterPredicate) other).ratingPredicate)
            );
    }
}
