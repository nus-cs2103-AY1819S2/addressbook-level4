package seedu.address.logic.commands;

import java.util.Objects;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

/**
 * Deletes a patient from the patient list
 */
public class DeletePatientCommand extends Command {

    public static final String COMMAND_WORD = "deletepat";
    public static final String COMMAND_ALIAS = "dp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": deletes a patient using the NRIC specified.\n"
            + "Example: " + COMMAND_WORD + " r/S9637777A";
    public static final String NO_PATIENT_FOUND = "No patient with NRIC: %s found";
    public static final String PATIENT_DELETED = "Patient with NRIC: %s deleted.\n";

    private Nric toDelete;

    public DeletePatientCommand(Nric toDelete) {
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        Patient selectedPatient = model.getPatientByNric(toDelete.toString());

        if (selectedPatient == null) {
            throw new CommandException(String.format(NO_PATIENT_FOUND, toDelete.toString()));
        }

        model.deletePatientByNric(toDelete.toString());

        CommandResult result = new CommandResult(String.format(PATIENT_DELETED,
                toDelete.toString()));

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeletePatientCommand that = (DeletePatientCommand) o;
        return Objects.equals(toDelete, that.toDelete);
    }

}
