package seedu.address.model.book;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Book}'s {@code BookTag} containes all of the keywords given.
 */
public class BookTagsContainExactKeywordsPredicate implements Predicate<Book> {

    private final List<String> keywords;

    public BookTagsContainExactKeywordsPredicate(List<String> inputKeywords) {
        keywords = inputKeywords;
    }

    @Override
    public boolean test(Book book) {
        if (keywords.isEmpty()) {
            return true;
        }
        Iterator<Tag> itr = book.getTags().iterator();
        while (itr.hasNext()) {
            String tagStr = itr.next().tagName;
            if (keywords.stream()
                    .anyMatch(str ->
                            str.equalsIgnoreCase(tagStr))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookTagsContainExactKeywordsPredicate // instanceof handles nulls
                && keywords.containsAll(((BookTagsContainExactKeywordsPredicate) other).keywords)
                && ((BookTagsContainExactKeywordsPredicate) other).keywords.containsAll(keywords)); // state check
    }
}
