package seedu.address.logic.commands.sortmethods;

import java.util.List;

import seedu.address.model.person.Person;


/**
 * Sorts all persons by age.
 */
public class SortSurname {

    private List<Person> newList;

    public SortSurname(List<Person> lastShownList) {
        List<Person> sortedList = SortUtil.sortPersonsBySurnames(lastShownList);
        this.newList = sortedList;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
