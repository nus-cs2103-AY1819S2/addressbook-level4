package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyHealthWorkerBook;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage,
        RequestBookStorage, HealthWorkerBookStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getHealthWorkerBookFilePath();

    @Override
    Path getRequestBookFilePath();

    @Override
    Optional<ReadOnlyRequestBook> readRequestBook() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyHealthWorkerBook> readHealthWorkerBook() throws DataConversionException, IOException;

    @Override
    void saveRequestBook(ReadOnlyRequestBook requestBook) throws IOException;

    @Override
    void saveHealthWorkerBook(ReadOnlyHealthWorkerBook healthWorkerBook) throws IOException;

}
