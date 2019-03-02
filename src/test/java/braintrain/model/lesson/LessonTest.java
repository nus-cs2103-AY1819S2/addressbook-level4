package braintrain.model.lesson;

import static braintrain.testutil.LessonBuilder.DEFAULT_CORE_COUNT;
import static braintrain.testutil.LessonBuilder.DEFAULT_FIELDS;
import static braintrain.testutil.LessonBuilder.DEFAULT_NAME;
import static braintrain.testutil.TypicalLessons.LESSON_ONE_OPT;
import static braintrain.testutil.TypicalLessons.LESSON_TWO_OPT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import braintrain.model.card.exceptions.MissingCoreException;
import braintrain.testutil.Assert;
import braintrain.testutil.LessonBuilder;

public class LessonTest {
    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new Lesson("", DEFAULT_CORE_COUNT, DEFAULT_FIELDS);
        });
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new Lesson(null, DEFAULT_CORE_COUNT, DEFAULT_FIELDS);
        });
    }

    @Test
    public void constructor_invalidCoreCount_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new Lesson(DEFAULT_NAME, 0, DEFAULT_FIELDS);
        });
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new Lesson(DEFAULT_NAME, 1, DEFAULT_FIELDS);
        });
    }

    @Test
    public void constructor_invalidFields_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new Lesson(DEFAULT_NAME, DEFAULT_CORE_COUNT, new ArrayList<>());
        });
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new Lesson(DEFAULT_NAME, DEFAULT_CORE_COUNT, Arrays.asList("Country"));
        });
    }

    @Test
    public void addCard_invalidList_throwsIllegalArgumentException() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();
        List<String> invalidList = Arrays.asList("Japan", "Tokyo", "Starts with T", "JP");
        StringBuilder sb = new StringBuilder();
        invalidList.forEach(s -> {
            sb.append(s + ",");
        });
        Assert.assertThrows(IllegalArgumentException.class,
            "Line: " + sb.toString() + " does not match lesson format", () -> {
                lesson.addCard(invalidList);
            });
    }

    @Test
    public void addCard_invalidIndex_throwsMissingCoreException() {
        Lesson lesson = new Lesson(DEFAULT_NAME, DEFAULT_CORE_COUNT, DEFAULT_FIELDS);
        Assert.assertThrows(MissingCoreException.class, MissingCoreException.generateMessage(0), () -> {
            lesson.addCard(Arrays.asList("", "Tokyo"));
        });
        Assert.assertThrows(MissingCoreException.class, MissingCoreException.generateMessage(1), () -> {
            lesson.addCard(Arrays.asList("Japan", ""));
        });
    }

    @Test
    public void addCard() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();
        assertTrue(lesson.addCard(Arrays.asList("China", "Beijing")));
    }

    @Test
    public void setQuestionAnswerIndices_invalidQuestionIndex_throwsIllegalArgumentException() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();;
        Assert.assertThrows(IllegalArgumentException.class, "Question index: -1 out of bounds", () -> {
            lesson.setQuestionAnswerIndices(-1, 1);
        });
        Assert.assertThrows(IllegalArgumentException.class, "Question index: 2 out of bounds", () -> {
            lesson.setQuestionAnswerIndices(2, 1);
        });
    }

    @Test
    public void setQuestionAnswerIndices_invalidAnswerIndex_throwsIllegalArgumentException() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();;
        Assert.assertThrows(IllegalArgumentException.class, "Answer index: -1 out of bounds", () -> {
            lesson.setQuestionAnswerIndices(0, -1);
        });
        Assert.assertThrows(IllegalArgumentException.class, "Answer index: 2 out of bounds", () -> {
            lesson.setQuestionAnswerIndices(0, 2);
        });
    }

    @Test
    public void setQuestionAnswerIndices() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();;
        assertEquals(lesson.getQuestionIndex(), 0);
        assertEquals(lesson.getAnswerIndex(), 0);
        lesson.setQuestionAnswerIndices(1, 1);
        assertEquals(lesson.getQuestionIndex(), 1);
        assertEquals(lesson.getAnswerIndex(), 1);

    }

    @Test
    public void setOptionalShown_invalidIndex() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();;

        assertFalse(lesson.setOptionalShown(-1, true));
        assertFalse(lesson.setOptionalShown(2, true));
    }

    @Test
    public void getCoreCount() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();;
        assertEquals(lesson.getCoreCount(), DEFAULT_CORE_COUNT);
    }

    @Test
    public void getCardFields() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();;
        assertEquals(lesson.getCardFields(), DEFAULT_FIELDS);
    }

    @Test
    public void getCards() {
        Lesson lesson = new LessonBuilder().build();
        assertTrue(lesson.isInitalized());
        assertTrue(lesson.getCards().size() == 0);
    }
}
