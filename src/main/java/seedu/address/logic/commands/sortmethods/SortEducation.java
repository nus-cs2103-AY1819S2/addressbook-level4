package seedu.address.logic.commands.sortmethods;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;


/**
 * Sorts all persons by Education.
 */
public class SortEducation {

    private List<Person> newList;

    public SortEducation(List<Person> lastShownList) {
        this.newList =
                lastShownList.stream().sorted(Comparator.comparing(Person::educationToString))
                        .collect(Collectors.toList());;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
