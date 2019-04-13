package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.record.Record;


/**
 * Tests that a {@code Record}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionRecordContainsKeywordsPredicate extends ContainsKeywordsPredicate<Record> {
    public DescriptionRecordContainsKeywordsPredicate(List<String> keywords) {
        super(keywords, true, false);
    }

    public DescriptionRecordContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Record record) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordCaseSensitive(record.getDescription()
                    .toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(record.getDescription()
                    .toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordCaseSensitive(record.getDescription()
                    .toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(record.getDescription().toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof DescriptionRecordContainsKeywordsPredicate
            && keywords.equals(((DescriptionRecordContainsKeywordsPredicate) other).keywords));
    }
}
