package seedu.address.logic.commands.sortmethods;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;


/**
 * Sorts all persons by age.
 */
public class SortSurname {

    private List<Person> newList;

    public SortSurname(List<Person> lastShownList) {
        this.newList =
                lastShownList.stream().sorted(Comparator.comparing(Person::surnamesToString)).
                        collect(Collectors.toList());
    }

    public List<Person> getList() {
        return this.newList;
    }
}
