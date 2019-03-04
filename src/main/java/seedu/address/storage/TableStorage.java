package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.ReadOnlyTables;
import seedu.address.model.RestOrRant;

/**s
 * Represents a storage for {@link RestOrRant}.
 */
public interface TableStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTableFilePath();

    /**
     * Returns Tables data as a {@link ReadOnlyTables}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTables> readTable() throws DataConversionException, IOException;

    /**
     * @see #getTableFilePath()
     */
    Optional<ReadOnlyTables> readTable(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRestOrRant} to the storage.
     * @param tables cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTable(ReadOnlyTables tables) throws IOException;

    /**
     * @see #saveTable(ReadOnlyTables)
     */
    void saveTable(ReadOnlyTables tables, Path filePath) throws IOException;

    void backupTables(ReadOnlyTables tables) throws IOException;

}
