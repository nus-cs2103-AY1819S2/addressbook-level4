package quickdocs.logic.commands;

import java.util.logging.Logger;

import quickdocs.commons.core.LogsCenter;
import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.logic.parser.DiagnosePatientCommandParser;
import quickdocs.model.Model;
import quickdocs.model.consultation.Diagnosis;

/**
 * Creates or replace the diagnosis record of current consultation session.
 * Diagnosis records the symptoms of the patient during the current consultation session, and
 * also the assessment of the illness given by the doctor
 */
public class DiagnosePatientCommand extends Command {
    public static final String COMMAND_WORD = "diagnose";
    public static final String COMMAND_ALIAS = "d";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Record the symptoms and assessment of the patient's"
            + "current condition.\n"
            + "Parameters: "
            + DiagnosePatientCommandParser.PREFIX_SYMPTOM + "symptom 1 "
            + DiagnosePatientCommandParser.PREFIX_SYMPTOM + "symptom 2 " + " ... "
            + DiagnosePatientCommandParser.PREFIX_ASSESSMENT + "assessment\n"
            + "Example: "
            + DiagnosePatientCommandParser.PREFIX_SYMPTOM + "runny nose "
            + DiagnosePatientCommandParser.PREFIX_SYMPTOM + "sore throat "
            + DiagnosePatientCommandParser.PREFIX_ASSESSMENT + "flu\n";
    public static final String NO_ONGOING_CONSULTATION = "There is no ongoing consultation to record diagnosis\n";

    private static final Logger logger = LogsCenter.getLogger(DiagnosePatientCommand.class);

    private Diagnosis patientDiagnosis;

    public DiagnosePatientCommand(Diagnosis diagnosis) {
        this.patientDiagnosis = diagnosis;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        // diagnosis can only be given when there's an ongoing consultation session
        if (model.checkConsultation() == false) {
            throw new CommandException(NO_ONGOING_CONSULTATION);
        }

        model.diagnosePatient(patientDiagnosis);
        logger.info("Diagnosis entered or replaced for current consultation");

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
