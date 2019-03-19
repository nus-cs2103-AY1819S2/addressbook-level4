package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.model.user.User;

/**
 * Represents explicit and implicit user importing and exporting of {@link User}.
 */
public interface UserImportExport {
    /**
     * returns the default file Path where the updated user are placed at.
     */
    Path getImportExportFilePath();

    /**
     * Returns User data as a {@link User} from a given location.
     * Returns {@code Optional.empty()} if file is not found.
     * @throws IOException if problem encountered when reading from location
     */
    Optional<User> importUser(Path filePath) throws IOException;

    /**
     * Exports the given {@link User} to the given location.
     * @param user cannot be null.
     * @param filePath cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void exportUser(User user, Path filePath) throws IOException;

}
