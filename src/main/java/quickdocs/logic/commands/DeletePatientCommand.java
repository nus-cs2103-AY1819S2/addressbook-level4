package quickdocs.logic.commands;

import java.util.Objects;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;

/**
 * Deletes a patient record from the patient list
 */
public class DeletePatientCommand extends Command {

    public static final String COMMAND_WORD = "deletepat";
    public static final String COMMAND_ALIAS = "dp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": deletes a patient using the NRIC specified.\n"
            + "Example: " + COMMAND_WORD + " r/S9637777A";
    public static final String NO_PATIENT_FOUND = "No patient with NRIC: %s found";
    public static final String PATIENT_DELETED = "Patient with NRIC: %s deleted.\n";

    private Nric patientToDelete;

    public DeletePatientCommand(Nric toDelete) {
        this.patientToDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        Patient selectedPatient = model.getPatientByNric(patientToDelete.toString());

        if (selectedPatient == null) {
            throw new CommandException(String.format(NO_PATIENT_FOUND, patientToDelete.toString()));
        }

        model.deletePatientByNric(patientToDelete.toString());

        CommandResult result = new CommandResult(String.format(PATIENT_DELETED,
                patientToDelete.toString()));

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
        return Objects.equals(patientToDelete, that.patientToDelete);
    }

}
