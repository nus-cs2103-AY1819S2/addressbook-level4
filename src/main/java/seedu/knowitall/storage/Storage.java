package seedu.knowitall.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.knowitall.commons.exceptions.DataConversionException;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.model.ReadOnlyUserPrefs;
import seedu.knowitall.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage {

    String FILE_FORMAT = ".json";

    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    void readCardFolders(List<ReadOnlyCardFolder> readFolders) throws Exception;

    void saveCardFolder(ReadOnlyCardFolder cardFolder, int index) throws IOException;

    void saveCardFolders(List<ReadOnlyCardFolder> cardFolders, Path cardFolderFilesPath) throws IOException;
}
