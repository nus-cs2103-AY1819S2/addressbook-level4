package seedu.address.logic.commands.sortmethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
            sortedList =
                    lastShownList.stream().sorted(Comparator.comparing(Person::getSkillsNumber))
                            .collect(Collectors.toList());
        } else if (prefix.equals("p")) {
            sortedList =
                    lastShownList.stream().sorted(Comparator.comparing(Person::getPositionsNumber))
                            .collect(Collectors.toList());
        } else if (prefix.equals("e")) {
            sortedList =
                    lastShownList.stream().sorted(Comparator.comparing(Person::getEndorsementsNumber))
                            .collect(Collectors.toList());
        }
        Collections.reverse(sortedList);
        this.newList = sortedList;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
