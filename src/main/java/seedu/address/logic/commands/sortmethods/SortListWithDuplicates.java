package seedu.address.logic.commands.sortmethods;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Person;

public class SortListWithDuplicates {

    private List<Person> newList;

    public SortListWithDuplicates(List<Person> persons, SortMethod secondarySortMethod) {
        System.out.println("SortListWithDuplicates");
        Person prevPerson = persons.get(0);
        List<Person> dupPersonList = new ArrayList<>();
        List<Person> orderedPersonList = new ArrayList<>();
        for (Person person : persons) {
            if (person.surnameToString().equals(prevPerson.surnameToString())) {
                dupPersonList.add(person);
            } else {
                SortDuplicateList sortedDupList = new SortDuplicateList(dupPersonList, secondarySortMethod);
                orderedPersonList.addAll(sortedDupList.getList());
                dupPersonList = new ArrayList<>();
                dupPersonList.add(person);
            }
            prevPerson = person;
        }
        SortDuplicateList sortedDupList = new SortDuplicateList(dupPersonList, secondarySortMethod);
        orderedPersonList.addAll(sortedDupList.getList());
        this.newList =  orderedPersonList;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
