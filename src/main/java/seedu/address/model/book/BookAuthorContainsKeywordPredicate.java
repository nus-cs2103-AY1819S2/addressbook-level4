package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Book}'s {@code AuthorName} matches any of the keywords given.
 */
public class BookAuthorContainsKeywordPredicate implements Predicate<Book> {
    private final List<String> keywords;

    public BookAuthorContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Book book) {
        if (keywords.isEmpty()) {
            return true;
        }
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(book.getAuthor().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookAuthorContainsKeywordPredicate // instanceof handles nulls
                && keywords.containsAll(((BookAuthorContainsKeywordPredicate) other).keywords)
                && ((BookAuthorContainsKeywordPredicate) other).keywords.containsAll(keywords)); // state check
    }
}
