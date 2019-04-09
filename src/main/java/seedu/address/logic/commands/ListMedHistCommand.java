package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_MEDHIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDHISTS;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;
import seedu.address.model.person.PersonId;

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

    public static final String MESSAGE_PATIENT_NOT_FOUND =
            "Patient with the ID is not found. Please enter a valid patient ID.";
    public static final String MESSAGE_DOCTOR_NOT_FOUND =
            "Doctor with the ID is not found. Please enter a valid doctor ID.";

    private final Optional<PersonId> targetPatientId;

    private final Optional<PersonId> targetDoctorId;

    private final Optional<ValidDate> targetDate;

    public ListMedHistCommand(PersonId targetPatientId, PersonId targetDoctorId, ValidDate targetDate) {
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
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Predicate<MedicalHistory> predicateListMedHistIsPid;
        Predicate<MedicalHistory> predicateListMedHistIsDid;
        Predicate<MedicalHistory> predicateListMedHistIsDate;

        if (targetPatientId.isPresent()) {
            Patient patientWithId = model.getPatientById(targetPatientId.get());
            if (patientWithId == null) {
                throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
            }
            predicateListMedHistIsPid  = x -> x.getPatientId().equals(targetPatientId.get());

        } else {
            predicateListMedHistIsPid = x -> true;
        }

        if (targetDoctorId.isPresent()) {
            Doctor doctorWithId = model.getDoctorById(targetDoctorId.get());
            if (doctorWithId == null) {
                throw new CommandException(MESSAGE_DOCTOR_NOT_FOUND);
            }
            predicateListMedHistIsDid  = x -> x.getDoctorId().equals(targetDoctorId.get());

        } else {
            predicateListMedHistIsDid  = x -> true;
        }

        if (targetDate.isPresent()) {
            predicateListMedHistIsDate = x -> x.getDate().equals(targetDate.get());
        } else {
            predicateListMedHistIsDate = x -> true;
        }

        Predicate<MedicalHistory> specifiedPredicate = PREDICATE_SHOW_ALL_MEDHISTS
                .and(predicateListMedHistIsPid).and(predicateListMedHistIsDid).and(predicateListMedHistIsDate);

        model.updateFilteredMedHistList(specifiedPredicate);
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.ShowPanel.MED_HIST_PANEL);
    }
}
