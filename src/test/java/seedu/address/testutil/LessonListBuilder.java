package seedu.address.testutil;

import java.util.List;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;

/**
 * A utility class to help with building {@link LessonList} objects.
 */
public class LessonListBuilder {
    private LessonList lessonList;

    /**
     * Builds an empty {@link LessonList} object.
     */
    public LessonListBuilder() {
        lessonList = new LessonList();
    }

    /**
     * Adds {@link Lesson} objects to the {@code LessonList}.
     *
     * @param lessons the {@link Lesson} objects to the {@code LessonList}.
     */
    public LessonListBuilder withLessons(List<Lesson> lessons) {
        for (Lesson lesson: lessons) {
            lessonList.addLesson(lesson);
        }

        return this;
    }

    /**
     * Builds and returns a {@link LessonList}.
     *
     * @return a {@link LessonList}
     */
    public LessonList build() {
        return lessonList;
    }
}
