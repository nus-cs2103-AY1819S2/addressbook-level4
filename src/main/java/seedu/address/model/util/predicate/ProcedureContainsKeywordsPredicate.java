package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.record.Record;

/**
 * Tests that a {@code Record}'s {@code Procedure} matches any of the keywords given.
 */
public class ProcedureContainsKeywordsPredicate extends ContainsKeywordsPredicate<Record> {
    public ProcedureContainsKeywordsPredicate(List<String> keywords) {
        super(keywords, true, false);
    }

    public ProcedureContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Record record) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringCaseSensitive(record.getProcedure()
                    .toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(record.getProcedure()
                    .toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringCaseSensitive(record.getProcedure()
                    .toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringIgnoreCase(record.getProcedure().toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof ProcedureContainsKeywordsPredicate
            && keywords.equals(((ProcedureContainsKeywordsPredicate) other).keywords));
    }

}
