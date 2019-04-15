package seedu.address.logic.commands.appointment;

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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentChronology;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentDoctorId;
import seedu.address.model.appointment.AppointmentPatientId;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.person.PersonId;

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
    public static final String MESSAGE_SUCCESS_FILTERED_PATIENT = ", filtered by patient ID";
    public static final String MESSAGE_SUCCESS_FILTERED_DOCTOR = ", filtered by doctor ID";
    public static final String MESSAGE_SUCCESS_FILTERED_DATE = ", filtered by date";
    public static final String MESSAGE_SUCCESS_FILTERED_TIME = ", filtered by time";
    public static final String MESSAGE_SUCCESS_FILTERED_STATUS = ", filtered by status";
    public static final String MESSAGE_SUCCESS_FILTERED_CHRONOLOGY = ", filtered by chronology";
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
        this.listAppointmentDescriptor = new ListAppointmentDescriptor(listAppointmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Predicate<Appointment>> predicates = new ArrayList<>();
        StringBuilder result = new StringBuilder(MESSAGE_SUCCESS);

        if (listAppointmentDescriptor.getPatientId().isPresent()) {
            PersonId id = listAppointmentDescriptor.getPatientId().get();
            if (model.getPatientById(id) == null) {
                throw new CommandException(AddAppointmentCommand.MESSAGE_PATIENT_NOT_FOUND);
            }
            predicates.add(appointment -> {
                return appointment.getPatientId().equals(id);
            });
            result.append(MESSAGE_SUCCESS_FILTERED_PATIENT + ": ");
            result.append(id);
        }

        if (listAppointmentDescriptor.getDoctorId().isPresent()) {
            PersonId id = listAppointmentDescriptor.getDoctorId().get();
            if (model.getDoctorById(id) == null) {
                throw new CommandException(AddAppointmentCommand.MESSAGE_DOCTOR_NOT_NOT_FOUND);
            }
            predicates.add(appointment -> {
                return appointment.getDoctorId().equals(id);
            });
            result.append(MESSAGE_SUCCESS_FILTERED_DOCTOR + ": ");
            result.append(id);
        }

        listAppointmentDescriptor.getDate().ifPresent(date -> {
            predicates.add(appointment -> {
                return appointment.getDate().equals(date);
            });
            result.append(MESSAGE_SUCCESS_FILTERED_DATE + ": ");
            result.append(date);
        });

        listAppointmentDescriptor.getTime().ifPresent(time -> {
            predicates.add(appointment -> {
                return appointment.getTime().equals(time);
            });
            result.append(MESSAGE_SUCCESS_FILTERED_TIME + ": ");
            result.append(time);
        });

        listAppointmentDescriptor.getStatus().ifPresent(status -> {
            predicates.add(appointment -> {
                return appointment.getAppointmentStatus().equals(status);
            });
            result.append(MESSAGE_SUCCESS_FILTERED_STATUS + ": ");
            result.append(status);
        });

        listAppointmentDescriptor.getChronology().ifPresent(chronology -> {
            AppointmentChronology.refreshCurrentTime();
            switch(chronology) {
            case PAST:
                predicates.add(appointment -> {
                    return AppointmentChronology.isInPast(appointment);
                });
                break;
            case FUTURE:
                predicates.add(appointment -> {
                    return AppointmentChronology.isInFuture(appointment);
                });
                break;
            default:
                break;
            }

            result.append(MESSAGE_SUCCESS_FILTERED_CHRONOLOGY + ": ");
            result.append(chronology);
        });

        // reduce predicate list to a single predicate
        Predicate<Appointment> combinedPredicate = predicates.stream().reduce(x-> true, Predicate::and);
        model.updateFilteredAppointmentList((PREDICATE_SHOW_ALL_APPOINTMENTS.and(combinedPredicate)));

        return new CommandResult(result.toString(), CommandResult.ShowPanel.APPOINTMENT_PANEL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListAppointmentCommand that = (ListAppointmentCommand) o;
        return Objects.equals(listAppointmentDescriptor, that.listAppointmentDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listAppointmentDescriptor);
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
        private Optional<AppointmentChronology> chronology = Optional.empty();

        public ListAppointmentDescriptor() {
        }

        /**
         * Copy constructor. For defensive purposes, ensures only a copy is used.
         */
        public ListAppointmentDescriptor(ListAppointmentDescriptor toCopy) {
            setPatientId(toCopy.patientId);
            setDoctorId(toCopy.doctorId);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setStatus(toCopy.status);
            setChronology(toCopy.chronology);
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

        public Optional<AppointmentChronology> getChronology() {
            return chronology;
        }

        public void setChronology(Optional<AppointmentChronology> chronology) {
            this.chronology = chronology;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ListAppointmentDescriptor that = (ListAppointmentDescriptor) o;
            return Objects.equals(patientId, that.patientId)
                    && Objects.equals(doctorId, that.doctorId)
                    && Objects.equals(date, that.date)
                    && Objects.equals(time, that.time)
                    && Objects.equals(status, that.status)
                    && Objects.equals(chronology, that.chronology);
        }

        @Override
        public int hashCode() {
            return Objects.hash(patientId, doctorId, date, time, status, chronology);
        }
    }
}
