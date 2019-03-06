package seedu.address.model.lesson;

import static seedu.address.model.lesson.Lesson.EXCEPTION_INVALID_INDEX;
import static seedu.address.testutil.LessonBuilder.DEFAULT_CORE_COUNT;
import static seedu.address.testutil.LessonBuilder.DEFAULT_FIELDS;
import static seedu.address.testutil.LessonBuilder.DEFAULT_NAME;
import static seedu.address.testutil.TypicalLessons.LESSON_ONE_OPT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.MissingCoreException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.LessonBuilder;

public class LessonTest {
    private static final String NAME_DEFAULT = "Test Lesson";

    private static final int CORE_COUNT_DEFAULT = 2;

    private static final List<String> FIELDS_DEFAULT = Arrays.asList("Country", "Capital");
    private static final List<String> FIELDS_OPTIONALS = Arrays.asList("Country", "Capital", "Hint", "Country Code");
    private static final List<String> CARD_STRINGS_DEFAULT = Arrays.asList("Japan", "Tokyo");
    private static final List<String> CARD_STRINGS_OPTIONALS = Arrays.asList("Japan", "Tokyo", "Starts with T", "JP");
    private static final Card CARD_JAPAN =
        new Card(Arrays.asList("Japan", "Tokyo"), Arrays.asList("Starts with T", "JP"));

    private final Lesson lesson = new Lesson(NAME_DEFAULT, CORE_COUNT_DEFAULT, FIELDS_DEFAULT);
    private final Lesson lessonOptional = new Lesson(NAME_DEFAULT, CORE_COUNT_DEFAULT, FIELDS_OPTIONALS);

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Lesson("", DEFAULT_CORE_COUNT, DEFAULT_FIELDS));

        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Lesson(null, DEFAULT_CORE_COUNT, DEFAULT_FIELDS));
    }

    @Test
    public void constructor_invalidCoreCount_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Lesson(DEFAULT_NAME, 0, DEFAULT_FIELDS));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Lesson(DEFAULT_NAME, 1, DEFAULT_FIELDS));
    }

    @Test
    public void constructor_invalidFields_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Lesson(DEFAULT_NAME, DEFAULT_CORE_COUNT, new ArrayList<>()));
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Lesson(DEFAULT_NAME, DEFAULT_CORE_COUNT, Arrays.asList("Country")));
    }

    @Test
    public void addCard_invalidIndex_throwsMissingCoreException() {
        Lesson lesson = new Lesson(DEFAULT_NAME, DEFAULT_CORE_COUNT, DEFAULT_FIELDS);
        Assert.assertThrows(MissingCoreException.class, MissingCoreException.generateMessage(0), () ->
                lesson.addCard(Arrays.asList("", "Tokyo")));
        Assert.assertThrows(MissingCoreException.class, MissingCoreException.generateMessage(1), () ->
                lesson.addCard(Arrays.asList("Japan", "")));
    }

    @Test
    public void addCard() throws MissingCoreException {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();
        assertEquals(lesson.getCards().size(), 1);
        lesson.addCard(List.of("China", "Beijing"));
        assertEquals(lesson.getCards().size(), 2);
    }

    @Test
    public void setQuestionAnswerIndices_invalidQuestionIndex_throwsIllegalArgumentException() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();

        Assert.assertThrows(IllegalArgumentException.class, "Question index: -1 out of bounds", () ->
                lesson.setQuestionAnswerIndices(-1, 1));
        Assert.assertThrows(IllegalArgumentException.class, "Question index: 2 out of bounds", () ->
                lesson.setQuestionAnswerIndices(2, 1));
    }

    @Test
    public void setQuestionAnswerIndices_invalidAnswerIndex_throwsIllegalArgumentException() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();
        Assert.assertThrows(IllegalArgumentException.class, "Answer index: -1 out of bounds", () ->
                lesson.setQuestionAnswerIndices(0, -1));
        Assert.assertThrows(IllegalArgumentException.class, "Answer index: 2 out of bounds", () ->
                lesson.setQuestionAnswerIndices(0, 2));
    }

    @Test
    public void setOptionalShown_invalidIndex() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();

        Assert.assertThrows(IllegalArgumentException.class, EXCEPTION_INVALID_INDEX, ()->
                lesson.setIsVisibleOptional(-1, true));

        Assert.assertThrows(IllegalArgumentException.class, EXCEPTION_INVALID_INDEX, () ->
                lesson.setIsVisibleOptional(100, true));
    }

    @Test
    public void getCoreCount() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();
        assertEquals(lesson.getCoreCount(), DEFAULT_CORE_COUNT);
    }

    @Test
    public void getCardFields() {
        Lesson lesson = new LessonBuilder(LESSON_ONE_OPT).build();
        assertEquals(lesson.getCoreHeaders(), DEFAULT_FIELDS);
    }

    @Test
    public void getCards() {
        Lesson lesson = new LessonBuilder().build();
        assertTrue(lesson.isInitialized());
        assertEquals(0, lesson.getCards().size());
    }
}
