package seedu.address.logic.commands.sortmethods;

import java.util.List;

import seedu.address.model.person.Person;


/**
 * Sorts all persons by age.
 */
public class SortName {

    private List<Person> newList;

    public SortName(List<Person> lastShownList) {
        List<Person> sortedList = SortUtil.sortPersonsByNames(lastShownList);
        this.newList = sortedList;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
