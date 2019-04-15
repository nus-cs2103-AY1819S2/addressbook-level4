package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.PocketProject;
import seedu.address.model.ReadOnlyPocketProject;

/**
 * Represents a storage for {@link PocketProject}.
 */
public interface PocketProjectStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPocketProjectFilePath();

    /**
     * Returns PocketProject data as a {@link ReadOnlyPocketProject}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPocketProject> readPocketProject() throws DataConversionException, IOException;

    /**
     * @see #getPocketProjectFilePath()
     */
    Optional<ReadOnlyPocketProject> readPocketProject(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyPocketProject} to the storage.
     * @param pocketProject cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePocketProject(ReadOnlyPocketProject pocketProject) throws IOException;

    /**
     * @see #savePocketProject(ReadOnlyPocketProject)
     */
    void savePocketProject(ReadOnlyPocketProject pocketProject, Path filePath) throws IOException;

}
