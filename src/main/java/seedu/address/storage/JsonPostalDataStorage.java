package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.PostalDataSet;


/**
 * A class to access Postal Data stored in the hard disk as a json file
 */
public class JsonPostalDataStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPostalDataStorage.class);
    private Path filePath;
    private Optional<JsonSerializablePostalData> jsonPostalData;

    public JsonPostalDataStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPostalDataFilePath() {
        return filePath;
    }

    public Optional<PostalDataSet> loadPostalData() throws DataConversionException {
        return loadPostalData(filePath);
    }

    /**
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<PostalDataSet> loadPostalData(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        jsonPostalData = JsonUtil.readJsonFile(
                filePath, JsonSerializablePostalData.class);
        if (!jsonPostalData.isPresent()) {
            return Optional.empty();
        } else {
            try {
                return Optional.of(jsonPostalData.get().toModelType());
            } catch (IllegalValueException ive) {
                logger.info("Illegal values found in postal data " + filePath + ": " + ive.getMessage());
                throw new DataConversionException(ive);
            }

        }

    }
}
