package seedu.knowitall.storage.csvmanager;

import java.io.IOException;
import java.util.List;

import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.ReadOnlyCardFolder;


/**
 * API for export and import of card folders
 */
interface CsvCommands {

    public CardFolder readFoldersToCsv(CsvFile csvFile) throws IOException, CommandException;

    public void writeFoldersToCsv(List<ReadOnlyCardFolder> cardFolders) throws IOException;

}
