package seedu.address.logic.commands;

import java.time.Clock;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.Prescription;
import seedu.address.model.patient.Nric;
import seedu.address.model.record.ConsultationRecord;

/**
 * End the current consultation session and store the details
 */
public class EndConsultationCommand extends Command {
    public static final String COMMAND_WORD = "endconsult";
    public static final String COMMAND_ALIAS = "ec";
    public static final String NO_CONSULT_EXCEPTION = "There is no ongoing consultation";
    public static final String DIAGNOSIS_EXCEPTION = "No diagnosis given for current consultation yet";
    public static final String PRESCRIPTION_EXCEPTION = "No prescription given for current consultation yet";
    public static final String END_CONSULT_FEEDBACK = "Consultation for %s ended\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        Consultation currentConsultation = model.getCurrentConsultation();

        if (currentConsultation == null) {
            throw new CommandException(NO_CONSULT_EXCEPTION);
        }

        if (currentConsultation.getDiagnosis() == null) {
            throw new CommandException(DIAGNOSIS_EXCEPTION);
        }

        if (currentConsultation.getPrescriptions() == null) {
            throw new CommandException(PRESCRIPTION_EXCEPTION);
        }

        for (Prescription prescription : currentConsultation.getPrescriptions()) {
            prescription.getMedicine().subtractQuantity(prescription.getQuantity());
            ConsultationRecord record = new ConsultationRecord(prescription);
            model.addRecord(record, Clock.systemDefaultZone());
        }

        Nric patientNric = currentConsultation.getPatient().getNric();

        model.endConsultation();

        return new CommandResult(String.format(END_CONSULT_FEEDBACK, patientNric.getNric()));
    }
}
