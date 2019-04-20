package seedu.address.model.util;

import static seedu.address.model.util.SampleLessons.SAMPLE_1_LESSON;
import static seedu.address.model.util.SampleLessons.SAMPLE_2_LESSON;

import seedu.address.model.lesson.LessonList;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    /**
     * This is a static-methods-only (utility) class which should not be instantiated.
     * Note that this is not a singleton class given that not even a single instance is allowed.
     */
    private SampleDataUtil() { }

    public static LessonList getSampleBrainTrain() {
        LessonList lessonList = new LessonList();
        lessonList.addLesson(SAMPLE_1_LESSON);
        lessonList.addLesson(SAMPLE_2_LESSON);
        return lessonList;
    }
}
