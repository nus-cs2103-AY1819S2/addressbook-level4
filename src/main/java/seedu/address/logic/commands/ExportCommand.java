package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOLDERNAME;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CardFolderNotFoundException;
import seedu.address.model.Model;
import seedu.address.storage.csvmanager.CardFolderExport;
import seedu.address.storage.csvmanager.CsvFile;

/**
 * Exports single or multiple card folders into a .csv file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": exports single or multiple card folders into"
            + "their respective .csv files."
            + "Parameters: INDEX (Index specifies the card folder index to export) \n"
            + "Can specify more than one card folder index to export"
            + "Example: " + COMMAND_WORD + "1 3 5 7";

    public static final String MESSAGE_SUCCESS = "Successfully exported card folders to: $1%s";

    public static final String MESSAGE_MISSING_CARD_FOLDERS = "Could not find the specified folder index: ";

    public static final String MESSAGE_FILE_OPS_FAILURE = "Could not export to specified file";

    private List<Integer> cardFolderIndexes;
    /*
    private Set<CardFolderExport> cardFolders;
    private CsvFile filename;


    public ExportCommand(Set<CardFolderExport> cardFolders, CsvFile filename) {
        this.cardFolders = cardFolders;
        this.filename = filename;
    }
    */

    public ExportCommand(List<Integer> cardFolderIndexes) {
        this.cardFolderIndexes = cardFolderIndexes;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        // check whether model contains the card folders desired. Catch exception thrown
        try {
            model.exportCardFolders(cardFolderIndexes);
        } catch (CardFolderNotFoundException e) {
            throw new CommandException(MESSAGE_MISSING_CARD_FOLDERS + e.getMessage());
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_OPS_FAILURE);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ExportCommand // instanceof handles nulls
                && (cardFolders.containsAll(((ExportCommand) other).cardFolders)
                && filename.equals(((ExportCommand) other).filename));
    }
    */
}
