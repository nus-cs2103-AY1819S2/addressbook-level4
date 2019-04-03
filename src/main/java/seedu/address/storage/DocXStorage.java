package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DocX;
import seedu.address.model.ReadOnlyDocX;

/**
 * Represents a storage for {@link DocX}.
 */
public interface DocXStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDocXFilePath();

    /**
     * Returns DocX data as a {@link ReadOnlyDocX}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDocX> readDocX() throws DataConversionException, IOException;

    /**
     * @see #getDocXFilePath()
     */
    Optional<ReadOnlyDocX> readDocX(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDocX} to the storage.
     * @param docX cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDocX(ReadOnlyDocX docX) throws IOException;

    /**
     * @see #saveDocX(ReadOnlyDocX)
     */
    void saveDocX(ReadOnlyDocX docX, Path filePath) throws IOException;

}
