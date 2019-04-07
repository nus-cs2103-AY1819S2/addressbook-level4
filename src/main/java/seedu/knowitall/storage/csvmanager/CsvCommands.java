package seedu.knowitall.storage.csvmanager;

import java.io.IOException;
import java.util.List;

import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.storage.csvmanager.exceptions.IncorrectCsvHeadersException;


/**
 * API for export and import of card folders
 */
interface CsvCommands {

    public CardFolder readFoldersToCsv(CsvFile csvFile) throws IOException, CommandException,
            IncorrectCsvHeadersException;

    public void writeFoldersToCsv(List<ReadOnlyCardFolder> cardFolders) throws IOException;

}
