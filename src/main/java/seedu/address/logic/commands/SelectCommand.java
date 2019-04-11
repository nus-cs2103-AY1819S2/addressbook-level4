package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Returns suggestions for the user when "select" is entered.
 */
public class SelectCommand extends Command {
    public static final String COMMAND_WORD = "select";
    public static final String MAIN_USAGE = "Did you mean:\n"
        + "Selecting a patient: \"patientselect\" or \"pselect\"\n";
    public static final String GOTO_USAGE = "Did you mean:\n"
        + "Selecting a record: \"recordselect\" or \"rselect\"\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (MainWindow.isGoToMode()) {
            return new CommandResult(GOTO_USAGE);
        } else {
            return new CommandResult(MAIN_USAGE);
        }
    }
}
