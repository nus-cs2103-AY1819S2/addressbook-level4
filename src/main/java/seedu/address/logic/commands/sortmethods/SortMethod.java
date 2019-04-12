package seedu.address.logic.commands.sortmethods;

import seedu.address.model.person.Person;

import java.util.List;

public interface SortMethod {
    void execute(List<Person> lastShownList, String... type);
    List<Person> getList();
}
