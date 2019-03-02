package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends MenuStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    //  Path getRestOrRantFilePath(); // TODO: remove
    @Override
    Path getMenuFilePath();

    //  Optional<ReadOnlyRestOrRant> readRestOrRant() throws DataConversionException, IOException; // TODO: remove
    @Override
    Optional<ReadOnlyRestOrRant> readMenu() throws DataConversionException, IOException;

    //  void saveRestOrRant(ReadOnlyRestOrRant restOrRant) throws IOException; // TODO: remove
    @Override
    void saveMenu(ReadOnlyRestOrRant menu) throws IOException;

}
