package seedu.address.logic.commands.sortmethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Sorts all persons by number of skills, positions or endorsements
 */
/**
 * Follows the SortMethod interface
 * Sorts all persons by the number of tag type specified by {@code type} (skill, position, endorsement).
 * Should two people have the same number of tags of the specified type, they are subsequently ordered alphabetically
 *     by that tag type.
 */
public class SortTagNumber implements SortMethod {

    private List<Person> newList;

    /**
     * alters the newList to contain persons in newly sorted order
     */
    public void execute(List<Person> lastShownList, String... type) {
        String prefix = type[0].substring(0, 1);
        List<Person> initialSortedList = new ArrayList<>();
        Comparator<Person> personTagNumberComparator = Comparator.comparing(Person::getSkillsNumber);
        if (prefix.equals("s")) {
            initialSortedList = SortUtil.sortPersons(lastShownList, personTagNumberComparator);
        } else if (prefix.equals("p")) {
            personTagNumberComparator = Comparator.comparing(Person::getPositionsNumber);
            initialSortedList = SortUtil.sortPersons(lastShownList, personTagNumberComparator);
        } else if (prefix.equals("e")) {
            personTagNumberComparator = Comparator.comparing(Person::getEndorsementsNumber);
            initialSortedList = SortUtil.sortPersons(lastShownList, personTagNumberComparator);
        }
        Collections.reverse(initialSortedList);
        SortListWithDuplicates secondarySort = new SortListWithDuplicates(initialSortedList, new SortTags(),
                personTagNumberComparator, type);
        this.newList = secondarySort.getList();
    }

    public List<Person> getList() {
        return this.newList;
    }
}
