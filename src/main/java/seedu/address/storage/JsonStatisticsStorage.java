package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * A class to access MapGrid data stored as a json file on the hard disk.
 */
public class JsonStatisticsStorage implements StatisticsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStatisticsStorage.class);

    private static final String DEFAULT_BACKUP_PATH = ".backup.json";

    private Path filePath;

    public JsonStatisticsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getStatisticsFilePath() {
        return filePath;
    }

    @Override
    public Optional<PlayerStatistics> readStatisticsData() throws DataConversionException, IOException {
        return readStatisticsData(filePath);
    }

    /**
     * retrives the statistics data values frm json file at specified filepath.
     * @param filePath
     * @return
     * @throws DataConversionException
     * @throws IOException
     */
    public Optional<PlayerStatistics> readStatisticsData(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableStatistics> jsonStatisticsData = JsonUtil.readJsonFile(
                filePath, JsonSerializableStatistics.class);

        if (!(jsonStatisticsData.isPresent())) {
            return Optional.empty();
        }

        return Optional.of(jsonStatisticsData.get().toModelType());
    }

    @Override
    public void saveStatisticsData(PlayerStatistics statisticsData) throws IOException {
        saveStatisticsData(statisticsData, filePath);
    }

    /**
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveStatisticsData(PlayerStatistics statisticsData, Path filePath) throws IOException {
        //System.out.println("Inside JsonStatisticsStorage saveStatisticsData");
        requireNonNull(statisticsData);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);

        String hitCount = String.valueOf(statisticsData.getHitCount());
        String missCount = String.valueOf(statisticsData.getMissCount());
        String movesMade = String.valueOf(statisticsData.getMovesMade());
        String enemyShipsDestroyed = String.valueOf(statisticsData.getEnemyShipsDestroyed());
        String attacksMade = String.valueOf(statisticsData.getAttacksMade());

        //JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);

        JsonUtil.saveJsonFile(new JsonSerializableStatistics(hitCount, missCount, movesMade,
                                        enemyShipsDestroyed, attacksMade), filePath);

        //System.out.println("Saving to JsonFile in JsonStatisticsStorage");
    }
}
