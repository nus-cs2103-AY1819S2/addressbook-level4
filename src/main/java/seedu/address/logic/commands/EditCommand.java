package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";
    private static final String MAIN_USAGE = "Did you mean:\n"
        + "Editing a patient: \"patientedit\" or \"pedit\"\n"
        + "Editing a task: \"taskedit\" or \"tedit\"\n";
    private static final String GOTO_USAGE = "Did you mean:\n"
        + "Editing a record: \"recordedit\" or redit\"\n"
        + "Editing a task: \"taskedit\" or tedit\"\n"
        + "Editing teeth: \"teethedit\"";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (MainWindow.isGoToMode()) {
            return new CommandResult(GOTO_USAGE);
        } else {
            return new CommandResult(MAIN_USAGE);
        }
    }
}
