package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.ui.MainWindow;

/**
 * Clears the record list of the specified patient.
 * Should only be run in the GoTo mode.
 */
public class RecordClearCommand extends Command {

    public static final String COMMAND_WORD = "recordclear";
    public static final String COMMAND_WORD2 = "rclear";
    public static final String MESSAGE_SUCCESS = "The dental record list of %1$s has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        Patient target = MainWindow.getRecordPatient();

        Patient editedTarget = target.copy();
        editedTarget.setRecords(new ArrayList<>());

        MainWindow.setRecordPatient(editedTarget);
        model.setPerson(target, editedTarget);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedTarget.getName().fullName));
    }
}
