package seedu.address.storage;

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
    Path getUserFilePath();

    /**
     * Sets the folder path of the data file.
     */
    void setUserFilePath(Path folderPath);

    /**
     * Returns Lessons data as a {@link User}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     */
    Optional<User> readUser();

    /**
     * @see #getUserFilePath()
     */
    Optional<User> readUser(Path folderPath);

    /**
     * Saves the given {@link User} to the storage.
     * @param user cannot be null.
     */
    void saveUser(User user);

    /**
     * @see #saveUser(User, Path)
     */
    void saveUser(User user, Path filePath);
}
