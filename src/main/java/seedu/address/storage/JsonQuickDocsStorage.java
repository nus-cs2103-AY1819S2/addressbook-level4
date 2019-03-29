package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
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
    private Path filePath;

    public JsonQuickDocsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFilePath() {
        return filePath;
    }

    @Override
    public Optional<QuickDocs> readQuickDocs() throws DataConversionException {
        return readQuickDocs(filePath);
    }

    /**
     * Similar to {@link #readQuickDocs()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<QuickDocs> readQuickDocs(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableQuickDocs> jsonQuickDocs = JsonUtil.readJsonFile(
                filePath, JsonSerializableQuickDocs.class);
        if (!jsonQuickDocs.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonQuickDocs.get().toModelType());
        } catch (IllegalValueException | IllegalArgumentException ie) {
            logger.info("Illegal values found in " + filePath + ": " + ie.getMessage());
            throw new DataConversionException(ie);
        }
    }

    @Override
    public void saveQuickDocs(QuickDocs quickDocs) throws IOException {
        saveQuickDocs(quickDocs, filePath);
    }

    /**
     * Similar to {@link #saveQuickDocs(QuickDocs)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveQuickDocs(QuickDocs quickDocs, Path filePath) throws IOException {
        requireNonNull(quickDocs);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableQuickDocs(quickDocs), filePath);
    }
}
