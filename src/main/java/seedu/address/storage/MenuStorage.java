package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.RestOrRant;

/**
 * Represents a storage for {@link RestOrRant}.
 */
public interface MenuStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMenuFilePath();

    /**
     * Returns RestOrRant data as a {@link ReadOnlyRestOrRant}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRestOrRant> readMenu() throws DataConversionException, IOException;

    /**
     * @see #getMenuFilePath()
     */
    Optional<ReadOnlyRestOrRant> readMenu(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRestOrRant} to the storage.
     * @param menu cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMenu(ReadOnlyRestOrRant menu) throws IOException;

    /**
     * @see #saveMenu(ReadOnlyRestOrRant)
     */
    void saveMenu(ReadOnlyRestOrRant menu, Path filePath) throws IOException;

    void backupMenu(ReadOnlyRestOrRant menu) throws IOException;

}
