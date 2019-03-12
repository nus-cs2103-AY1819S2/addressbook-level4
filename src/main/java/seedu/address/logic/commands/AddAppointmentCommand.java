package seedu.address.logic.commands;

import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Adds a new appointment to a patient.
 */
public class AddAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "add-appt";

    private Index index;
    private Appointment appointment;

    public AddAppointmentCommand(Index index, Appointment appointment) {
        this.index = index;
        this.appointment = appointment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException("index: " + index + "appointment: " + appointment.value);
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
        return Objects.equals(index, that.index) && Objects.equals(appointment.value, that.appointment.value);
    }
}
