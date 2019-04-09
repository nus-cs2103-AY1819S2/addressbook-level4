package seedu.address.model.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.lesson.LessonList;
import seedu.address.testutil.LessonBuilder;

public class SampleDataUtilTest {
    @Test
    public void getSampleBrainTrain() {
        LessonList lessonList = new LessonList();
        lessonList.addLesson(new LessonBuilder().withName("sampleData").build());
        assertEquals(lessonList, SampleDataUtil.getSampleBrainTrain());
    }
}
