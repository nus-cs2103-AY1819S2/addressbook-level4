package seedu.finance.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.finance.commons.exceptions.DataConversionException;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.ReadOnlyUserPrefs;
import seedu.finance.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends FinanceTrackerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getFinanceTrackerFilePath();

    @Override
    Optional<ReadOnlyFinanceTracker> readFinanceTracker() throws DataConversionException, IOException;

    @Override
    void saveFinanceTracker(ReadOnlyFinanceTracker financeTracker) throws IOException;

}
