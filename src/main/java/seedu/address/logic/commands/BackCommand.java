package seedu.address.logic.commands;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Terminates the program.
 */
public class BackCommand extends Command {

    public static final String COMMAND_WORD = "back";

    private static final String MESSAGE_BACK_ACKNOWLEDGEMENT = "Displaying the patient list";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (MainWindow.isGoToMode()) {
            Alert alert = new Alert(Alert.AlertType.NONE,
                    "You will be directed back to patients list.\nConfirm action?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                return new CommandResult(MESSAGE_BACK_ACKNOWLEDGEMENT, false, false, true);
            } else {
                return new CommandResult(Messages.MESSAGE_NOTHING_DONE);
            }
        } else {
            throw new CommandException(Messages.MESSAGE_IN_PATIENT_MODE);
        }
    }
}
