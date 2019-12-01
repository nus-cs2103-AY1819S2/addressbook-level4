package seedu.address.logic.commands.medicalhistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_MEDHIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDHISTS;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;

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
            "Listed all medical histories filtered by specified constraint(s) if any. "
                    + "Write up of medical histories are not be shown in the list.";

    private final ListMedHistDescriptor listMedHistDescriptor;

    public ListMedHistCommand(ListMedHistDescriptor listMedHistDescriptor) {
        this.listMedHistDescriptor = listMedHistDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Predicate<MedicalHistory> predicateListMedHistIsPid;
        Predicate<MedicalHistory> predicateListMedHistIsDid;
        Predicate<MedicalHistory> predicateListMedHistIsDate;

        // set patient id filter
        if (listMedHistDescriptor.getPatientId().isPresent()) {
            PersonId patientId = listMedHistDescriptor.getPatientId().get();
            Patient patientWithId = model.getPatientById(patientId);
            if (patientWithId == null) {
                throw new CommandException(AddMedHistCommand.MESSAGE_PATIENT_NOT_FOUND);
            }
            predicateListMedHistIsPid = medHist -> medHist.getPatientId().equals(patientId);
        } else {
            predicateListMedHistIsPid = medHist -> true;
        }

        // set doctor id filter
        if (listMedHistDescriptor.getDoctorId().isPresent()) {
            PersonId doctorId = listMedHistDescriptor.getDoctorId().get();
            Doctor doctorWithId = model.getDoctorById(doctorId);
            if (doctorWithId == null) {
                throw new CommandException(AddMedHistCommand.MESSAGE_DOCTOR_NOT_FOUND);
            }
            predicateListMedHistIsDid = medHist -> medHist.getDoctorId().equals(doctorId);

        } else {
            predicateListMedHistIsDid = medHist -> true;
        }

        // set medical history filter
        if (listMedHistDescriptor.getDate().isPresent()) {
            predicateListMedHistIsDate = x -> x.getDate().equals(listMedHistDescriptor.getDate().get());
        } else {
            predicateListMedHistIsDate = x -> true;
        }

        Predicate<MedicalHistory> specifiedPredicate = PREDICATE_SHOW_ALL_MEDHISTS
                .and(predicateListMedHistIsPid).and(predicateListMedHistIsDid).and(predicateListMedHistIsDate);

        model.updateFilteredMedHistList(specifiedPredicate);
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.ShowPanel.MED_HIST_PANEL);
    }

    /**
     * Stores the fields to filter medical history by.
     */
    public static class ListMedHistDescriptor {

        private Optional<PersonId> targetPatientId;

        private Optional<PersonId> targetDoctorId;

        private Optional<ValidDate> targetDate;

        public ListMedHistDescriptor() {
            targetPatientId = Optional.empty();
            targetDoctorId = Optional.empty();
            targetDate = Optional.empty();
        }

        /**
         * Copy constructor. For defensive purposes, ensures only a copy is used.
         */
        public ListMedHistDescriptor(ListMedHistDescriptor toCopy) {
            setPatientId(toCopy.targetPatientId);
            setDoctorId(toCopy.targetDoctorId);
            setDate(toCopy.targetDate);
        }

        public Optional<PersonId> getPatientId() {
            return targetPatientId;
        }

        public void setPatientId(Optional<PersonId> patientId) {
            this.targetPatientId = patientId;
        }

        public Optional<PersonId> getDoctorId() {
            return targetDoctorId;
        }

        public void setDoctorId(Optional<PersonId> doctorId) {
            this.targetDoctorId = doctorId;
        }

        public Optional<ValidDate> getDate() {
            return targetDate;
        }

        public void setDate(Optional<ValidDate> date) {
            this.targetDate = date;
        }
    }
}
