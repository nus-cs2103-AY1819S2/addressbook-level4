package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.csvmanager.CsvFile;


/**
 * Imports a .json file containing card folders data into the application
 */
public class ImportCommand extends Command {


    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": imports a .json file containing "
            + "card folders information.\n"
            + "File imported must have a .csv extension.\n"
            + "Default file path if not specified will be in the root folder of this application\n"
            + "Parameters: CSV_FILE_NAME\n"
            + "Example: " + COMMAND_WORD + "alice.csv";
    public static final String MESSAGE_FILE_OPS_FAILURE = "Could not import from specified file. Check that it exists "
            + "in root directory";
    public static final String MESSAGE_SUCCESS = "Successfully imported: %1$s";

    private CsvFile csvFile;

    public ImportCommand(CsvFile csvFile) {
        this.csvFile = csvFile;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (model.isInFolder()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_INSIDE_FOLDER);
        }

        try {
            model.importCardFolders(csvFile);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_OPS_FAILURE);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, csvFile.filename));
    }

    /**
     * Returns true if file extension is of .json format.
     */
    private boolean isCorrectFileExtension(String filename) {
        return filename.split("\\.(?=[^\\.]+$)")[1].equals("csv");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ImportCommand // instanceof handles nulls
                && csvFile.filename.equals(((ImportCommand) other).csvFile.filename);
    }
}
