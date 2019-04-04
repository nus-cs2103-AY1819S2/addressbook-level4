package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * Manages storage of MapGrid data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private StatisticsStorage statisticsStorage;

    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                            StatisticsStorage statisticsStorage) {
        super();
        this.statisticsStorage = statisticsStorage;
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ MapGrid methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Path getStatisticsFilePath() {
        return statisticsStorage.getStatisticsFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    @Override
    public void saveStatisticsData(PlayerStatistics statisticsData) throws IOException {
        System.out.println("Inside Storage Manager, Saving Stats Data");
        saveStatisticsData(statisticsData, statisticsStorage.getStatisticsFilePath());
    }


    public void saveStatisticsData(PlayerStatistics statisticsData, Path filePath) throws IOException {
        System.out.println("Inside Storage Manager, Saving Stats Data with FilePath");
        logger.fine("Attempting to save statistics to file: " + filePath);
        statisticsStorage.saveStatisticsData(statisticsData, filePath);
    }

    @Override
    public Optional<PlayerStatistics> readStatisticsData() throws DataConversionException, IOException {
        return readStatisticsData(statisticsStorage.getStatisticsFilePath());
    }

    @Override
    public Optional<PlayerStatistics> readStatisticsData(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return statisticsStorage.readStatisticsData(filePath);
    }

    /**
     * Saves the ReadOnlyAddressBook locally in a fixed temporary location.
     *
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        addressBookStorage.backupAddressBook(addressBook);
    }
}
