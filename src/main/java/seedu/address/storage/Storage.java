package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * API of the Storage component
 * @author bos10
 */
public interface Storage extends UserPrefsStorage, StatisticsStorage {

    /**
     * Reads the specified userPref stored in the config file.
     * @return
     * @throws DataConversionException
     * @throws IOException
     */
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    /**
     * Saves the specified userPref stored into the config file.
     * @param userPrefs cannot be null.
     * @throws IOException
     */
    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    /**
     * Saves the current game statistics data into storage.
     * @param statisticsData
     * @throws IOException
     */
    @Override
    void saveStatisticsData(PlayerStatistics statisticsData) throws IOException;
}
