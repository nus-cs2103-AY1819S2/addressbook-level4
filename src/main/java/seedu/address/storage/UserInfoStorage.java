package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.UserInfo;

/**
 * Represents a storage for {@link UserInfo}.
 */
public interface UserInfoStorage {

    /**
     * Returns FilePath of UserInfo file
     */
    Path getUserInfoFilePath();

    /**
     * Returns an optional CourseList of some of the courses in nus
     * @param filePath
     * @return An optional courseList of moduleInfo of all available modules
     * @throws DataConversionException
     * @throws IOException
     */
    Optional<UserInfo> readUserInfoFile(Path filePath) throws DataConversionException;

    Optional<UserInfo> readUserInfoFile()throws DataConversionException;

    /**
     * Saves the given {@link UserInfo} at default filePath
     * @param userInfo
     * @throws IOException
     */
    void saveUserInfo(UserInfo userInfo) throws IOException;

    /**
     * Saves the given {@link UserInfo} to the storage at {@link Path}.
     * @param userInfo cannot be null.
     * @param filePath cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserInfo(UserInfo userInfo, Path filePath) throws IOException;
}
