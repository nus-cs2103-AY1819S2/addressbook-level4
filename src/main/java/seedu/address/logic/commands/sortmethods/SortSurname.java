package seedu.address.logic.commands.sortmethods;

import seedu.address.model.person.Person;

import java.util.List;


/**
 * Sorts all persons by age.
 */
public class SortSurname {

    private List<Person> newList;

    public SortSurname(List<Person> lastShownList) {
        System.out.println("called2");
        List<Person> sortedList = SortUtil.sortPersonsBySurnames(lastShownList);
        this.newList = sortedList;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
