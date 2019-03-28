package seedu.address.model.util.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.patient.Patient;

/**
 * Contains multiple different ContainsKeywordsPredicate
 */
public class MultipleContainsKeywordsPredicate extends ContainsKeywordsPredicate<Patient> {
    private List<ContainsKeywordsPredicate> predicateList;

    public MultipleContainsKeywordsPredicate (List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    public void setPredicateList (List<ContainsKeywordsPredicate> predList) {
        predicateList = predList;
    }

    @Override
    public boolean test(Patient patient) {
        if (isAnd) {
            return predicateList.stream().anyMatch(pred -> pred.test(patient));
        } else {
            return predicateList.stream().allMatch(pred -> pred.test(patient));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof MultipleContainsKeywordsPredicate // instanceof handles nulls
            && predicateList.equals(((MultipleContainsKeywordsPredicate) other).predicateList)); // state check
    }
}
