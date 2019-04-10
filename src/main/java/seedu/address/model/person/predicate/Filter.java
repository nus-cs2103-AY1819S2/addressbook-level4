package seedu.address.model.person.predicate;

import java.security.PublicKey;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class Filter {
    private String filterName;
    Predicate<Person> predicate;

    public Filter(String filterName){
        this.filterName = filterName;
        this.predicate = null;
    }
    public Filter(String filterName, Predicate<Person> predicate){
        this.filterName = filterName;
        this.predicate = predicate;
    }

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
}
