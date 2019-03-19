package seedu.address.logic.commands.sortMethods;

import java.util.*;
import seedu.address.model.person.Person;


/**
 * Sorts all persons by age.
 */
public class SortAlphabetical {

    private List<Person> newList;

    public List<Person> getList() {
        return this.newList;
    }

    public SortAlphabetical(List<Person> lastShownList) {
        List<Person> newList = SortUtil.sortPersonsByNames(lastShownList);
        this.newList = newList;
    }
}
