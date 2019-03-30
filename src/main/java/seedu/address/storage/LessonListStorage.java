package seedu.address.storage;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.model.lesson.LessonList;

/**
 * Represents a storage for {@link LessonList}.
 */
public interface LessonListStorage {
    /**
     * Returns the folder path of the data file.
     */
    Path getLessonListFolderPath();

    /**
     * Sets the folder path of the data file.
     */
    void setLessonListFolderPath(Path folderPath);

    /**
     * Returns LessonList data as a {@link LessonList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     */
    Optional<LessonList> readLessonList();

    /**
     * @see #getLessonListFolderPath()
     */
    Optional<LessonList> readLessonList(Path folderPath);

    /**
     * Saves the given {@link LessonList} to the storage.
     * @param lessonList cannot be null.
     * @return Number of lessonList successfully saved.
     */
    int saveLessonList(LessonList lessonList);

    /**
     * @see #saveLessonList(LessonList)
     */
    int saveLessonList(LessonList lessonList, Path filePath);
}
