package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Diagnosis;

/**
 * Adds or replace diagnosis of current consultation session
 */
public class DiagnosePatientCommand extends Command {
    public static final String COMMAND_WORD = "diagnose";

    private Diagnosis patientDiagnosis;

    public DiagnosePatientCommand(Diagnosis diagnosis) {
        this.patientDiagnosis = diagnosis;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        model.diagnosePatient(patientDiagnosis);

        return new CommandResult(patientDiagnosis.toString());
    }

    public Diagnosis getPatientDiagnosis() {
        return patientDiagnosis;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DiagnosePatientCommand
                && patientDiagnosis.equals(((DiagnosePatientCommand) other).getPatientDiagnosis()));
    }
}
