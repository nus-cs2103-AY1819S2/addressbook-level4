package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;

/**
 * Represents a storage for {@link BookShelf}.
 */
public interface BookShelfStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBookShelfFilePath();

    /**
     * Returns BookShelf data as a {@link ReadOnlyBookShelf}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBookShelf> readBookShelf() throws DataConversionException, IOException;

    /**
     * @see #getBookShelfFilePath()
     */
    Optional<ReadOnlyBookShelf> readBookShelf(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBookShelf} to the storage.
     * @param bookShelf cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBookShelf(ReadOnlyBookShelf bookShelf) throws IOException;

    /**
     * @see #saveBookShelf(ReadOnlyBookShelf)
     */
    void saveBookShelf(ReadOnlyBookShelf bookShelf, Path filePath) throws IOException;

    void backupBookShelf(ReadOnlyBookShelf bookShelf) throws IOException;

}
