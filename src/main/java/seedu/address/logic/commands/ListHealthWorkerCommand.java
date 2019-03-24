package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_HEALTHWORKERS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListHealthWorkerCommand extends ListCommand implements HealthWorkerCommand {

    public static final String MESSAGE_SUCCESS = "Listed all health workers";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredHealthWorkerList(PREDICATE_SHOW_ALL_HEALTHWORKERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof ListHealthWorkerCommand;
    }
}
