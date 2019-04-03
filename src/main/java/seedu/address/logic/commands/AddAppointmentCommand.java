package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_APPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.Objects;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Adds a new appointment to a patient.
 */
public class AddAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "add-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new appointment to docX. "
            + "Parameters: "
            + PREFIX_PATIENT_ID + "PATIENT_ID "
            + PREFIX_DOCTOR_ID + "DOCTOR_ID "
            + PREFIX_DATE_OF_APPT + "DATE_OF_APPT "
            + PREFIX_START_TIME + "START_TIME " + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT_ID + "1 "
            + PREFIX_DOCTOR_ID + "1 "
            + PREFIX_DATE_OF_APPT + "2019-06-01 "
            + PREFIX_START_TIME + "9 ";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in docX";

    private Appointment appointment;

    public AddAppointmentCommand(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.hasAppointment(appointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.addAppointment(appointment);
        model.commitDocX();
        return new CommandResult(String.format(MESSAGE_SUCCESS, appointment));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddAppointmentCommand that = (AddAppointmentCommand) o;
        return (appointment.getPatientId() == that.appointment.getPatientId()
                && Objects.equals(appointment.getTimeOfAppt(), that.appointment.getTimeOfAppt()));
    }
}
