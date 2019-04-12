package seedu.address.model.util.predicate;

import java.util.Arrays;
import java.util.List;

/**
 * Contains multiple different ContainsKeywordsPredicate
 */
public class MultipleContainsKeywordsPredicate<T> extends ContainsKeywordsPredicate<T> {
    private List<ContainsKeywordsPredicate> predicateList;

    public MultipleContainsKeywordsPredicate (List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        super(keywords, isIgnoreCase, isAnd);
    }

    public void setPredicateList (List<ContainsKeywordsPredicate> predList) {
        predicateList = predList;
    }

    @Override
    public boolean test(T obj) {
        if (!isAnd) {
            return predicateList.stream().anyMatch(pred -> pred.test(obj));
        } else {
            return predicateList.stream().allMatch(pred -> pred.test(obj));
        }
    }

    @Override
    public String toString() {
        return String.format(Arrays.toString(predicateList.toArray()) + isAnd + isIgnoreCase).trim();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof MultipleContainsKeywordsPredicate // instanceof handles nulls
            && predicateList.equals(((MultipleContainsKeywordsPredicate) other).predicateList));
    }
}
