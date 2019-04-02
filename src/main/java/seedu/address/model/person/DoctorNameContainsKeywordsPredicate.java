/* @@author siyingpoof */

package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Doctor}'s {@code Name} matches any of the keywords given.
 */
public class DoctorNameContainsKeywordsPredicate implements Predicate<Doctor> {
    private final List<String> keywords;

    public DoctorNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Doctor doctor) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(doctor.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoctorNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DoctorNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
