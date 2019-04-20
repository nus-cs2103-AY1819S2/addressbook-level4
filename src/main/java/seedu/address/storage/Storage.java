package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.user.User;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage, LessonListStorage, UserStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Optional<LessonList> readLessonList();

    @Override
    int saveLessonList(LessonList lessonList);

    @Override
    Optional<User> readUser();

    @Override
    void saveUser(User user);

    void deleteLesson(String lessonName) throws IOException;

}
