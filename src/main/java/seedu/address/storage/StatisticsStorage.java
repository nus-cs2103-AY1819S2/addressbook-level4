package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.MapGrid;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * Represents a storage for {@link MapGrid}.
 */
public interface StatisticsStorage {

    Path getStatisticsFilePath();


    void saveStatisticsData(PlayerStatistics statisticsData) throws IOException;
    void saveStatisticsData(PlayerStatistics statisticsData, Path filePath) throws IOException;

    Optional<PlayerStatistics> readStatisticsData() throws DataConversionException, IOException;
    Optional<PlayerStatistics> readStatisticsData(Path filePath) throws DataConversionException, IOException;
}
