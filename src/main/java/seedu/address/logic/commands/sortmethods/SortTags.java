package seedu.address.logic.commands.sortmethods;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Follows the SortMethod interface
 * Sorts all persons by tag specified by {@code type} (skill, position, endorsement).
 * Each persons specified tag type set is ordered alphabetically.
 * Persons are then ordered alphabetically based on this ordered tag type.
 * Should two people have matching sets of the specified tags, then they are subsequently ordered by the tags of
 *     the other types (in their pre-existing ordering)
 * Should two people have perfectly matching tags, they are then ordered alphabetically by name (first name first)
 */
public class SortTags implements SortMethod {

    private List<Person> newList;

    /**
     * alters the newList to contain persons in newly sorted order
     */
    public void execute(List<Person> lastShownList, String... type) {
        String prefix = type[0].substring(0, 1);
        //Modify each Person to organise tags alphabetically
        List<Person> personsWithCorrectTagOrder = SortUtil.orderPersonsTags(lastShownList, prefix);
        //Sort Persons alphabetically by tags
        Comparator<Person> personTagComparator = Comparator.comparing(Person::tagsToString);
        List<Person> initialSortedList = SortUtil.sortPersons(personsWithCorrectTagOrder, personTagComparator);
        SortListWithDuplicates secondarySort = new SortListWithDuplicates(initialSortedList, new SortName(),
                personTagComparator);
        this.newList = secondarySort.getList();
    }

    public List<Person> getList() {
        return this.newList;
    }
}
