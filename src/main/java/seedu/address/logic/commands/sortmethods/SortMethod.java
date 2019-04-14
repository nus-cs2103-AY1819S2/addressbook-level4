package seedu.address.logic.commands.sortmethods;

import java.util.List;

import seedu.address.model.person.Person;

/**
 *  Interface for sorting methods.
 *  String variable arguments are to hold [tag=skill/position/endorsement] and number of
 *      [tag=skill/position/endorsement] sorting method specifiers since the sorting methods are similar for these and
 *      so the correct method can be chosen within that sort class
 */
public interface SortMethod {
    void execute(List<Person> lastShownList, String... type);
    List<Person> getList();
}
