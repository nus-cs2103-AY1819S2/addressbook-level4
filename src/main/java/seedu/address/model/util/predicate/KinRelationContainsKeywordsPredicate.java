package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.patient.Patient;

/**
 * Tests that a {@code Patient}'s attached {@code NextOfKin} {@code Relation} matches any of the keywords given.
 */
public class KinRelationContainsKeywordsPredicate extends ContainsKeywordsPredicate<Patient> {

    public KinRelationContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    public KinRelationContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    @Override
    public boolean test(Patient patient) {
        if (!isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getNextOfKin().getKinRelation()
                    .toString(), keyword));

        } else if (isIgnoreCase && !isAnd) {
            return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getNextOfKin().getKinRelation()
                    .toString(), keyword));

        } else if (!isIgnoreCase && isAnd) {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordCaseSensitive(patient.getNextOfKin().getKinRelation()
                    .toString(), keyword));

        } else {
            return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(patient.getNextOfKin().getKinRelation()
                    .toString(), keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof KinRelationContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((KinRelationContainsKeywordsPredicate) other).keywords)); // state check
    }
}
