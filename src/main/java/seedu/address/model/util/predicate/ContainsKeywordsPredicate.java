package seedu.address.model.util.predicate;

import java.util.List;
import java.util.function.Predicate;

/**
 * Represents a Predicate with keywords to use with an associated attribute
 */
public abstract class ContainsKeywordsPredicate<T> implements Predicate<T> {
    protected final List<String> keywords;
    protected final boolean isIgnoreCase;
    protected final boolean isAnd;

    /**
     * Basic constructor for predicates.
     *
     * @param keywords List of keywords to test against.
     */
    public ContainsKeywordsPredicate(List<String> keywords, boolean isIgnoreCase, boolean isAnd) {
        this.keywords = keywords;
        this.isIgnoreCase = isIgnoreCase;
        this.isAnd = isAnd;
    }

    public ContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.isIgnoreCase = true;
        this.isAnd = false;
    }
}
