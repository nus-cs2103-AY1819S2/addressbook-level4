package seedu.address.storage.csv_manager;

import seedu.address.model.ReadOnlyCardFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Manages the exporting of flashcard folders
 */
public class CsvCardExport {

    private static final String COMMA_DELIMITTER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";



    private List<ReadOnlyCardFolder> cardFolders;
    private String filename;

    public CsvCardExport(List<ReadOnlyCardFolder> cardFolders, String filename) {
        this.cardFolders = cardFolders;
        this.filename = filename;
    }

    private void writeFoldersToCSV() throws IOException {
        String filepath = getFilePath();
    }

    private String getFilePath() throws IOException {
        String defaultFilePath = new File("./").getCanonicalPath();
        return defaultFilePath + "/" + filename;
    }




}
