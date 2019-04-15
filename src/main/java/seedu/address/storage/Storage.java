package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.PostalDataSet;
import seedu.address.model.ReadOnlyFoodDiary;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends FoodDiaryStorage, UserPrefsStorage, PostalDataStorage {

    @Override
    Optional<PostalDataSet> getPostalData() throws DataConversionException;

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getFoodDiaryFilePath();

    @Override
    Optional<ReadOnlyFoodDiary> readFoodDiary() throws DataConversionException, IOException;

    @Override
    void saveFoodDiary(ReadOnlyFoodDiary foodDiary) throws IOException;

}
