package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_STATUS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.person.Patient;

/**
 * Change the status of an Appointment.
 */
public class MarkAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "mark-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the status of an existing appointment. "
            + "The status that can be set are: "
            + AppointmentStatus.ACTIVE.name() + ", "
            + AppointmentStatus.CANCELLED.name() + ", "
            + AppointmentStatus.COMPLETED.name() + ", "
            + AppointmentStatus.MISSED.name() + "\n"
            + "Parameters: INDEX (must be a positive integer) " + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPT_STATUS + "CANCELLED";

    public static final String MESSAGE_MARK_APPT_SUCCESS = "Changed status of Appointment: %1$s";
    public static final String MESSAGE_STATUS_MISSING = "The new appointment status must be provided.";
    public static final String MESSAGE_DUPLICATE_APPT = "This appointment already exists in docX.";
    public static final String MESSAGE_STATUS_UNCHANGED = "The new status is the same as the existing status.";

    private final Index index;
    private final ChangedAppointmentDescriptor changedAppointmentDescriptor;

    /**
     * @param index                        of the appointment in the filtered appointment list
     * @param changedAppointmentDescriptor details of the changed status
     */
    public MarkAppointmentCommand(Index index, ChangedAppointmentDescriptor changedAppointmentDescriptor) {
        requireNonNull(index);
        requireNonNull(changedAppointmentDescriptor);

        this.index = index;
        this.changedAppointmentDescriptor = new ChangedAppointmentDescriptor(changedAppointmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToChange = lastShownList.get(index.getZeroBased());
        Appointment changedAppointment = createChangedAppointment(appointmentToChange, changedAppointmentDescriptor);

        Patient patientStatusToChange = appointmentToChange.getPatient();

        if (!appointmentToChange.isSameAppointment(changedAppointment) && model.hasAppointment(changedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPT);
        }

        if (appointmentToChange.getAppointmentStatus().equals(changedAppointment.getAppointmentStatus())) {
            throw new CommandException(MESSAGE_STATUS_UNCHANGED);
        }

        if ((changedAppointment.getAppointmentStatus().equals(AppointmentStatus.COMPLETED)
                || changedAppointment.getAppointmentStatus().equals(AppointmentStatus.MISSED))
                && !appointmentToChange.isPast()) {
            throw new CommandException(AppointmentStatus.MESSAGE_CONSTRAINT_PAST);
        }

        Patient changedPatient =
                patientStatusToChange.changeAppointmentStatus(changedAppointment.getAppointmentStatus());
        model.setPatient(patientStatusToChange, changedPatient);
        model.setAppointment(appointmentToChange, changedAppointment);
        model.commitDocX();
        return new CommandResult(String.format(MESSAGE_MARK_APPT_SUCCESS, changedAppointment));
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code appointmentToChange}
     * changed with {@code changedAppointmentDescriptor}.
     */
    private static Appointment createChangedAppointment(Appointment appointmentToChange,
                                                        ChangedAppointmentDescriptor changedAppointmentDescriptor) {
        assert appointmentToChange != null;

        AppointmentStatus newStatus = changedAppointmentDescriptor.getStatus();

        return new Appointment(appointmentToChange.getPatientId(),
                appointmentToChange.getDoctorId(),
                appointmentToChange.getDate(),
                appointmentToChange.getTime(),
                newStatus);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAppointmentCommand)) {
            return false;
        }

        // state check
        MarkAppointmentCommand e = (MarkAppointmentCommand) other;
        return index.equals(e.index)
                && changedAppointmentDescriptor.equals(e.changedAppointmentDescriptor);
    }

    /**
     * Stores new status of the appointment.
     */
    public static class ChangedAppointmentDescriptor {
        private AppointmentStatus status;

        public ChangedAppointmentDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public ChangedAppointmentDescriptor(ChangedAppointmentDescriptor toCopy) {
            setStatus(toCopy.status);
        }

        /**
         * Returns true if status is changed.
         */
        public boolean isStatusChanged() {
            return status != null;
        }

        public void setStatus(AppointmentStatus status) {
            this.status = status;
        }

        public AppointmentStatus getStatus() {
            return status;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof MarkAppointmentCommand.ChangedAppointmentDescriptor)) {
                return false;
            }

            // state check
            ChangedAppointmentDescriptor e = (ChangedAppointmentDescriptor) other;

            return getStatus().equals(e.getStatus());
        }
    }

}
