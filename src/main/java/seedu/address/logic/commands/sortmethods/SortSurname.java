package seedu.address.logic.commands.sortmethods;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;


/**
 * Sorts all persons by age.
 */
public class SortSurname {

    private List<Person> newList;

    public SortSurname(List<Person> lastShownList) {
        Comparator<Person> personSurnameComparator = Comparator.comparing(Person::surnameToString);
        this.newList = SortUtil.sortPersons(lastShownList, personSurnameComparator);
    }

    public List<Person> getList() {
        return this.newList;
    }
}
