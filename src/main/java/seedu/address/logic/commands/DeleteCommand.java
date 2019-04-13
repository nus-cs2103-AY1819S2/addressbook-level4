package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Returns suggestions for the user when "delete" is entered.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MAIN_USAGE = "Did you mean:\n"
        + "Deleting a patient: \"patientdelete\" or \"pdelete\"\n"
        + "Deleting a task: \"taskdelete\" or \"tdelete\"\n";
    public static final String GOTO_USAGE = "Did you mean:\n"
        + "Deleting a record: \"recorddelete\" or \"rdelete\"\n"
        + "Deleting a task: \"taskdelete\" or \"tdelete\"\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (MainWindow.isGoToMode()) {
            return new CommandResult(GOTO_USAGE);
        } else {
            return new CommandResult(MAIN_USAGE);
        }
    }
}
