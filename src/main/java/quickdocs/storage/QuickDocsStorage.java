package quickdocs.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import quickdocs.commons.exceptions.DataConversionException;
import quickdocs.model.QuickDocs;

/**
 * Represents a storage for {@link QuickDocs}.
 */
public interface QuickDocsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFilePath();

    /**
     * Returns {@code QuickDocs} data as a {@link QuickDocs}.
     *
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<QuickDocs> readQuickDocs() throws DataConversionException, IOException;

    /**
     * Saves the given {@link QuickDocs} to the storage.
     * @param quickDocs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveQuickDocs(QuickDocs quickDocs) throws IOException;
}
