package seedu.address.model.util;

import java.util.Arrays;
import java.util.Collections;

import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static Card[] getSampleCards() {
        return new Card[] {
            new Card(Arrays.asList("Belgium", "Brussels"), Collections.singletonList("B")),
            new Card(Arrays.asList("Japan", "Tokyo"), Collections.singletonList("T")),
        };
    }

    public static LessonList getSampleBrainTrain() {
        LessonList lessonList = new LessonList();
        Lesson lesson = new Lesson("sampleData", Arrays.asList("Country", "Capital"),
            Collections.singletonList("Hint"));

        for (Card sampleCard: getSampleCards()) {
            lesson.addCard(sampleCard);
        }

        lessonList.addLesson(lesson);
        return lessonList;
    }
}
