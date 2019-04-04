package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Clears the Patient list.
 */
public class PatientClearCommand extends Command {

    public static final String COMMAND_WORD = "patientclear";
    public static final String COMMAND_WORD2 = "pclear";
    public static final String MESSAGE_SUCCESS = "Patient list has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setPatientList(new ArrayList<>());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
