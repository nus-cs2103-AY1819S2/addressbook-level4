package seedu.hms.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.hms.commons.core.LogsCenter;
import seedu.hms.commons.exceptions.DataConversionException;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.ReadOnlyUserPrefs;
import seedu.hms.model.UserPrefs;

/**
 * Manages storage of HotelManagementSystem data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private HotelManagementSystemStorage hotelManagementSystemStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(HotelManagementSystemStorage hotelManagementSystemStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.hotelManagementSystemStorage = hotelManagementSystemStorage;
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


    // ================ HotelManagementSystem methods ==============================

    @Override
    public Path getHotelManagementSystemFilePath() {
        return hotelManagementSystemStorage.getHotelManagementSystemFilePath();
    }

    @Override
    public Optional<ReadOnlyHotelManagementSystem> readHotelManagementSystem() throws DataConversionException,
        IOException {
        return readHotelManagementSystem(hotelManagementSystemStorage.getHotelManagementSystemFilePath());
    }

    @Override
    public Optional<ReadOnlyHotelManagementSystem> readHotelManagementSystem(Path filePath)
        throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return hotelManagementSystemStorage.readHotelManagementSystem(filePath);
    }

    @Override
    public void saveHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem) throws IOException {
        saveHotelManagementSystem(hotelManagementSystem,
            hotelManagementSystemStorage.getHotelManagementSystemFilePath());
    }

    @Override
    public void saveHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem, Path filePath)
        throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        hotelManagementSystemStorage.saveHotelManagementSystem(hotelManagementSystem, filePath);
    }

}
