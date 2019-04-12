package seedu.address.model.medicalhistory;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code MedicalHistory}'s {@code Name} matches any of the keywords given.
 */
public class MedHistContainsKeywordsPredicate implements Predicate<MedicalHistory> {
    private final List<String> keywords;

    public MedHistContainsKeywordsPredicate (List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(MedicalHistory medHist) {
        Boolean writeUpContainsKeyword = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        medHist.getWriteUp().value.replaceAll("\\p{P}", " "), keyword));
        return writeUpContainsKeyword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedHistContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((MedHistContainsKeywordsPredicate) other).keywords)); // state check
    }

}

