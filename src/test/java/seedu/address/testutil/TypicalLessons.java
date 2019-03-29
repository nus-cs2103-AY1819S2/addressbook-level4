package seedu.address.testutil;

import static seedu.address.testutil.TypicalCards.CARD_CAT;
import static seedu.address.testutil.TypicalCards.CARD_DOG;
import static seedu.address.testutil.TypicalCards.CARD_DOGCAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.lesson.Lesson;

/**
 * A utility class containing a list of {@link Lesson} objects used for testing.
 */
public class TypicalLessons {
    public static final Lesson LESSON_DEFAULT = new LessonBuilder().build();
    public static final Lesson LESSON_TRUE_FALSE = new LessonBuilder()
            .withName("True and False Trivia")
            .withCoreHeaders("Proposition", "Truth value")
            .withOptionalHeaders("")
            .withQuestionCoreIndex(0)
            .withAnswerCoreIndex(1)
            .withCards(CARD_CAT, CARD_DOG, CARD_DOGCAT)
            .build();
    private TypicalLessons() {} // Prevents instantiation

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(LESSON_DEFAULT, LESSON_TRUE_FALSE));
    }
}
