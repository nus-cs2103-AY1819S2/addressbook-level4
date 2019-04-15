package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.job.JobListName.EMPTY;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_SUCCESS = "Listed all persons and jobs";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.revertList();
        model.clearJobFilteredLists();
        model.setIsAllJobScreen(true);
        model.updateFilteredPersonLists(EMPTY);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
