package seedu.address.logic.commands;

import static seedu.address.logic.parser.DiagnosePatientCommandParser.PREFIX_ASSESSMENT;
import static seedu.address.logic.parser.DiagnosePatientCommandParser.PREFIX_SYMPTOM;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Diagnosis;

/**
 * Adds or replace diagnosis of current consultation session
 */
public class DiagnosePatientCommand extends Command {
    public static final String COMMAND_WORD = "diagnose";
    public static final String COMMAND_ALIAS = "d";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Record the symptoms and assessment of the patient's"
            + "current condition.\n"
            + "Parameters: "
            + PREFIX_SYMPTOM + "symptom 1" + " ..."
            + PREFIX_ASSESSMENT + "assessment\n"
            + "Example: "
            + PREFIX_SYMPTOM + "runny nose "
            + PREFIX_SYMPTOM + "sore throat "
            + PREFIX_ASSESSMENT + "flu\n";
    public static final String NO_ONGOING_CONSULTATION = "There is no ongoing consultation to record diagnosis\n";

    private Diagnosis patientDiagnosis;

    public DiagnosePatientCommand(Diagnosis diagnosis) {
        this.patientDiagnosis = diagnosis;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (model.checkConsultation() == false) {
            throw new CommandException(NO_ONGOING_CONSULTATION);
        }

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
