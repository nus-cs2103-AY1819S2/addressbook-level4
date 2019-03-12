package seedu.address.storage.csv_manager;

import seedu.address.model.ReadOnlyCardFolder;

import java.util.List;

/**
 * Manages the exporting of flashcard folders
 */
public class CsvCardExport {

    private List<ReadOnlyCardFolder> cardFolders;
    private String filename;

    public CsvCardExport(List<ReadOnlyCardFolder> cardFolders, String filename) {
        this.cardFolders = cardFolders;
        this.filename = filename;
    }


}
