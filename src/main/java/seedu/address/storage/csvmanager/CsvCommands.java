package seedu.address.storage.csvmanager;

import seedu.address.model.ReadOnlyCardFolder;

import java.io.IOException;
import java.util.List;

/**
 * API for export and import of card folders
 */
interface CsvCommands {

    public void writeFoldersToCsv(List<ReadOnlyCardFolder> cardFolders, CsvFile filename) throws IOException;

    public void readFoldersToCsv(CsvFile csvFile);

}
