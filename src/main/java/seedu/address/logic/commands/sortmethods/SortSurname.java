package seedu.address.logic.commands.sortmethods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;


/**
 * Sorts all persons by age.
 */
public class SortSurname implements SortMethod {

    private List<Person> newList;

    public void execute(List<Person> lastShownList, String... type) {
        Comparator<Person> personSurnameComparator = Comparator.comparing(Person::surnameToString);
        List<Person> initialSortedList = SortUtil.sortPersons(lastShownList, personSurnameComparator);
        SortListWithDuplicates secondarySort = new SortListWithDuplicates(initialSortedList, new SortName(),
                personSurnameComparator);
        this.newList = secondarySort.getList();
    }

    public List<Person> getList() {
        return this.newList;
    }
}
