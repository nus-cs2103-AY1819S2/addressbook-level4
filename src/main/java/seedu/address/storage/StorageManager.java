package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.PostalDataSet;
import seedu.address.model.ReadOnlyFoodDiary;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of FoodDiary data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FoodDiaryStorage foodDiaryStorage;
    private UserPrefsStorage userPrefsStorage;
    private JsonPostalDataStorage jsonPostalDataStorage;


    public StorageManager(FoodDiaryStorage foodDiaryStorage, UserPrefsStorage userPrefsStorage,
                          JsonPostalDataStorage jsonPostalDataStorage) {
        super();
        this.foodDiaryStorage = foodDiaryStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.jsonPostalDataStorage = jsonPostalDataStorage;
    }
    // ================ PostalDataStorage methods ==============================

    @Override
    public Optional<PostalDataSet> getPostalData() throws DataConversionException {
        return jsonPostalDataStorage.loadPostalData();
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


    // ================ FoodDiary methods ==============================

    @Override
    public Path getFoodDiaryFilePath() {
        return foodDiaryStorage.getFoodDiaryFilePath();
    }

    @Override
    public Optional<ReadOnlyFoodDiary> readFoodDiary() throws DataConversionException, IOException {
        return readFoodDiary(foodDiaryStorage.getFoodDiaryFilePath());
    }

    @Override
    public Optional<ReadOnlyFoodDiary> readFoodDiary(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return foodDiaryStorage.readFoodDiary(filePath);
    }

    @Override
    public void saveFoodDiary(ReadOnlyFoodDiary foodDiary) throws IOException {
        saveFoodDiary(foodDiary, foodDiaryStorage.getFoodDiaryFilePath());
    }

    @Override
    public void saveFoodDiary(ReadOnlyFoodDiary foodDiary, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        foodDiaryStorage.saveFoodDiary(foodDiary, filePath);
    }

}
