package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.model.user.User;

/**
 * Represents a storage for {@link User}.
 */
public interface UserStorage {
    /**
     * Returns the folder path of the data file.
     */
    Path getLessonsFolderPath();

    /**
     * Sets the folder path of the data file.
     */
    void setLessonsFolderPath(Path folderPath);

    /**
     * Returns Lessons data as a {@link User}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<User> readLessons() throws IOException;

    /**
     * @see #getLessonsFolderPath()
     */
    Optional<User> readLessons(Path folderPath) throws IOException;

    /**
     * Saves the given {@link User} to the storage.
     * @param user cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUser(User user) throws IOException;

    /**
     * @see #saveUser(User, Path)
     */
    void saveUser(User user, Path filePath) throws IOException;
}
