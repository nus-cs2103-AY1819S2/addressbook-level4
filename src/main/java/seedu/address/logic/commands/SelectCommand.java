package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

public class SelectCommand extends Command {
    public static final String COMMAND_WORD = "select";
    private static final String MAIN_USAGE = "Did you mean:\n"
        + "Finding a patient: \"patientselect\" or \"pselect\"\n";
    private static final String GOTO_USAGE = "No relevant select command in Records mode.\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (MainWindow.isGoToMode()) {
            return new CommandResult(GOTO_USAGE);
        } else {
            return new CommandResult(MAIN_USAGE);
        }
    }
}
