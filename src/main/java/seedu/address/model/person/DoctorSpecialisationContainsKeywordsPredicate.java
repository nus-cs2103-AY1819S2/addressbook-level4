package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Doctor}'s {@code Specialisation} matches any of the keywords given.
 */
public class DoctorSpecialisationContainsKeywordsPredicate implements Predicate<Doctor> {
    private final List<String> keywords;

    public DoctorSpecialisationContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Doctor doctor) {
        final String specs = doctor.getSpecs().toString()
                .replaceAll("\\[", "").replaceAll("\\]", "");
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(specs, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoctorSpecialisationContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DoctorSpecialisationContainsKeywordsPredicate) other).keywords)); // state check
    }

}
