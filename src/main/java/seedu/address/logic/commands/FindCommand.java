package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    private static final String MAIN_USAGE = "Did you mean:\n"
        + "Finding a patient: \"patientfind\" or \"pfind\"\n";
    private static final String GOTO_USAGE = "No relevant find command in Records mode\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (MainWindow.isGoToMode()) {
            return new CommandResult(GOTO_USAGE);
        } else {
            return new CommandResult(MAIN_USAGE);
        }
    }
}
