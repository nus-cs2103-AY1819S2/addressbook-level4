package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Returns suggestions for the user when "add" is entered.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String MAIN_USAGE = "Did you mean:\n"
        + "Adding a patient: \"patientadd\" or \"padd\"\n"
        + "Adding a task: \"taskadd\" or \"tadd\"\n";
    public static final String GOTO_USAGE = "Did you mean:\n"
        + "Adding a record: \"recordadd\" or \"radd\"\n"
        + "Adding a task: \"taskadd\" or \"tadd\"\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (MainWindow.isGoToMode()) {
            return new CommandResult(GOTO_USAGE);
        } else {
            return new CommandResult(MAIN_USAGE);
        }
    }
}
