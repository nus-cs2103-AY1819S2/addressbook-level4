package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COMPANIES;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Company;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCpnyCommand extends Command {

    public static final String COMMAND_WORD = "listcpny";

    public static final String MESSAGE_SUCCESS = "Listed all companies";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);
        ObservableList<Company> c = model.getFilteredCompanyList();
        for (Company comp : c) {
            System.out.println(comp.toString());
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
