package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.logic.parser.DeleteAppCommandParser;
import quickdocs.model.Model;
import quickdocs.model.appointment.Appointment;


/**
 * Adds an appointment to quickdocs.
 */
public class DeleteAppCommand extends Command {

    public static final String COMMAND_WORD = "deleteapp";
    public static final String COMMAND_ALIAS = "da";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an appointment in quickdocs. "
            + "Parameters: "
            + DeleteAppCommandParser.PREFIX_DATE + "DATE "
            + DeleteAppCommandParser.PREFIX_START + "START "
            + "Example: " + COMMAND_WORD + " "
            + DeleteAppCommandParser.PREFIX_DATE + "2019-10-23 "
            + DeleteAppCommandParser.PREFIX_START + "16:00\n";

    public static final String MESSAGE_SUCCESS = "Appointment deleted:\n%1$s\n";
    public static final String MESSAGE_APPOINTMENT_NOT_FOUND = "No appointment with the given date and time found";

    private final LocalDate date;
    private final LocalTime start;

    /**
     * Creates a DeleteAppCommand to delete the specified {@code Appointment}
     */
    public DeleteAppCommand(LocalDate date, LocalTime start) {
        this.date = date;
        this.start = start;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

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
