package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

public class CopyCommand extends Command {
    public static final String COMMAND_WORD = "copy";
    private static final String MAIN_USAGE = "Did you mean:\n"
        + "Copying a patient: patientcopy or pcopy\n"
        + "Copying a task: taskcopy or tcopy\n";
    private static final String GOTO_USAGE = "Did you mean:\n"
        + "Copying a task: taskcopy or tcopy\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (MainWindow.isGoToMode()) {
            return new CommandResult(GOTO_USAGE);
        } else {
            return new CommandResult(MAIN_USAGE);
        }
    }
}
