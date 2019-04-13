package quickdocs.storage;

import java.io.IOException;
import java.util.Optional;

import quickdocs.commons.exceptions.DataConversionException;
import quickdocs.model.QuickDocs;
import quickdocs.model.ReadOnlyUserPrefs;
import quickdocs.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    Optional<QuickDocs> readQuickDocs() throws DataConversionException, IOException;

    void saveQuickDocs(QuickDocs quickDocs) throws IOException;
}
