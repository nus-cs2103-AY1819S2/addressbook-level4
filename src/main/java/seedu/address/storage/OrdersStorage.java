package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.RestOrRant;

/**
 * Represents a storage for orders from {@link RestOrRant}.
 */
public interface OrdersStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getOrdersFilePath();

    /**
     * Returns order data as part of a {@link ReadOnlyRestOrRant}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRestOrRant> readOrders() throws DataConversionException, IOException;

    /**
     * @see #getOrdersFilePath()
     */
    Optional<ReadOnlyRestOrRant> readOrders(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRestOrRant} to the storage.
     * @param restOrRant cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveOrders(ReadOnlyRestOrRant restOrRant) throws IOException;

    /**
     * @see #saveOrders(ReadOnlyRestOrRant)
     */
    void saveOrders(ReadOnlyRestOrRant restOrRant, Path filePath) throws IOException;

    void backupOrders(ReadOnlyRestOrRant restOrRant) throws IOException;

}
