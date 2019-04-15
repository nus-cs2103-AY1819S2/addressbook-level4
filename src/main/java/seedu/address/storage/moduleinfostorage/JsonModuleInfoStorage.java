package seedu.address.storage.moduleinfostorage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.JsonUtil.readJsonFileFromInputStream;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.moduleinfo.ModuleInfoList;

/**
 * A class to access Module info data stored as a json file on the hard disk.
 */
public class JsonModuleInfoStorage implements ModuleInfoStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonModuleInfoStorage.class);
    private String inputStreamPath;

    public JsonModuleInfoStorage(String inputStreamPath) {
        this.inputStreamPath = inputStreamPath;
    }

    public String getModuleInfoInputStreamPath() {
        return inputStreamPath;
    }

    @Override
    public Optional<ModuleInfoList> readModuleInfoFile() throws DataConversionException {
        return readModuleInfoFile(this.inputStreamPath);
    }

    /**
     * Similar to {@link #readModuleInfoFile()}.
     *
     * @param inputStreamPath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ModuleInfoList> readModuleInfoFile(String inputStreamPath) throws DataConversionException {
        requireNonNull(inputStreamPath);
        Optional<JsonSerializableModuleInfoList> moduleInfoListOptional =
                readJsonFileFromInputStream(inputStreamPath, JsonSerializableModuleInfoList.class);
        if (!moduleInfoListOptional.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(moduleInfoListOptional.get().toModelType());
        } catch (IllegalValueException e) {
            logger.info("Illegal values found in " + inputStreamPath + ": " + e.getMessage());
            throw new DataConversionException(e);
        }
    }

}
