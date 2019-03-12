package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;

public class BookListFilterPredicate implements Predicate<Book> {
    private Predicate<Book> namePredicate;
    private Predicate<Book> tagPredicate;
    private Predicate<Book> ratingPredicate;

    public BookListFilterPredicate(List<String> nameStr, List<String> tagStr, List<String> ratingStr) {
        namePredicate = predicateWithNull(new BookNameContainsKeywordsPredicate(nameStr), nameStr);
        tagPredicate = predicateWithNull(new BookTagsContainExactKeywordsPredicate(tagStr), tagStr);
        ratingPredicate = predicateWithNull(new BookRatingMatchesScorePredicate(ratingStr), ratingStr);
    }

    @Override
    public boolean test(Book book) {
        return namePredicate.test(book) &&
                tagPredicate.test(book) &&
                ratingPredicate.test(book);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookListFilterPredicate // instanceof handles nulls
                && namePredicate.equals(((BookListFilterPredicate) other).namePredicate)
                && tagPredicate.equals(((BookListFilterPredicate) other).tagPredicate)
                && ratingPredicate.equals(((BookListFilterPredicate) other).ratingPredicate)
            );
    }

    /**
     * Return a proper predicate type
     */
    private Predicate<Book> predicateWithNull(Predicate<Book> pre, List<String> keywords) {
        if (keywords.isEmpty()) {
            return book -> true;
        } else {
            return pre;
        }
    }
}
