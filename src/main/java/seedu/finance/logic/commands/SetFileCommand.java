package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_FILE;

import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.finance.commons.core.LogsCenter;
import seedu.finance.commons.exceptions.DataConversionException;
import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.Model;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.storage.JsonFinanceTrackerStorage;
import seedu.finance.storage.StorageManager;



/**
 * Sets a file path to store data.
 */
public class SetFileCommand extends Command {

    public static final String COMMAND_WORD = "setfile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the file path to store data. "
            + "Parameters: "
            + PREFIX_FILE + "FILENAME (Without file extension or folder path) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "finance";
    public static final String MESSAGE_SUCCESS = "File Set: %1$s";

    public static final String MESSAGE_CONSTRAINTS
            = "Filename should not contain special characters, '\\' character, or be blank.";

    private static final Logger logger = LogsCenter.getLogger(JsonFinanceTrackerStorage.class);

    private final Path path;

    /**
     * Creates a SetFileCommand to set the specified Path as the new Finance Tracker Storage json.
     */
    public SetFileCommand(Path path) {
        requireNonNull(path);

        this.path = path;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        JsonFinanceTrackerStorage newStorage = new JsonFinanceTrackerStorage(path);
        Optional<ReadOnlyFinanceTracker> financeTrackerOptional;
        ReadOnlyFinanceTracker initialData;
        try {
            financeTrackerOptional = newStorage.readFinanceTracker();
            if (!financeTrackerOptional.isPresent()) {
                logger.info("Data file not found. A new empty FinanceTracker will be created with file name.");
                initialData = new FinanceTracker();
            } else {
                initialData = financeTrackerOptional.get();
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty FinanceTracker");
            initialData = new FinanceTracker();
        }

        model.setFinanceTrackerFilePath(path);
        model.setFinanceTracker(initialData);
        model.commitFinanceTracker();
        StorageManager.setFinanceTrackerStorage(newStorage);
        return new CommandResult(String.format(MESSAGE_SUCCESS, path), true, false, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof SetFileCommand)) {
            return false;
        }

        SetFileCommand e = (SetFileCommand) other;
        return path.equals(e.path);
    }
}
