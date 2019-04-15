package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Returns suggestions for the user when "list" is entered.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String MAIN_USAGE = "Did you mean:\n"
        + "Listing all patients: \"patientlist\" or \"plist\"\n"
        + "Listing all tasks: \"tasklist\" or \"tlist\"\n";
    public static final String GOTO_USAGE = "Did you mean:\n"
        + "Listing all records: \"recordlist\" or \"rlist\"\n"
        + "Listing all tasks: \"tasklist\" or \"tlist\"\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (MainWindow.isGoToMode()) {
            return new CommandResult(GOTO_USAGE);
        } else {
            return new CommandResult(MAIN_USAGE);
        }
    }
}
