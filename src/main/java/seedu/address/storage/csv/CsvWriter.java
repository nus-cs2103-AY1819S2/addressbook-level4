package seedu.address.storage.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

/**
 * A CSV Java Library for creating csv files.
 * Currently this library is exclusively for https://github.com/cs2103-ay1819s2-t12-3/main unless otherwise stated by
 * the author LEE WEI HAO JONATHAN (A0166980R)
 *
 * @author LEE WEI HAO JONATHAN (A0166980R)
 */
public class CsvWriter {

    private static final String FILE_OPS_ERROR_MESSAGE = "Could not export data to csv file: ";
    private static String defaultHeading = "";
    private static final Path DEFAULT_EXPORT_FOLDER_PATH = Paths.get("exported");
    private String csvFileName;
    private Model model;
    private File exportFolder;

    public CsvWriter(String csvFileName, Model model) {
        this.csvFileName = csvFileName;
        this.model = model;
    }

    /**
     * Exports the current list in the GUI to a csv file.
     * @throws CommandException If there is an error exporting the current list in the GUI to a csv file.
     */
    public void export() throws CommandException {
        List<Medicine> currentGuiList = model.getFilteredMedicineList();
        createCsvFile(csvFileName);
    }

    /**
     * Creates a .csv file based on the input csv file name.
     * @param csvFileName The input csv file name.
     * @throws CommandException If there are errors creating the .csv file.
     */
    private void createCsvFile(String csvFileName) throws CommandException {
        createIfExportDirectoryMissing();
        try {
            Files.createFile(Paths.get("exported", csvFileName + ".csv"));
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    /**
     * Converts an input string to be compliant with RFC4180 based on
     * its definition of CSV format.
     * Source: https://tools.ietf.org/html/rfc4180
     * @param input The input string to be converted to CSV compliant format
     * @return The converted string which is in CSV compliant format.
     */
    public String convertStringToCsvFieldCompliantFormat(String input) {

        /**
         * If double quotes are used to enclose fields, then a double-quote appearing inside
         * a field must be escaped by preceding it with another double quote.
         * Based on definition of the CSV Format in RFC4180 point 7.
         */
        if (input.contains("\"")) {
            input = input.replaceAll("\"", "\"\"");
        }


        /**
         * If the input contains a comma or a link break, to ensure the comma is preserved
         * in the CSV file format, the input string is enclosed by double quotation
         * marks.
         * Based on definition of the CSV Format in RFC4180 point 5 and 6.
         */
        if (input.contains(",") || input.contains("\n")) {
            input = "\"" + input + "\"";
        }

        return input;
    }

    /**
     * Creates the export directory if export directory where exported data are stored.
     * @throws CommandException if there are errors creating the export directory.
     */
    private void createIfExportDirectoryMissing() throws CommandException {
        if (Files.isDirectory(DEFAULT_EXPORT_FOLDER_PATH) == false) {
            try {
                Files.createDirectory(Paths.get("exported"));
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }
    }

}
