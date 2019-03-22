package seedu.travel.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.travel.commons.exceptions.DataConversionException;
import seedu.travel.model.ReadOnlyTravelBuddy;
import seedu.travel.model.TravelBuddy;

/**
 * Represents a storage for {@link TravelBuddy}.
 */
public interface TravelBuddyStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTravelBuddyFilePath();

    /**
     * Returns TravelBuddy data as a {@link ReadOnlyTravelBuddy}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTravelBuddy> readTravelBuddy() throws DataConversionException, IOException;

    /**
     * @see #getTravelBuddyFilePath()
     */
    Optional<ReadOnlyTravelBuddy> readTravelBuddy(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTravelBuddy} to the storage.
     * @param travelBuddy cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTravelBuddy(ReadOnlyTravelBuddy travelBuddy) throws IOException;

    /**
     * @see #saveTravelBuddy(ReadOnlyTravelBuddy)
     */
    void saveTravelBuddy(ReadOnlyTravelBuddy travelBuddy, Path filePath) throws IOException;

    void backupTravelBuddy(ReadOnlyTravelBuddy travelBuddy) throws IOException;
}
