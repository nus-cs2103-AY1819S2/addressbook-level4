package seedu.address.model.util;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.util.SampleLessons.SAMPLE_1_LESSON;

import org.junit.Test;

import seedu.address.model.lesson.LessonList;

public class SampleDataUtilTest {
    @Test
    public void getSampleBrainTrain() {
        LessonList lessonList = new LessonList();
        lessonList.addLesson(SAMPLE_1_LESSON);
        assertEquals(lessonList, SampleDataUtil.getSampleBrainTrain());
    }
}
