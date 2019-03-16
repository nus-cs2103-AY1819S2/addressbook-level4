package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.patient.Patient;


/**
 * Tests that a {@code Patient}'s {@code DateOfBirth} matches any of the keywords given.
 */
public class DateOfBirthContainsKeywordsPredicate extends ContainsKeywordsPredicate<Patient> {

    public DateOfBirthContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Patient patient) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getDateOfBirth()
                .getDate(), keyword));
    }
}
