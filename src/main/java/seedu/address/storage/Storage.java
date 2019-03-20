package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Lessons;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage, LessonsStorage, UserStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Optional<Lessons> readLessons() throws IOException;

    @Override
    int saveLessons(Lessons lessons) throws IOException;

}
