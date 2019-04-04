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
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * A class to access MapGrid data stored as a json file on the hard disk.
 */
public class JsonStatisticsStorage implements StatisticsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStatisticsStorage.class);

    private static final String DEFAULT_BACKUP_PATH = ".backup.json";

    private Path filePath;

    private Path backupFilePath;

    public JsonStatisticsStorage(Path filePath) {
        this.filePath = filePath;
        this.backupFilePath = Paths.get(".backup", DEFAULT_BACKUP_PATH);
    }

    public Path getStatisticsFilePath() {
        return filePath;
    }

//    @Override
//    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException {
//        return readAddressBook(filePath);
//    }
//
//    /**
//     * Similar to {@link #readAddressBook()}.
//     *
//     * @param filePath location of the data. Cannot be null.
//     * @throws DataConversionException if the file is not in the correct format.
//     */
//    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException {
//        requireNonNull(filePath);
//
//        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
//                filePath, JsonSerializableAddressBook.class);
//        if (!jsonAddressBook.isPresent()) {
//            return Optional.empty();
//        }
//
//        try {
//            return Optional.of(jsonAddressBook.get().toModelType());
//        } catch (IllegalValueException ive) {
//            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
//            throw new DataConversionException(ive);
//        }
//    }

    @Override
    public void saveStatisticsData(PlayerStatistics statisticsData) throws IOException {
        saveStatisticsData(statisticsData, filePath);
    }

    /**
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveStatisticsData(PlayerStatistics statisticsData, Path filePath) throws IOException {
        System.out.println("Inside JsonStatisticsStorage saveStatisticsData");
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

        System.out.println("Saving to JsonFile in JsonStatisticsStorage");
    }

    /**
     * Saves the ReadOnlyAddressBook locally in a fixed temporary location.
     *
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
//    @Override
//    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
//        saveAddressBook(addressBook, backupFilePath);
//    }

}
