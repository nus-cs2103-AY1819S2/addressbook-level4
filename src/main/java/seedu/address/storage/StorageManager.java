package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * Manages storage of Player Statistics data in local storage.
 * @author bos10
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserPrefsStorage userPrefsStorage;
    private StatisticsStorage statisticsStorage;

    /**
     * Initializes the StorageManager object.
     * @param userPrefsStorage
     * @param statisticsStorage
     */
    public StorageManager(UserPrefsStorage userPrefsStorage,
                          StatisticsStorage statisticsStorage) {
        super();
        this.statisticsStorage = statisticsStorage;
        this.userPrefsStorage = userPrefsStorage;
        logger.info("Initialized StorageManager");
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Statistics Storage methods ==============================

    @Override
    public Path getStatisticsFilePath() {
        return statisticsStorage.getStatisticsFilePath();
    }

    @Override
    public void saveStatisticsData(PlayerStatistics statisticsData) throws IOException {
        saveStatisticsData(statisticsData, statisticsStorage.getStatisticsFilePath());
    }

    /**
     * Takes the statisticsData and store its JSON into specified filePath.
     *
     * @param statisticsData
     * @param filePath
     * @throws IOException
     */
    public void saveStatisticsData(PlayerStatistics statisticsData, Path filePath) throws IOException {
        logger.fine("Attempting to save statistics to file: " + filePath);
        statisticsStorage.saveStatisticsData(statisticsData, filePath);
    }

    @Override
    public Optional<PlayerStatistics> readStatisticsData() throws DataConversionException, IOException {
        return readStatisticsData(statisticsStorage.getStatisticsFilePath());
    }

    @Override
    public Optional<PlayerStatistics> readStatisticsData(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return statisticsStorage.readStatisticsData(filePath);
    }
}
