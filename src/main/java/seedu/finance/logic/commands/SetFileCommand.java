package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_FILE;

import java.nio.file.Path;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;

/**
 * Sets a file path to store data.
 */
public class SetFileCommand extends Command {

    public static final String COMMAND_WORD = "setfile";

    public static final int MAX_FILE_LENGTH = 250;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the file path to store data. "
            + "Parameters: "
            + PREFIX_FILE + "FILENAME (Without file extension or folder path) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "finance";
    public static final String MESSAGE_SUCCESS = "File Set: %1$s";

    public static final String MESSAGE_CONSTRAINTS =
            "Filename must be " + MAX_FILE_LENGTH + " or shorter "
                    + "and should not contain special characters, '\\' character, or be blank.";

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

        model.addPreviousDataFile(model.getFinanceTrackerFilePath());
        model.changeFinanceTrackerFile(path);
        model.commitFinanceTracker(true);
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
