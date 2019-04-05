package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRESCRIPTIONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Show all prescriptions in the docX record to the user.
 */
public class ListPrescriptionCommand extends Command {

    public static final String COMMAND_WORD = "list-presc";
    public static final String MESSAGE_SUCCESS = "Listed all prescriptions";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPrescriptionList(PREDICATE_SHOW_ALL_PRESCRIPTIONS);
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.ShowPanel.PRESC_PANEL);
    }
}

