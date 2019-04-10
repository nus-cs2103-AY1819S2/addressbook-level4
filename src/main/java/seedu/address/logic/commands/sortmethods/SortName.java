package seedu.address.logic.commands.sortmethods;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;


/**
 * Sorts all persons by age.
 */
public class SortName {

    private List<Person> newList;

    public SortName(List<Person> lastShownList) {
        Comparator<Person> personNameComparator = Comparator.comparing(Person::nameToString);
        this.newList = SortUtil.sortPersons(lastShownList, personNameComparator);
    }

    public List<Person> getList() {
        return this.newList;
    }
}
