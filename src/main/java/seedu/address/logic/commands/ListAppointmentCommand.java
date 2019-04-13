package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PLACEHOLDER_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentDoctorId;
import seedu.address.model.appointment.AppointmentPatientId;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentTime;

/**
 * Lists all appointments in docX to the user.
 */
public class ListAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "list-appt";
    public static final String COMMAND_EXAMPLE = "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE_OF_APPT + "2019-06-01 ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "[" + PREFIX_PATIENT_ID + PLACEHOLDER_PATIENT_ID + "] "
            + "[" + PREFIX_DOCTOR_ID + PLACEHOLDER_DOCTOR_ID + "] "
            + "[" + PREFIX_DATE_OF_APPT + PLACEHOLDER_DATE_OF_APPT + "] "
            + "[" + PREFIX_START_TIME + PLACEHOLDER_START_TIME + "] "
            + COMMAND_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Listed all appointments";
    public static final String MESSAGE_SUCCESS_FILTERED_PATIENT = ", filtered by patient ID ";
    public static final String MESSAGE_SUCCESS_FILTERED_DOCTOR = ", filtered by doctor ID ";
    public static final String MESSAGE_SUCCESS_FILTERED_DATE = ", filtered by date ";
    public static final String MESSAGE_SUCCESS_FILTERED_TIME = ", filtered by time ";
    public static final String MESSAGE_SUCCESS_FILTERED_STATUS = ", filtered by status ";
    public static final String MESSAGE_PATIENT_NOT_FOUND =
            "Appointment with the specified patient ID is not found.";
    public static final String MESSAGE_DOCTOR_NOT_FOUND =
            "Appointment with the specified doctor ID is not found.";
    public static final String MESSAGE_DATE_NOT_FOUND =
            "Appointment with the specified date is not found.";
    public static final String MESSAGE_TIME_NOT_FOUND =
            "Appointment with the specified time is not found.";
    public static final String MESSAGE_STATUS_NOT_FOUND =
            "Appointment with the specified status is not found.";

    private final ListAppointmentDescriptor listAppointmentDescriptor;

    public ListAppointmentCommand(ListAppointmentDescriptor listAppointmentDescriptor) {
        this.listAppointmentDescriptor = listAppointmentDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        List<Predicate<Appointment>> predicates = new ArrayList<>();
        StringBuilder result = new StringBuilder(MESSAGE_SUCCESS);

        listAppointmentDescriptor.getPatientId().ifPresent(id -> {
            predicates.add(appointment -> {
                return appointment.getPatientId().equals(id);
            });
            result.append(MESSAGE_SUCCESS_FILTERED_PATIENT);
        });

        listAppointmentDescriptor.getDoctorId().ifPresent(id -> {
            predicates.add(appointment -> {
                return appointment.getDoctorId().equals(id);
            });
            result.append(MESSAGE_SUCCESS_FILTERED_DOCTOR);
        });

        listAppointmentDescriptor.getDate().ifPresent(date -> {
            predicates.add(appointment -> {
                return appointment.getDate().equals(date);
            });
            result.append(MESSAGE_SUCCESS_FILTERED_DATE);
        });

        listAppointmentDescriptor.getTime().ifPresent(time -> {
            predicates.add(appointment -> {
                return appointment.getTime().equals(time);
            });
            result.append(MESSAGE_SUCCESS_FILTERED_TIME);
        });

        listAppointmentDescriptor.getStatus().ifPresent(status -> {
            predicates.add(appointment -> {
                return appointment.getAppointmentStatus().equals(status);
            });
            result.append(MESSAGE_SUCCESS_FILTERED_STATUS);
        });

        predicates.forEach(e -> System.out.println(e));

        // reduce predicate list to a single predicate
        Predicate<Appointment> combinedPredicate = predicates.stream().reduce(x-> true, Predicate::and);

        requireNonNull(model);
        model.updateFilteredAppointmentList((PREDICATE_SHOW_ALL_APPOINTMENTS.and(combinedPredicate)));

        return new CommandResult(result.toString(), CommandResult.ShowPanel.APPOINTMENT_PANEL);
    }

    /**
     * Stores the fields to filter appointments by.
     */
    public static class ListAppointmentDescriptor {
        private Optional<AppointmentPatientId> patientId = Optional.empty();
        private Optional<AppointmentDoctorId> doctorId = Optional.empty();
        private Optional<AppointmentDate> date = Optional.empty();
        private Optional<AppointmentTime> time = Optional.empty();
        private Optional<AppointmentStatus> status = Optional.empty();

        public ListAppointmentDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public ListAppointmentDescriptor(ListAppointmentDescriptor toCopy) {
            setPatientId(toCopy.patientId);
            setDoctorId(toCopy.doctorId);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setStatus(toCopy.status);
        }


        public Optional<AppointmentPatientId> getPatientId() {
            return patientId;
        }

        public void setPatientId(Optional<AppointmentPatientId> patientId) {
            this.patientId = patientId;
        }

        public Optional<AppointmentDoctorId> getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(Optional<AppointmentDoctorId> doctorId) {
            this.doctorId = doctorId;
        }

        public Optional<AppointmentDate> getDate() {
            return date;
        }

        public void setDate(Optional<AppointmentDate> date) {
            this.date = date;
        }

        public Optional<AppointmentTime> getTime() {
            return time;
        }

        public void setTime(Optional<AppointmentTime> time) {
            this.time = time;
        }

        public Optional<AppointmentStatus> getStatus() {
            return status;
        }

        public void setStatus(Optional<AppointmentStatus> status) {
            this.status = status;
        }
    }
}
