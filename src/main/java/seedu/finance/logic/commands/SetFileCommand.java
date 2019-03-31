package seedu.finance.logic.commands;

import seedu.finance.commons.core.LogsCenter;
import seedu.finance.commons.exceptions.DataConversionException;
import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.Model;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.util.SampleDataUtil;
import seedu.finance.storage.JsonFinanceTrackerStorage;

import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_FILE;

public class SetFileCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(JsonFinanceTrackerStorage.class);

    public static final String COMMAND_WORD = "setfile";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the file path to store data. "
            + "Parameters: "
            + PREFIX_FILE + "FILE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "data\\finance.json";
    public static final String MESSAGE_SUCCESS = "File Set: %1$s";

    private final Path path;

    /**
     * Creates a SetCommand to set the specificed {@code Amount} as budget
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
                logger.info("Data file not found. Will be starting with a sample FinanceTracker");
            }
            initialData = financeTrackerOptional.orElseGet(SampleDataUtil::getSampleFinanceTracker);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty FinanceTracker");
            initialData = new FinanceTracker();
        }

        model.setFinanceTrackerFilePath(path);
        model.setFinanceTracker(initialData);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_SUCCESS, path));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instaceof handles null
        if (!(other instanceof SetFileCommand)) {
            return false;
        }

        SetFileCommand e = (SetFileCommand) other;
        return path.equals(e.path);
    }
}
