package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDHISTS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all medical histories in the docX record to the user.
 */
public class ListMedHistCommand extends Command {

    public static final String COMMAND_WORD = "list-med-hist";

    public static final String MESSAGE_SUCCESS = "Listed all medical histories";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredMedHistList(PREDICATE_SHOW_ALL_MEDHISTS);
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.ShowPanel.MED_HIST_PANEL);
    }
}
