package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PatientTagContainsKeywordsPredicate implements Predicate<Patient> {
    private final List<String> keywords;

    public PatientTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Patient patient) {
        final String tags = patient.getTags().toString()
                .replaceAll("\\[", "").replaceAll("\\]", "");
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tags, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PatientTagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PatientTagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
