package seedu.address.logic.commands.sortmethods;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Takes a list of persons and sorts the list of persons according to the {@code secondarySortMethod}
 * If there is only one person in the list, then this person is returned.
 *  This ordered list can be returned via {@code getList}
 */
public class SortDuplicateList {

    private List<Person> newList;

    public SortDuplicateList(List<Person> dupPersonList, SortMethod secondarySortMethod, String... type) {
        if (dupPersonList.size() == 1) {
            this.newList = new ArrayList<>();
            this.newList.add(dupPersonList.get(0));
        } else {
            processSortMethod(secondarySortMethod, dupPersonList, type);
        }
    }

    private void processSortMethod(SortMethod command, List<Person> lastShownList, String... type) {
        command.execute(lastShownList, type);
        this.newList = command.getList();
    }

    public List<Person> getList() {
        return this.newList;
    }
}
