package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;

import seedu.address.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonPostalData {

    private Path filePath;

    public JsonPostalData(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPostalDataFilePath() {
        return filePath;
    }

    public Optional<PostalData> readUserPrefs() throws DataConversionException {
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
     */
    public Optional<ReadOnlyFoodDiary> readFoodDiary(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableFoodDiary> jsonFoodDiary = JsonUtil.readJsonFile(
                filePath, JsonSerializableFoodDiary.class);
        if (!jsonFoodDiary.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFoodDiary.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }


}
