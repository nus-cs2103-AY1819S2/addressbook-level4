package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_MEDHIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDHISTS;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.MedicalHistory;

/**
 * Lists all medical histories in the docX record to the user.
 */
public class ListMedHistCommand extends Command {

    public static final String COMMAND_WORD = "list-med-hist";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all medical histories or medical histories satisfying specified restrictions.\n"
            + COMMAND_WORD + " "
            + "[" + PREFIX_PATIENT_ID + "PATIENT-ID " + "] "
            + "[" + PREFIX_DOCTOR_ID + "DOCTOR-ID " + "] "
            + "[" + PREFIX_DATE_OF_MEDHIST + "DATE " + "]\n"
            + "Example: " + COMMAND_WORD + "\n"
            + COMMAND_WORD + " " + PREFIX_PATIENT_ID + "1\n"
            + COMMAND_WORD + " " + PREFIX_DOCTOR_ID + "1\n"
            + COMMAND_WORD + " " + PREFIX_DATE_OF_MEDHIST + "2018-03-02\n"
            + COMMAND_WORD + " " + PREFIX_PATIENT_ID + "1 " + PREFIX_DOCTOR_ID + "1 "
            + PREFIX_DATE_OF_MEDHIST + "2018-03-02";

    public static final String MESSAGE_SUCCESS =
            "Listed all medical histories. Write up of medical histories are not be shown in the list.";

    private final Optional<Integer> targetPatientId;

    private final Optional<Integer> targetDoctorId;

    private final Optional<LocalDate> targetDate;

    public ListMedHistCommand(Integer targetPatientId, Integer targetDoctorId, LocalDate targetDate) {
        if (targetPatientId == null) {
            this.targetPatientId = Optional.empty();
        } else {
            this.targetPatientId = Optional.of(targetPatientId);
        }

        if (targetDoctorId == null) {
            this.targetDoctorId = Optional.empty();
        } else {
            this.targetDoctorId = Optional.of(targetDoctorId);
        }

        if (targetDate == null) {
            this.targetDate = Optional.empty();
        } else {
            this.targetDate = Optional.of(targetDate);
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String messageSuccess;
        Predicate<MedicalHistory> predicateListMedHistIsPid =
            x -> {
                if (!targetPatientId.isPresent()) {
                    return true;
                }
                return x.getPatientId() == targetPatientId.get(); };

        Predicate<MedicalHistory> predicateListMedHistIsDid =
            x -> {
                if (!targetDoctorId.isPresent()) {
                    return true;
                }
                return x.getDoctorId() == targetDoctorId.get(); };

        Predicate<MedicalHistory> predicateListMedHistIsDate =
            x -> {
                if (!targetDate.isPresent()) {
                    return true;
                }
                return x.getDate().equals(targetDate.get()); };

        Predicate<MedicalHistory> specifiedPredicate = PREDICATE_SHOW_ALL_MEDHISTS
                .and(predicateListMedHistIsPid).and(predicateListMedHistIsDid).and(predicateListMedHistIsDate);

        model.updateFilteredMedHistList(specifiedPredicate);
        messageSuccess = MESSAGE_SUCCESS;
        return new CommandResult(messageSuccess, CommandResult.ShowPanel.MED_HIST_PANEL);
    }
}
