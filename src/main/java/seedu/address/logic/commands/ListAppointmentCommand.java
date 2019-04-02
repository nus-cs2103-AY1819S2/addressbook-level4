package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Lists all appointments in docX to the user.
 */
public class ListAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "list-appt";

    public static final String MESSAGE_SUCCESS = "Listed all appointments";

    /**
     * This command requires the change of panel in the UI, therefore needs to access the UI.
     */
    private static MainWindow mainWindow;

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        mainWindow.showAppointmentPanel();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Addeding the MainWindow as a dependency
     * @param mainWindow
     */
    public static void addMainWindow(MainWindow mainWindow) {
        requireNonNull(mainWindow);
        ListAppointmentCommand.mainWindow = mainWindow;
    }
}
