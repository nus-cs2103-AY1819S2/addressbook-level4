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
import seedu.address.model.QuickDocs;

/**
 * A class to access QuickDocs data stored as a json file on the hard disk.
 */
public class JsonQuickDocsStorage implements QuickDocsStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonQuickDocsStorage.class);
    private Path filePath = Paths.get("data" , "quickdocs.json");

    public Path getFilePath() {
        return filePath;
    }

    /**
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<QuickDocs> readQuickDocs() throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableQuickDocs> jsonQuickDocs = JsonUtil.readJsonFile(
                filePath, JsonSerializableQuickDocs.class);
        if (!jsonQuickDocs.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonQuickDocs.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * save QuickDocs data to disk
     */
    public void saveQuickDocs(QuickDocs quickDocs) throws IOException {
        requireNonNull(quickDocs);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableQuickDocs(quickDocs), filePath);
    }


}
