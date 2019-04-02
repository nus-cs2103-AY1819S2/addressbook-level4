package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDHISTS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Lists all medical histories in the docX record to the user.
 */
public class ListMedHistCommand extends Command {

    public static final String COMMAND_WORD = "list-med-hist";

    public static final String MESSAGE_SUCCESS = "Listed all medical histories";

    /**
     * This command requires the change of panel in the UI, therefore needs to access the UI.
     */
    private static MainWindow mainWindow;


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredMedHistList(PREDICATE_SHOW_ALL_MEDHISTS);
        mainWindow.showMedHistPanel();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Addeding the MainWindow as a dependency
     * @param mainWindow
     */
    public static void addMainWindow(MainWindow mainWindow) {
        requireNonNull(mainWindow);
        ListMedHistCommand.mainWindow = mainWindow;
    }
}
