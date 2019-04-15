package seedu.address.logic.commands.sortmethods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;

/**
 *  Takes a list of persons, a secondary sorting method and a comparator.
 *  Looks through the list of persons for people matching on the specified comparator.
 *  Makes subsequent call to {@code SortDuplicateList} to order the matching persons according to
 *      secondary sorting method.
 *  Sorted list can be retrieved via {@code getList()}
 */
public class SortListWithDuplicates {

    private List<Person> newList;

    public SortListWithDuplicates(List<Person> persons, SortMethod secondarySortMethod,
                                  Comparator<Person> personSurnameComparator, String... type) {
        Person prevPerson = persons.get(0);
        List<Person> dupPersonList = new ArrayList<>();
        List<Person> orderedPersonList = new ArrayList<>();
        for (Person person : persons) {
            if (personSurnameComparator.compare(person, prevPerson) == 0) {
                dupPersonList.add(person);
            } else {
                SortDuplicateList sortedDupList = new SortDuplicateList(dupPersonList, secondarySortMethod, type);
                orderedPersonList.addAll(sortedDupList.getList());
                dupPersonList = new ArrayList<>();
                dupPersonList.add(person);
            }
            prevPerson = person;
        }
        SortDuplicateList sortedDupList = new SortDuplicateList(dupPersonList, secondarySortMethod, type);
        orderedPersonList.addAll(sortedDupList.getList());
        this.newList = orderedPersonList;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
