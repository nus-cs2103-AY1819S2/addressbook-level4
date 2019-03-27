package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.GradTrak;
import seedu.address.model.ReadOnlyGradTrak;

/**
 * Represents a storage for {@link GradTrak}.
 */
public interface GradTrakStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGradTrakFilePath();

    /**
     * Returns GradTrak data as a {@link ReadOnlyGradTrak}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGradTrak> readGradTrak() throws DataConversionException, IOException;

    /**
     * @see #getGradTrakFilePath()
     */
    Optional<ReadOnlyGradTrak> readGradTrak(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGradTrak} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGradTrak(ReadOnlyGradTrak addressBook) throws IOException;

    /**
     * @see #saveGradTrak(ReadOnlyGradTrak)
     */
    void saveGradTrak(ReadOnlyGradTrak addressBook, Path filePath) throws IOException;

}
