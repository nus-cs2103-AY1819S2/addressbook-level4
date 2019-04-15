package quickdocs.logic.commands;

import java.time.Clock;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.consultation.Consultation;
import quickdocs.model.consultation.Prescription;
import quickdocs.model.patient.Nric;
import quickdocs.model.record.ConsultationRecord;

/**
 * End the current consultation session and store the consultation details
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

        /**
         * The current consultation session can end only when both the diagnosis and the prescription
         * is provided
         */
        if (currentConsultation.getDiagnosis() == null) {
            throw new CommandException(DIAGNOSIS_EXCEPTION);
        }

        if (currentConsultation.getPrescriptions() == null) {
            throw new CommandException(PRESCRIPTION_EXCEPTION);
        }

        /**
         * The deduction of medicine from the inventory will only happen once the
         * consultation is confirmed to have ended.
         */
        for (Prescription prescription : currentConsultation.getPrescriptions()) {
            model.executePrescription(prescription);
        }

        ConsultationRecord record = new ConsultationRecord(currentConsultation.getPrescriptions(),
                currentConsultation.getDiagnosis());
        model.addRecord(record, Clock.systemDefaultZone());

        Nric patientNric = currentConsultation.getPatient().getNric();

        model.endConsultation();

        return new CommandResult(String.format(END_CONSULT_FEEDBACK, patientNric.getNric()));
    }
}
