package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * Represents a storage for PlayerStatistics Objects\
 * @author bos10
 */
public interface StatisticsStorage {
    /**
     * Gets the current specified filePath of where statistics data is stored.
     * @return
     */
    Path getStatisticsFilePath();

    /**
     * Calls the SaveStatisticsData with a specified filepath.
     * @param statisticsData
     * @throws IOException
     */
    void saveStatisticsData(PlayerStatistics statisticsData) throws IOException;

    /**
     * Saves the statistics data into the specified filepath.
     * @param statisticsData
     * @param filePath
     * @throws IOException
     */
    void saveStatisticsData(PlayerStatistics statisticsData, Path filePath) throws IOException;
    /**
     * Calls the readStatisticsData method with a specified filepath.
     */
    Optional<PlayerStatistics> readStatisticsData() throws DataConversionException, IOException;

    /**
     * Read the stored statistics data (if any) at the specified filePath.
     * @param filePath
     * @return
     * @throws DataConversionException
     * @throws IOException
     */
    Optional<PlayerStatistics> readStatisticsData(Path filePath) throws DataConversionException, IOException;
}
