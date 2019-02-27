package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage {
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;
    Path getcardFolderFilesPath();
    Optional<ReadOnlyCardFolder> readCardFolder() throws DataConversionException, IOException;
    void saveCardFolder(ReadOnlyCardFolder cardFolder) throws IOException;

}
