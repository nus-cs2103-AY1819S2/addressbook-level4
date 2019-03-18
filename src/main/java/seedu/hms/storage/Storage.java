package seedu.hms.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.hms.commons.exceptions.DataConversionException;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.ReadOnlyUserPrefs;
import seedu.hms.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends HotelManagementSystemStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getHotelManagementSystemFilePath();

    @Override
    Optional<ReadOnlyHotelManagementSystem> readHotelManagementSystem() throws DataConversionException, IOException;

    @Override
    void saveHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem) throws IOException;

}
