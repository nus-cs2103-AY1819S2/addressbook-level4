package seedu.address.model.medicine.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.Medicine;

/**
 * Tests that a {@code Medicine}'s {@code Batch} matches any of the keywords given.
 */
public class BatchContainsKeywordsPredicate implements Predicate<Medicine> {
    private final List<String> keywords;

    public BatchContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Medicine medicine) {
        for (Batch batch: medicine.getBatches().values()) {
            if (keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(batch.getBatchNumber()
                            .batchNumber, keyword))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BatchContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((BatchContainsKeywordsPredicate) other).keywords)); // state check
    }

}
