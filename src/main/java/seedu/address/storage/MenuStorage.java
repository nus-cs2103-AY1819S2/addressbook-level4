package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.menu.Menu;
import seedu.address.model.menu.ReadOnlyMenu;

/**
 * Represents a storage for {@link Menu}.
 */
public interface MenuStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMenuFilePath();

    /**
     * Returns RestOrRant data as a {@link ReadOnlyMenu}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMenu> readMenu() throws DataConversionException, IOException;

    /**
     * @see #getMenuFilePath()
     */
    Optional<ReadOnlyMenu> readMenu(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMenu} to the storage.
     *
     * @param menu cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMenu(ReadOnlyMenu menu) throws IOException;

    /**
     * @see #saveMenu(ReadOnlyMenu)
     */
    void saveMenu(ReadOnlyMenu menu, Path filePath) throws IOException;

    void backupMenu(ReadOnlyMenu menu) throws IOException;

}
