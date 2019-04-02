package seedu.address.logic.commands.sortmethods;

import java.util.List;

import seedu.address.model.person.Person;


/**
 * Sorts all persons by GPA.
 */
public class SortGpa {

    private List<Person> newList;

    public SortGpa(List<Person> lastShownList) {
        List<Person> sortedList = SortUtil.sortPersonsByGpa(lastShownList);
        this.newList = sortedList;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
