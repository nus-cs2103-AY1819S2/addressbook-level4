package seedu.address.storage;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.model.lesson.LessonList;

/**
 * Represents a storage for {@link LessonList}.
 */
public interface LessonsStorage {
    /**
     * Returns the folder path of the data file.
     */
    Path getLessonsFolderPath();

    /**
     * Sets the folder path of the data file.
     */
    void setLessonsFolderPath(Path folderPath);

    /**
     * Returns LessonList data as a {@link LessonList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     */
    Optional<LessonList> readLessons();

    /**
     * @see #getLessonsFolderPath()
     */
    Optional<LessonList> readLessons(Path folderPath);

    /**
     * Saves the given {@link LessonList} to the storage.
     * @param lessonList cannot be null.
     * @return Number of lessonList successfully saved.
     */
    int saveLessons(LessonList lessonList);

    /**
     * @see #saveLessons(LessonList)
     */
    int saveLessons(LessonList lessonList, Path filePath);
}
