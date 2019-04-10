package seedu.address.logic.commands.sortmethods;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;


/**
 * Sorts all persons by Education.
 */
public class SortEducation {

    private List<Person> newList;

    public SortEducation(List<Person> lastShownList) {
        Comparator<Person> personEducationComparator = Comparator.comparing(Person::educationToString);
        this.newList = SortUtil.sortPersons(lastShownList, personEducationComparator);
    }

    public List<Person> getList() {
        return this.newList;
    }
}
