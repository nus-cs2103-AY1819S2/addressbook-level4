package seedu.address.storage.csv_manager;

public class CsvFile {

    public static final String MESSAGE_FILENAME_CONSTRAINTS = "File name should not be left blank and should have"
            + ".csv format";

    public final String filename;

    public CsvFile(String filename) {
        this.filename = filename;
    }

    public static boolean isValidFileName(String filename) {
        return isCorrectFileExtension(filename) && !isFileNameEmpty(filename);
    }
    
    private static boolean isFileNameEmpty(String filename) {
        return filename.isEmpty();
    }


    /**
     * Returns true if file extension is of .json format.
     */
    private static boolean isCorrectFileExtension(String filename) {
        return filename.split("\\.(?=[^\\.]+$)")[1].equals("csv");
    }
}
