package braintrain.storage;

import java.io.IOException;
import java.util.Optional;

import braintrain.commons.exceptions.DataConversionException;
import braintrain.model.ReadOnlyUserPrefs;
import braintrain.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

}
