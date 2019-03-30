package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.UserInfo;

/**
 * Manages storage of user info in local storage
 */
public class UserInfoStorageManager implements UserInfoStorage {

    private static final Logger logger = LogsCenter.getLogger(UserInfoStorage.class);
    private Path filePath = Paths.get("src", "main", "resources", "userinfo.json");

    public UserInfoStorageManager(Path filePath) {
        this.filePath = filePath;
    }

    public UserInfoStorageManager() {
    }

    @Override
    public Path getUserInfoFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserInfo> readUserInfoFile(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonAdaptedUserInfo> jsonUserInfo = JsonUtil.readJsonFile(filePath,
                JsonAdaptedUserInfo.class);
        if (!jsonUserInfo.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonUserInfo.get().toModelType());
        } catch (IllegalValueException e) {
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
            throw new DataConversionException(e);
        }
    }

    @Override
    public Optional<UserInfo> readUserInfoFile() throws DataConversionException {
        return readUserInfoFile(filePath);
    }

    @Override
    public void saveUserInfo(UserInfo userInfo) throws IOException {
        saveUserInfo(userInfo, filePath);
    }

    @Override
    public void saveUserInfo(UserInfo userInfo, Path filePath) throws IOException {
        requireNonNull(userInfo);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonAdaptedUserInfo(userInfo), filePath);
    }
}
