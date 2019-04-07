package seedu.knowitall.logic.commands;

import java.io.IOException;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.DuplicateCardFolderException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.Model.State;
import seedu.knowitall.storage.csvmanager.CsvFile;


/**
 * Imports a .json file containing card folders data into the application
 */
public class ImportCommand extends Command {


    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": imports a .csv file containing "
            + "card folders information.\n"
            + "File imported must have a .csv extension.\n"
            + "Default file path if not specified will be in the root folder of this application\n"
            + "Parameters: CSV_FILE_NAME\n"
            + "Example: " + COMMAND_WORD + " alice.csv";
    public static final String MESSAGE_FILE_OPS_FAILURE = "Could not import from specified file. Check that it exists "
            + "in root directory";
    public static final String MESSAGE_SUCCESS = "Successfully imported: %1$s";

    public static final String MESSAGE_DUPLICATE_CARD_FOLDERS = "Card folder already exists in model";

    public static final String MESSAGE_CARD_FIELD_WRONG_SPECIFICATION = "Card fields in csv file do not follow "
            + "flashcard " + "specification:\n";

    private CsvFile csvFile;

    public ImportCommand(CsvFile csvFile) {
        this.csvFile = csvFile;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (model.getState() != State.IN_HOMEDIR) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_INSIDE_FOLDER);
        }

        try {
            model.importCardFolders(csvFile);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_OPS_FAILURE);
        } catch (DuplicateCardFolderException e) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD_FOLDERS);
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_CARD_FIELD_WRONG_SPECIFICATION + e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, csvFile.filename));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ImportCommand // instanceof handles nulls
                && csvFile.filename.equals(((ImportCommand) other).csvFile.filename);
    }
}
