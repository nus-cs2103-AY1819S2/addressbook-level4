package seedu.address.logic.commands.prescription;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRESCRIPTIONS;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.prescription.Prescription;

/**
 * Show all prescriptions in the docX record to the user.
 */
public class ListPrescriptionCommand extends Command {

    public static final String COMMAND_WORD = "list-presc";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all medical histories or medical histories satisfying specified restrictions.\n"
            + COMMAND_WORD + " "
            + "[" + PREFIX_PATIENT_ID + "PATIENT-ID " + "] "
            + "[" + PREFIX_DOCTOR_ID + "DOCTOR-ID " + "] "
            + "Example: " + COMMAND_WORD + "\n"
            + COMMAND_WORD + " " + PREFIX_PATIENT_ID + "1\n"
            + COMMAND_WORD + " " + PREFIX_DOCTOR_ID + "2\n"
            + COMMAND_WORD + " " + PREFIX_PATIENT_ID + "1 " + PREFIX_DOCTOR_ID + "2 ";
    public static final String MESSAGE_SUCCESS = "Listed all prescriptions";

    public static final String MESSAGE_PATIENT_NOT_FOUND =
            "Patient with ID as specified has not had any prescription";
    public static final String MESSAGE_DOCTOR_NOT_FOUND =
            "Doctor with ID as specified has not issued any prescription";

    private final Optional<PersonId> targetPatientId;

    private final Optional<PersonId> targetDoctorId;

    public ListPrescriptionCommand(PersonId targetPatientId, PersonId targetDoctorId) {
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
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Predicate<Prescription> predicateListPrescriptionIsPid;
        Predicate<Prescription> predicateListPrescriptionIsDid;

        if (targetPatientId.isPresent()) {
            Patient patientWithId = model.getPatientById(targetPatientId.get());
            if (patientWithId == null) {
                throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
            }
            predicateListPrescriptionIsPid = x -> x.getPatientId().equals(targetPatientId.get());

        } else {
            predicateListPrescriptionIsPid = x -> true;
        }

        if (targetDoctorId.isPresent()) {
            Doctor doctorWithId = model.getDoctorById(targetDoctorId.get());
            if (doctorWithId == null) {
                throw new CommandException(MESSAGE_DOCTOR_NOT_FOUND);
            }
            predicateListPrescriptionIsDid = x -> x.getDoctorId().equals(targetDoctorId.get());

        } else {
            predicateListPrescriptionIsDid = x -> true;
        }

        Predicate<Prescription> specifiedPredicate = PREDICATE_SHOW_ALL_PRESCRIPTIONS
                .and(predicateListPrescriptionIsPid).and(predicateListPrescriptionIsDid);

        model.updateFilteredPrescriptionList(specifiedPredicate);
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.ShowPanel.PRESC_PANEL);
    }
}

