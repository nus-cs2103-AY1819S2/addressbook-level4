package seedu.address.storage.moduleinfostorage;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.moduleinfo.ModuleInfoList;

/**
 * Manages storage of All the module information data in local storage.
 */
public class ModuleInfoManager implements ModuleInfoStorage {

    private static final Logger logger = LogsCenter.getLogger(ModuleInfoManager.class);
    private static final String inputStreamPath = "/json/AllModules.json";

    private ModuleInfoStorage moduleInfoStorage;

    public ModuleInfoManager() {
        super();
        this.moduleInfoStorage = new JsonModuleInfoStorage(inputStreamPath);
    }

    @Override
    public Optional<ModuleInfoList> readModuleInfoFile()throws DataConversionException {
        return readModuleInfoFile(moduleInfoStorage.getModuleInfoInputStreamPath());
    }

    @Override
    public Optional<ModuleInfoList> readModuleInfoFile(String inputStreamPath) throws DataConversionException {
        logger.fine("Attempting to read data from file: " + inputStreamPath);
        return moduleInfoStorage.readModuleInfoFile(inputStreamPath);
    }

    @Override
    public String getModuleInfoInputStreamPath() {
        return inputStreamPath;
    }

}
