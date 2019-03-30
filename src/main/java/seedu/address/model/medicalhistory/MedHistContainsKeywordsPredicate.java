package seedu.address.model.medicalhistory;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.function.Predicate;

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

        Boolean nameContainsKeyword = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(medHist.getName().fullName, keyword));
        Boolean writeUpContainsKeyword = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(medHist.getWriteUp().value, keyword));
        return nameContainsKeyword || writeUpContainsKeyword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedHistContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((MedHistContainsKeywordsPredicate) other).keywords)); // state check
    }

}

