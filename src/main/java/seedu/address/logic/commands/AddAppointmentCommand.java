package seedu.address.logic.commands;

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

    private Appointment appointment;

    public AddAppointmentCommand(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return new CommandResult("successfuly added appointment");
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
