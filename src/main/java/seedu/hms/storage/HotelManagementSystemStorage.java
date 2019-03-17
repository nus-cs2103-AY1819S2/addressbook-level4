package seedu.hms.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.hms.commons.exceptions.DataConversionException;
import seedu.hms.model.ReadOnlyHotelManagementSystem;

/**
 * Represents a storage for {@link seedu.hms.model.HotelManagementSystem}.
 */
public interface HotelManagementSystemStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getHotelManagementSystemFilePath();

    /**
     * Returns HotelManagementSystem data as a {@link ReadOnlyHotelManagementSystem}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyHotelManagementSystem> readHotelManagementSystem() throws DataConversionException, IOException;

    /**
     * @see #getHotelManagementSystemFilePath()
     */
    Optional<ReadOnlyHotelManagementSystem> readHotelManagementSystem(Path filePath) throws DataConversionException,
        IOException;

    /**
     * Saves the given {@link ReadOnlyHotelManagementSystem} to the storage.
     *
     * @param hotelManagementSystem cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem) throws IOException;

    /**
     * @see #saveHotelManagementSystem(ReadOnlyHotelManagementSystem)
     */
    void saveHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem, Path filePath)
        throws IOException;

}
