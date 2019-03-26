package seedu.address.commons.util.csv;

import static java.util.Objects.requireNonNull;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVWriter;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.tag.Tag;

/**
 * A CsvWrapper class to complement the export command for MediTabs.
 * It uses Opencsv library from http://opencsv.sourceforge.net/index.html
 *
 */
public class CsvWrapper {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not export data to csv file: ";
    private static String[] defaultHeading = {"Name", "Batch Number", "Quantity", "Expiry Date", "Company", "Tags",
                                              "Notifications"};
    private static final String DEFAULT_EXPORT_FOLDER_NAME = "exported";
    private static final Path DEFAULT_EXPORT_FOLDER_PATH = Paths.get(DEFAULT_EXPORT_FOLDER_NAME);
    private String csvFileName;
    private Model model;
    private Path csvFilePath;

    public CsvWrapper(String csvFileName, Model model) {
        requireNonNull(csvFileName);
        requireNonNull(model);
        this.csvFileName = csvFileName;
        this.model = model;
    }

    /**
     * Exports the current list in the GUI to a csv file.
     * @throws CommandException If there is an error exporting the current list in the GUI to a csv file.
     */
    public void export() throws CommandException {
        try {
            List<Medicine> currentGuiList = model.getFilteredMedicineList();
            createCsvFile(csvFileName);
            writeDataToCsv(currentGuiList);
        } catch (CommandException ce) {
            doCleanUp();
            throw ce;
        }
    }

    /**
     * Creates a .csv file based on the input csv file name.
     * @param csvFileName The input csv file name.
     * @throws CommandException If there are errors creating the .csv file.
     */
    private void createCsvFile(String csvFileName) throws CommandException {
        createIfExportDirectoryMissing();
        try {
            csvFilePath = Files.createFile(Paths.get(DEFAULT_EXPORT_FOLDER_NAME, csvFileName + ".csv"));
        } catch (FileAlreadyExistsException fae) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + csvFileName + ".csv" + " already exists in \""
                    + DEFAULT_EXPORT_FOLDER_NAME + "\" directory.");
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    /**
     * Cleans up empty csv that is created in the exported directory if an error occurs.
     * If csvFilePath is null, return as there is no file to clean up.
     * @throws CommandException If there is an error cleaning up the empty csv file created.
     */
    private void doCleanUp() throws CommandException {
        if (csvFilePath == null) {
            return;
        }
        try {
            Files.deleteIfExists(csvFilePath);
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    /**
     * Writes data from current list displayed in GUI when export command is called to a csv file.
     * Note: For medicine containing more than one batch, the csv data will be compiled
     * such that each individual batches of each medicine has its own individual line in the csv file.
     * Medicines with no batches, in other words, just added or initialised without any batches linked to it,
     * it will be ignored and not written in the csv file. This is due to the fact that there is no useful information
     * to be compiled to the csv file for those medicines without any batches.
     * @param currentGuiList The current list displayed in the GUI when the export command is called.
     * @throws CommandException If there is an error exporting the current list in the GUI to a csv file.
     */
    private void writeDataToCsv(List currentGuiList) throws CommandException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFilePath.toString()))) {
            csvWriter.writeNext(defaultHeading);
            Iterator iterator = currentGuiList.listIterator();
            while (iterator.hasNext()) {
                Medicine current = (Medicine) iterator.next();
                if (isMedicineInitialised(current) == false) {
                    continue;
                }
                List<String[]> medicineDataStringArray = processMedicineData(current);
                csvWriter.writeAll(medicineDataStringArray);
            }
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    /**
     * Creates the export directory if export directory where exported data are stored.
     * @throws CommandException if there are errors creating the export directory.
     */
    private void createIfExportDirectoryMissing() throws CommandException {
        if (Files.isDirectory(DEFAULT_EXPORT_FOLDER_PATH) == false) {
            try {
                Files.createDirectory(DEFAULT_EXPORT_FOLDER_PATH);
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }
    }

    /**
     * Returns whether the input medicine has been initialised.
     * Initalised, in this case, is defined as whether it has any batches or it was just added with currently
     * no batches linked to it.
     * @param medicine The input medicine.
     * @return Whether the input medicine has been initialised.
     */
    private boolean isMedicineInitialised(Medicine medicine) {
        if (medicine.getBatches().size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Processes the input medicine data and returns a List of String Array
     * representation of the input medicine data for writing to csv file.
     * @param medicine The input medicine.
     * @return Returns a list of String Array containing the input medicine data for writing to csv file.
     */
    private List<String[]> processMedicineData(Medicine medicine) {
        List<String[]> result = new ArrayList<>();
        Collection<Batch> batches = medicine.getBatches().values();
        Iterator iterator = batches.iterator();
        while (iterator.hasNext()) {
            Batch currentBatch = (Batch) iterator.next();
            String[] currentData = buildStringArray(medicine, currentBatch);
            result.add(currentData);
        }
        return result;
    }

    /**
     * Builds a String Array containing detailed information of the medicine
     * which will be written to the csv file.
     * @param medicine The input medicine.
     * @param batch The input batch of the input medicine.
     * @return A String Array containing detailed information of the medicine.
     */
    private String[] buildStringArray(Medicine medicine, Batch batch) {
        final StringBuilder builder = new StringBuilder();
        String delimiter = "|";
        String[] result;
        builder.append(medicine.getName())
                .append(delimiter)
                .append(batch.getBatchNumber())
                .append(delimiter)
                .append(batch.getQuantity())
                .append(delimiter)
                .append(batch.getExpiry())
                .append(delimiter)
                .append(medicine.getCompany())
                .append(delimiter);
        Iterator iterator = medicine.getTags().iterator();
        while (iterator.hasNext()) {
            Tag current = (Tag) iterator.next();
            String formattedCurrentTagString = current.toStringUpperCase();
            builder.append(formattedCurrentTagString);
            if (iterator.hasNext()) {
                builder.append(' ');
            }
        }
        result = builder.toString().split("\\|");
        return result;
    }

    public static String[] getDefaultHeading() {
        return defaultHeading;
    }
}
