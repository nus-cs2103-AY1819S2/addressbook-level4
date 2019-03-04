package seedu.address.storage.moduleinfostorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.moduleinfo.ModuleInfoList;

/**
 * API of the Storage component
 */
public interface ModuleInfoStorage {

    /**
     * Returns the file path of the data file containing all module Information and listings.
     */
    Path getModuleInfoFilePath();

    /**
     * Returns an arraylist of all the available modules in nus
     * @param filePath
     * @return An ArrayList of moduleInfo of all available modules
     * @throws DataConversionException
     * @throws IOException
     */
    Optional<ModuleInfoList> readModuleInfoFile(Path filePath) throws DataConversionException;

    Optional<ModuleInfoList> readModuleInfoFile()throws DataConversionException;
}
