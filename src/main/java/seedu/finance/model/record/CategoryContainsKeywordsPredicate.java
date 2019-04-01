package seedu.finance.model.record;

import java.util.List;
import java.util.function.Predicate;

import seedu.finance.commons.util.StringUtil;
import seedu.finance.model.category.Category;


/**
 * Tests that a {@code Record}'s {@code Category} matches any of the keywords given.
 */
public class CategoryContainsKeywordsPredicate implements Predicate<Record> {
    private final List<String> keywords;

    public CategoryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Record record) {
        Category category = record.getCategory();
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(category.toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoryContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CategoryContainsKeywordsPredicate) other).keywords)); // state check
    }

}
