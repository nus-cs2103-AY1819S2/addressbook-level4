package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Tests that a criteria given matches the list of available sorting criteria.
 */
public class FilterCriteriaContainsKeywordPredicate implements Predicate<String> {
    private final String criteria;
    private final String[] filterKeywords;
    private final String[] keywords = {"gender", "major", "tags", "yearofstudy"};

    public FilterCriteriaContainsKeywordPredicate(String criteria) {
        requireNonNull(criteria);
        requireNonNull(criteria.split("\\s+", 2)[1]);
        this.criteria = criteria.split("\\s+", 2)[0];
        this.filterKeywords = criteria.split("\\s+", 2)[1].split("\\s+");
    }

    @Override
    public boolean test(String criteria) {
        for (int i = 0; i < keywords.length; i++) {
            if (criteria.equalsIgnoreCase(keywords[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCriteriaContainsKeywordPredicate // instanceof handles nulls
                && criteria.equals(((FilterCriteriaContainsKeywordPredicate) other).criteria)); // state check
    }

    @Override
    public String toString() {
        return criteria;
    }

    public String[] getFilterKeywords() {
        return filterKeywords;
    }

}