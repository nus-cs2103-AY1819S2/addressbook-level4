package seedu.address.logic.commands.sortmethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Sorts all persons by number of skills, positions or endorsements
 */
public class SortTagNumber {

    private List<Person> newList;

    public SortTagNumber(List<Person> lastShownList, String type) {
        String prefix = type.substring(0, 1);
        List<Person> sortedList = new ArrayList<>();
        if (prefix.equals("s")) {
            Comparator<Person> personSkillsNumberComparator = Comparator.comparing(Person::getSkillsNumber);
            sortedList = SortUtil.sortPersons(lastShownList, personSkillsNumberComparator);
        } else if (prefix.equals("p")) {
            Comparator<Person> personPositionsNumberComparator = Comparator.comparing(Person::getPositionsNumber);
            sortedList = SortUtil.sortPersons(lastShownList, personPositionsNumberComparator);
        } else if (prefix.equals("e")) {
            Comparator<Person> personEndorsementsNumberComparator = Comparator.comparing(Person::getEndorsementsNumber);
            sortedList = SortUtil.sortPersons(lastShownList, personEndorsementsNumberComparator);
        }
        Collections.reverse(sortedList);
        this.newList = sortedList;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
