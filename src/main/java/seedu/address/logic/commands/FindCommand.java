package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Returns suggestions for the user when "find" is entered.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    public static final String MAIN_USAGE = "Did you mean:\n"
        + "Finding a patient: \"patientfind\" or \"pfind\"\n";
    public static final String GOTO_USAGE = "Did you mean:\n"
        + "Finding a record: \"recordfind\" or \"rfind\"\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (MainWindow.isGoToMode()) {
            return new CommandResult(GOTO_USAGE);
        } else {
            return new CommandResult(MAIN_USAGE);
        }
    }
}
