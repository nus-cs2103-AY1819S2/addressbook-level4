package seedu.travel.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.travel.commons.exceptions.DataConversionException;
import seedu.travel.model.ReadOnlyTravelBuddy;
import seedu.travel.model.ReadOnlyUserPrefs;
import seedu.travel.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TravelBuddyStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getTravelBuddyFilePath();

    @Override
    Optional<ReadOnlyTravelBuddy> readTravelBuddy() throws DataConversionException, IOException;

    @Override
    void saveTravelBuddy(ReadOnlyTravelBuddy travelBuddy) throws IOException;

}
