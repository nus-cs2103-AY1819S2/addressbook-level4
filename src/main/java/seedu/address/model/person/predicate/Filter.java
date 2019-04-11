package seedu.address.model.person.predicate;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Represents a filter in the address book job list.
 */
public class Filter {
    private String filterName;
    private Predicate<Person> predicate;

    /**
     * Constructs a {@code Filter}.
     *
     * @param filterName A valid filter name.
     */
    public Filter(String filterName) {
        this.filterName = filterName;
        this.predicate = null;
    }
    /**
     * Constructs a {@code Filter}.
     *
     * @param filterName A valid filter name.
     * @param predicate A valid filter name.
     */
    public Filter(String filterName, Predicate<Person> predicate) {
        this.filterName = filterName;
        this.predicate = predicate;
    }

    /**
     * Returns true if both filters of the same name.
     * This defines a weaker notion of equality between two filters.
     */
    public boolean isSameFilter(Filter otherFilter) {
        if (otherFilter == this) {
            return true;
        }

        return otherFilter != null
            && otherFilter.getFilterName().equals(this.getFilterName());
    }

    public Predicate<Person> getPredicate() {
        return predicate;
    }

    public String getFilterName() {
        return filterName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Filter // instanceof handles nulls
            && filterName.equals(((Filter) other).getFilterName())); // state check
    }
}
