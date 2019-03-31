package seedu.knowitall.logic.commands;

import java.io.IOException;
import java.util.List;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.CardFolderNotFoundException;
import seedu.knowitall.model.Model;
import seedu.knowitall.storage.csvmanager.exceptions.CsvManagerNotInitialized;


/**
 * Exports single or multiple card folders into a .csv file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": exports single or multiple card folders into"
            + " their respective .csv files.\n"
            + "Parameters: INDEX (Index specifies the card folder index to export) \n"
            + "Can specify more than one card folder index to export\n"
            + "Example: " + COMMAND_WORD + "1 3 5 7";

    public static final String MESSAGE_SUCCESS = "Successfully exported card folders";

    public static final String MESSAGE_MISSING_CARD_FOLDERS = "Could not find the specified folder index: ";

    public static final String MESSAGE_FILE_OPS_FAILURE = "Could not export to specified file";

    private List<Integer> cardFolderIndexes;

    public ExportCommand(List<Integer> cardFolderIndexes) {
        this.cardFolderIndexes = cardFolderIndexes;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        // check whether model contains the card folders desired. Catch exception thrown

        if (model.isInFolder()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_INSIDE_FOLDER);
        }
        try {
            model.exportCardFolders(cardFolderIndexes);
        } catch (CardFolderNotFoundException e) {
            throw new CommandException(MESSAGE_MISSING_CARD_FOLDERS + e.getMessage());
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_OPS_FAILURE);
        } catch (CsvManagerNotInitialized e) {
            throw new CommandException(Messages.MESSAGE_CSV_MANAGER_NOT_INITIALIZED);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ExportCommand // instanceof handles nulls
                && cardFolderIndexes.containsAll(((ExportCommand) other).cardFolderIndexes);
    }
}
