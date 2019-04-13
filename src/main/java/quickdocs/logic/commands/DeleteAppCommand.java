package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;
import static quickdocs.logic.parser.DeleteAppCommandParser.PREFIX_DATE;
import static quickdocs.logic.parser.DeleteAppCommandParser.PREFIX_START;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.appointment.Appointment;
import quickdocs.model.appointment.AppointmentManager;


/**
 * Deletes an {@code Appointment} in QuickDocs.
 */
public class DeleteAppCommand extends Command {

    public static final String COMMAND_WORD = "deleteapp";
    public static final String COMMAND_ALIAS = "da";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an appointment in QuickDocs. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_START + "START "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2019-10-23 "
            + PREFIX_START + "16:00\n";

    public static final String MESSAGE_SUCCESS = "Appointment deleted:\n%1$s\n";
    public static final String MESSAGE_APPOINTMENT_NOT_FOUND = "No appointment with the given date and time found.";
    public static final String MESSAGE_NON_OFFICE_HOURS = "Appointment start time is outside of office hours.";

    private final LocalDate date;
    private final LocalTime start;

    /**
     * Creates a {@code DeleteAppCommand} to delete the specified {@code Appointment}.
     */
    public DeleteAppCommand(LocalDate date, LocalTime start) {
        this.date = date;
        this.start = start;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // check if start time is within office hours
        if (start.isBefore(AppointmentManager.OPENING_HOUR) || start.isAfter(AppointmentManager.CLOSING_HOUR)) {
            throw new CommandException(MESSAGE_NON_OFFICE_HOURS);
        }

        // check if given appointment exists in QuickDocs
        Optional<Appointment> appointmentToDelete = model.getAppointment(date, start);
        if (!appointmentToDelete.isPresent()) {
            throw new CommandException(MESSAGE_APPOINTMENT_NOT_FOUND);
        }

        model.deleteAppointment(appointmentToDelete.get());
        return new CommandResult(String.format(MESSAGE_SUCCESS, appointmentToDelete.get()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAppCommand // instanceof handles nulls
                && date.equals(((DeleteAppCommand) other).date)
                && start.equals(((DeleteAppCommand) other).start));
    }
}
