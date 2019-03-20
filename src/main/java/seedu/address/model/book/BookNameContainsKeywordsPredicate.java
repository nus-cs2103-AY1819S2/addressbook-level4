package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Book}'s {@code BookName} matches any of the keywords given.
 */
public class BookNameContainsKeywordsPredicate implements Predicate<Book> {
    private final List<String> keywords;

    public BookNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Book book) {
        if (keywords.isEmpty()) {
            return true;
        }
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(book.getBookName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.containsAll(((BookNameContainsKeywordsPredicate) other).keywords)
                && ((BookNameContainsKeywordsPredicate) other).keywords.containsAll(keywords)); // state check
    }
}
