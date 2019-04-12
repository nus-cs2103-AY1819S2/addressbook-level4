package seedu.address.logic.commands.sortmethods;

import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Takes a list of persons and sorts the list of persons according to the {@code secondarySortMethod}
 * If there is only one person in the list, then this person is returned.
 *  This ordered list can be returned via {@code getList}
 */
public class SortDuplicateList {

    private List<Person> newList;

    private void getSortedPersons(SortMethod command, List<Person> lastShownList, String... type) {
        command.execute(lastShownList, type);
        this.newList = command.getList();
    }

    public SortDuplicateList(List<Person> dupPersonList, SortMethod secondarySortMethod, String... type) {
        if (dupPersonList.size() == 1) {
            this.newList = new ArrayList<>();
            this.newList.add(dupPersonList.get(0));
        } else {
            getSortedPersons(secondarySortMethod, dupPersonList, type);
        }
    }

    public List<Person> getList() {
        return this.newList;
    }
}
