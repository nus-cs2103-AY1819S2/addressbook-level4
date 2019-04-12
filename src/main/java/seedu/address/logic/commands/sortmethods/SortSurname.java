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
        List<Person> firstSortedList = SortUtil.sortPersons(lastShownList, personSurnameComparator);
        System.out.println(firstSortedList);
        SortListWithDuplicates finalSortedList = new SortListWithDuplicates(firstSortedList, new SortName());
        this.newList = finalSortedList.getList();
    }

    /**
     *  checks a List of Persons for duplicate surnames and returns a List of Persons subsequently ordered by first name
     */
    private static List<Person> duplicateValueListAlteration(List<Person> persons) {
        Person prevPerson = persons.get(0);
        List<Person> dupPersonList = new ArrayList<>();
        List<Person> orderedPersonList = new ArrayList<>();
        for (Person person : persons) {
            if (person.surnameToString().equals(prevPerson.surnameToString())) {
                dupPersonList.add(person);
            } else {

                orderedPersonList.addAll(sortDuplicateList(dupPersonList));
                dupPersonList = new ArrayList<>();
                dupPersonList.add(person);
            }
            prevPerson = person;
        }
        orderedPersonList.addAll(sortDuplicateList(dupPersonList));
        return orderedPersonList;
    }

    /**
     * Takes a list of persons and returns a list of persons ordered alphabetically by their first name
     * If there is only one person in the list, then this person is returned
     */
    private static List<Person> sortDuplicateList(List<Person> dupPersonList) {
        List<Person> orderedPersonDuplicateList = new ArrayList<>();
        if (dupPersonList.size() == 1) {
            orderedPersonDuplicateList.add(dupPersonList.get(0));
        } else {
            //SortUtil.callSortMethod(SortName(), dupPersonList);
            //orderedPersonDuplicateList.addAll(SortName.getList());
        }
        return orderedPersonDuplicateList;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
