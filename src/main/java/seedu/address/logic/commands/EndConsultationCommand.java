package seedu.address.logic.commands;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.Prescription;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.patient.Nric;
import seedu.address.model.record.ConsultationRecord;
import seedu.address.model.reminder.Reminder;

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
            if (!prescription.getMedicine().isSufficient()) {
                String title = String.format(Medicine.REMINDER_TITLE_IF_INSUFFICIENT, prescription.getMedicine().name);
                String comment = String.format(Medicine.REMINDER_COMMENT_IF_INSUFFICIENT,
                        prescription.getMedicine().getQuantity(), prescription.getMedicine().getThreshold());
                LocalDate date = LocalDate.now();
                LocalTime startTime = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(),
                        LocalTime.now().getSecond());
                Reminder reminder = new Reminder(title, comment, date, startTime, null);
                ObservableList<Reminder> reminderList = model.getFilteredReminderList();
                for (Reminder exReminder : reminderList) {
                    if (exReminder.getTitle().equals(title)) {
                        model.deleteReminder(exReminder);
                    }
                }
                model.addRem(reminder);
            }
        }
        ConsultationRecord record = new ConsultationRecord(currentConsultation.getPrescriptions(),
                currentConsultation.getDiagnosis());
        model.addRecord(record, Clock.systemDefaultZone());

        Nric patientNric = currentConsultation.getPatient().getNric();

        model.endConsultation();

        return new CommandResult(String.format(END_CONSULT_FEEDBACK, patientNric.getNric()));
    }
}
