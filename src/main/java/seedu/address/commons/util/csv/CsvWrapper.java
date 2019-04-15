package seedu.address.commons.util.csv;

import static java.util.Objects.requireNonNull;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVWriter;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.tag.Tag;

/**
 * A CsvWrapper class to complement the export command for MediTabs.
 * It uses Opencsv library from http://opencsv.sourceforge.net/index.html
 *
 */
public class CsvWrapper {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not export data to csv file: ";
    public static final String DEFAULT_EXPIRING_SOON_NOTIFICATION = "[EXPIRING SOON]";
    public static final String DEFAULT_EXPIRED_NOTIFICATION = "[EXPIRED]";
    public static final String DEFAULT_LOW_STOCK_NOTIFICATION = "[LOW STOCK]";
    // The reason for the limitation of maximum 255 characters for file name is due to the limitations
    // set by Windows (MAX_PATH limitation
    // in Windows https://docs.microsoft.com/en-us/windows/desktop/fileio/naming-a-file#maximum-path-length-limitation)
    // and also different file systems support up to different number of characters.
    // There is no universal way of solving this problem to
    // allow portability between different operating systems such as Windows, Linux and macOS due to this limitation,
    // hence based on the comparison charts and most common length limitation shown in the comparison chart in
    // https://en.wikipedia.org/wiki/Filename#Comparison_of_filename_limitations, the MAX_FILE_NAME_LENGTH is
    // set to 255 though it might not be a method to solve portability of file name length completely but it is
    // a general agreed limit by programmers when programming to set the file name to 255 characters.
    public static final int MAX_FILE_NAME_LENGTH = 255;
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
            List<Medicine> lowStockMedicinesList = model.getLowStockMedicinesList();
            createCsvFile(csvFileName);
            writeDataToCsv(currentGuiList, lowStockMedicinesList);
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
            if (csvFileName.length() > MAX_FILE_NAME_LENGTH) {
                throw new CommandException((FILE_OPS_ERROR_MESSAGE + "The file name is too long, try to reduce"
                        + " the specified file name length to be within 255 characters.\n"
                        + "Note: This is just a standardise file name length and a FileSystemException exception might"
                        + " still occur during file creation depending on your current"
                        + " operating system's file system."));
            }
            csvFilePath = Files.createFile(Paths.get(DEFAULT_EXPORT_FOLDER_NAME, csvFileName + ".csv"));
        } catch (FileAlreadyExistsException fae) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + csvFileName + ".csv" + " already exists in \""
                    + DEFAULT_EXPORT_FOLDER_NAME + "\" directory.");
        } catch (FileSystemException fse) {
            throw new CommandException((FILE_OPS_ERROR_MESSAGE + fse.getClass().getName() + ": " + fse.getReason()
                    + "One possible reason is the specified file name "
                    + "is too long for your current operating system's file system."));
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
     * @param lowQuantityMedicineList The list of low quantity medicines (Medicines with low stock)
     * @throws CommandException If there is an error exporting the current list in the GUI to a csv file.
     */
    private void writeDataToCsv(List currentGuiList, List lowQuantityMedicineList) throws CommandException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFilePath.toString()))) {
            csvWriter.writeNext(defaultHeading);
            Iterator iterator = currentGuiList.listIterator();
            while (iterator.hasNext()) {
                Medicine current = (Medicine) iterator.next();
                if (isMedicineInitialised(current) == false) {
                    continue;
                }
                List<String[]> medicineDataStringArray = processMedicineData(current, isMedicineLowQuantity(current,
                        lowQuantityMedicineList));
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
        if (Files.isDirectory(DEFAULT_EXPORT_FOLDER_PATH) == false && Files.exists(DEFAULT_EXPORT_FOLDER_PATH)) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + "an \"" + DEFAULT_EXPORT_FOLDER_NAME + "\" file without"
                    + " any file extension already exists and it is not a directory. "
                    + "Please remove it in order for the \"exported\" directory to be created.");
        }
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
     * Returns a list batch of the input medicine that are expiring soon.
     * Note: It returns an empty list of the input medicine does not have any batches that is expiring soon.
     * @param medicine The input medicine .
     * @return Returns a list batch of the input medicine that are expiring soon.
     * It returns an empty list of the input medicine does not have any batches that is expiring soon.
     */
    private List<Batch> getListOfBatchExpiringSoon(Medicine medicine) {
        return medicine.getFilteredBatch(model.getWarningPanelPredicateAccessor().getBatchExpiryPredicate());
    }

    /**
     * Returns true if the input medicine is in the input list of lowQuantityMedicineList else returns false.
     * @param medicine The input medicine.
     * @param lowQuantityMedicineList The list of medicines which are low in quantity/low in stock.
     * @return Returns true if the input medicine is in the input list of lowQuantityMedicineList else returns false.
     */
    private boolean isMedicineLowQuantity(Medicine medicine, List lowQuantityMedicineList) {
        return lowQuantityMedicineList.contains(medicine);
    }

    /**
     * Returns true if the input medicine batch is expired else returns false.
     * Note: A medicine batch is considered expired if the current date is greater than or equal to the medicine
     * batch expiry date.
     * @param batch The input medicine batch.
     * @return Returns true if the input medicine batch is expired else returns false.
     */
    private boolean isMedicineBatchExpired(Batch batch) {
        Expiry currentMedicineBatchExpiry = batch.getExpiry();
        Expiry currentDate;
        SimpleDateFormat currentDataAndTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateString = currentDataAndTimeFormat.format(new Date());
        currentDate = new Expiry(currentDateString);
        if (currentDate.compareTo(currentMedicineBatchExpiry) >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Processes the input medicine data and returns a List of String Array
     * representation of the input medicine data for writing to csv file.
     * @param medicine The input medicine.
     * @param isMedicineLowQuantity The input medicine low quantity status: It is true, if it is low stock else
     *                              it is false.
     * @return Returns a list of String Array containing the input medicine data for writing to csv file.
     */
    private List<String[]> processMedicineData(Medicine medicine, boolean isMedicineLowQuantity) {
        List<String[]> result = new ArrayList<>();
        Collection<Batch> batches = medicine.getBatches().values();
        Iterator iterator = batches.iterator();
        while (iterator.hasNext()) {
            Batch currentBatch = (Batch) iterator.next();
            String[] currentData = buildStringArray(medicine, currentBatch, isMedicineLowQuantity);
            result.add(currentData);
        }
        return result;
    }

    /**
     * Builds a String Array containing detailed information of the medicine
     * which will be written to the csv file.
     * @param medicine The input medicine.
     * @param batch The input batch of the input medicine.
     * @param isMedicineLowQuantity The input medicine low quantity status: It is true, if it is low stock else
     *                              it is false.
     * @return A String Array containing detailed information of the medicine.
     */
    private String[] buildStringArray(Medicine medicine, Batch batch, boolean isMedicineLowQuantity) {
        final StringBuilder builder = new StringBuilder();
        String delimiter = "|";
        List<Batch> listOfBatchesExpiringSoon = getListOfBatchExpiringSoon(medicine);
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
        builder.append(delimiter);
        if (isMedicineLowQuantity) {
            builder.append(DEFAULT_LOW_STOCK_NOTIFICATION);
            if (listOfBatchesExpiringSoon.contains(batch)) {
                builder.append(' ');
                if (isMedicineBatchExpired(batch)) {
                    builder.append(DEFAULT_EXPIRED_NOTIFICATION);
                } else {
                    builder.append(DEFAULT_EXPIRING_SOON_NOTIFICATION);
                }
            }
        } else {
            if (listOfBatchesExpiringSoon.contains(batch)) {
                if (isMedicineBatchExpired(batch)) {
                    builder.append(DEFAULT_EXPIRED_NOTIFICATION);
                } else {
                    builder.append(DEFAULT_EXPIRING_SOON_NOTIFICATION);
                }
            }
        }
        result = builder.toString().split("\\|");
        return result;
    }

    public static String[] getDefaultHeading() {
        return defaultHeading;
    }

    public static String getDefaultExpiringSoonNotification() {
        return DEFAULT_EXPIRING_SOON_NOTIFICATION;
    }

    public static String getDefaultLowStockNotification() {
        return DEFAULT_LOW_STOCK_NOTIFICATION;
    }

    public static String getDefaultExpiredNotification() {
        return DEFAULT_EXPIRED_NOTIFICATION;
    }
}
