package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all appointments in docX to the user.
 */
public class ListAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "list-appt";

    public static final String MESSAGE_SUCCESS = "Listed all appointments";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.ShowPanel.APPOINTMENT_PANEL);
    }
}
