package seedu.knowitall.storage.csvmanager;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

/**
 * Represents a Csv file for either import or export
 */
public class CsvFile {

    public static final String MESSAGE_CONSTRAINTS = "File name should not be left blank and should have"
            + ".csv format";
    public static final String FILENAME_CONSTRAINTS = "file name must only contain letters, "
            + "numbers and whitespaces,\nshould not be left blank"
            + " and should be between 1 and 50 characters";
    public static final String FILE_EXT_REGEX = "\\.(?=[^.]+$)";

    public final String filename;

    public CsvFile(String filename) {
        this.filename = filename;
    }

    public static boolean isValidFileName(String filename) {
        return !isFileNameEmpty(filename) && isCorrectFileExtension(filename);
    }

    private static boolean isFileNameEmpty(String filename) {
        return filename.isEmpty();
    }


    /**
     * Returns true if file extension is of .csv format.
     */
    private static boolean isCorrectFileExtension(String filename) {
        Pattern pattern = Pattern.compile(FILE_EXT_REGEX);
        Matcher matcher = pattern.matcher(filename);
        if (matcher.find()) {
            return filename.split(FILE_EXT_REGEX)[1].equals("csv");
        } else {
            return false;
        }
    }

    public String getFileNameWithoutExt() {
        return this.filename.split(FILE_EXT_REGEX)[0];
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof CsvFile && ((CsvFile) obj).filename.equals(this.filename);
    }

    @Override
    public int hashCode() {
        return filename.hashCode();
    }
}
