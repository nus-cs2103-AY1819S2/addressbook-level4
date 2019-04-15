/* @@author siyingpoof */

package seedu.address.model.person.doctor;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.DoctorUtil;

/**
 * Tests that a {@code Doctor}'s {@code Name} matches any of the keywords given.
 */
public class DoctorContainsKeywordsPredicate implements Predicate<Doctor> {
    private final List<String> keywords;

    public DoctorContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Doctor doctor) {
        boolean nameTest = keywords.stream()
                .anyMatch(keyword -> DoctorUtil.containsKeyword(doctor.getName().fullName, keyword));
        boolean phoneTest = keywords.stream()
                .anyMatch(keyword -> DoctorUtil.containsKeyword(doctor.getPhone().value, keyword));
        boolean genderTest = keywords.stream()
                .anyMatch(keyword -> DoctorUtil.containsKeyword(doctor.getGender().value, keyword));
        boolean yearTest = keywords.stream()
                .anyMatch(keyword -> DoctorUtil.containsKeyword(doctor.getYear().value, keyword));
        boolean didTest = keywords.stream()
                .anyMatch(keyword -> DoctorUtil.containsKeyword(doctor.getId().toString(), keyword));

        final String specs = doctor.getSpecs().toString()
                .replaceAll("\\[", "").replaceAll("\\]", "");
        boolean specTest = keywords.stream()
                .anyMatch(keyword -> DoctorUtil.containsKeyword(specs, keyword));

        return nameTest || phoneTest || genderTest || yearTest || didTest || specTest;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoctorContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DoctorContainsKeywordsPredicate) other).keywords)); // state check
    }

}
