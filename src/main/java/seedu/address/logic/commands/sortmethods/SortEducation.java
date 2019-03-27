package seedu.address.logic.commands.sortmethods;

import seedu.address.model.person.Person;

import java.util.List;


/**
 * Sorts all persons by Education.
 */
public class SortEducation {

    private List<Person> newList;

    public SortEducation(List<Person> lastShownList) {
        List<Person> sortedList = SortUtil.sortPersonsByEducation(lastShownList);
        this.newList = sortedList;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
