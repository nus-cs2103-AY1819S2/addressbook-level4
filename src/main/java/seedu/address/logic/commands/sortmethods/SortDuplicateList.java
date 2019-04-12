package seedu.address.logic.commands.sortmethods;

import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

public class SortDuplicateList {

    private List<Person> newList;

    private void getSortedPersons(SortMethod command, List<Person> lastShownList) {
        command.execute(lastShownList);
        this.newList = command.getList();
    }

    /**
     * Takes a list of persons and returns a list of persons ordered alphabetically by their first name
     * If there is only one person in the list, then this person is returned
     */
    public SortDuplicateList(List<Person> dupPersonList, SortMethod secondarySortMethod) {
        if (dupPersonList.size() == 1) {
            this.newList = new ArrayList<>();
            this.newList.add(dupPersonList.get(0));
        } else {
            getSortedPersons(secondarySortMethod, dupPersonList);
        }
    }

    public List<Person> getList() {
        return this.newList;
    }
}
