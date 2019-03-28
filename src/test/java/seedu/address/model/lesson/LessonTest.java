package seedu.address.model.lesson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalCards.CARD_DOG;
import static seedu.address.testutil.TypicalCards.CARD_DOG_CORE1;
import static seedu.address.testutil.TypicalCards.CARD_DOG_CORE2;
import static seedu.address.testutil.TypicalCards.CARD_EMPTY;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN_CORE1;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN_CORE2;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN_OPT1;
import static seedu.address.testutil.TypicalCards.CARD_MULTI;
import static seedu.address.testutil.TypicalLessons.LESSON_DEFAULT;
import static seedu.address.testutil.TypicalLessons.LESSON_TRUE_FALSE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.LessonBuilder;

public class LessonTest {
    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new LessonBuilder(LESSON_DEFAULT).withName("").build());

        Assert.assertThrows(IllegalArgumentException.class, () ->
                new LessonBuilder(LESSON_DEFAULT).withName(null).build());
    }


    @Test
    public void constructor_invalidCoreCount_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new LessonBuilder(LESSON_DEFAULT).withCoreHeaders("City").build());
    }

    @Test
    public void constructor_invalidFields_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Lesson(LESSON_DEFAULT.getName(), 1,
                        List.of("City", "Capital", "Country Code")));
    }

    @Test
    public void createLessons() {
        List<String> headers = new ArrayList<>();
        headers.addAll(LESSON_DEFAULT.getCoreHeaders());
        int noOfCoreHeaders = headers.size();
        headers.addAll(LESSON_DEFAULT.getOptionalHeaders());

        Lesson newLesson = new Lesson(LESSON_DEFAULT.getName(), noOfCoreHeaders, headers);
        assertEquals(newLesson.getName(), LESSON_DEFAULT.getName());
        assertEquals(newLesson.getCoreHeaderSize(), noOfCoreHeaders);
    }

    @Test
    public void equals() {
        // Different type of object -> return false
        assertFalse(LESSON_DEFAULT.equals(new Object()));

        // Same object -> returns true
        assertTrue(LESSON_DEFAULT.equals(LESSON_DEFAULT));

        // Different object -> returns false
        // newLesson is LESSON_TRUE_FALSE without LESSON_TRUE_FALSE's optional headers
        Lesson newLesson = new Lesson(LESSON_TRUE_FALSE.getName(),
                LESSON_TRUE_FALSE.getCoreHeaderSize(), LESSON_TRUE_FALSE.getCoreHeaders());
        assertFalse(LESSON_TRUE_FALSE.equals(newLesson));

        // Same cores and optionals -> returns true
        newLesson = new LessonBuilder(LESSON_DEFAULT).build();
        newLesson.setName("Different name");
        assertTrue(LESSON_DEFAULT.equals(newLesson)); // Should be considered equivalent
    }

    @Test
    public void setQuestionAnswerIndices() {
        Lesson newLesson = new LessonBuilder(LESSON_DEFAULT).build();

        newLesson.setQuestionAnswerIndices(0, 1);

        Assert.assertThrows(IllegalArgumentException.class, () ->
                newLesson.setQuestionAnswerIndices(-1, 1));

        Assert.assertThrows(IllegalArgumentException.class, () ->
                newLesson.setQuestionAnswerIndices(2, 1));

        Assert.assertThrows(IllegalArgumentException.class, () ->
                newLesson.setQuestionAnswerIndices(0, -1));

        Assert.assertThrows(IllegalArgumentException.class, () ->
                newLesson.setQuestionAnswerIndices(0, 2));

        newLesson.setAnswerCoreIndex(0);

        Assert.assertThrows(IllegalArgumentException.class, () ->
                newLesson.setQuestionCoreIndex(-1));

        Assert.assertThrows(IllegalArgumentException.class, () ->
                newLesson.setQuestionCoreIndex(2));


        newLesson.setAnswerCoreIndex(1);

        Assert.assertThrows(IllegalArgumentException.class, () ->
                newLesson.setAnswerCoreIndex(-1));

        Assert.assertThrows(IllegalArgumentException.class, () ->
                newLesson.setAnswerCoreIndex(2));
    }

    @Test
    public void setAndGetIsVisibleOptionals() {
        assertEquals(LESSON_DEFAULT.getCoreHeaderSize(), LessonBuilder.DEFAULT_CORE_HEADERS.size());

        Lesson newLesson = new LessonBuilder(LESSON_DEFAULT).build();
        boolean[] isVisibleOptionals = newLesson.getIsVisibleOptionals();
        assertEquals(newLesson.getIsVisibleOptionals(), isVisibleOptionals);

        boolean isFirstOptionalShown = newLesson.getIsVisibleOptional(0);
        // Not shown by default
        assertEquals(false, isFirstOptionalShown);

        Assert.assertThrows(IllegalArgumentException.class, () ->
                newLesson.setIsVisibleOptional(-1, true));

        newLesson.setIsVisibleOptional(0, true);
        isFirstOptionalShown = newLesson.getIsVisibleOptional(0);
        assertEquals(true, isFirstOptionalShown);
    }

    @Test
    public void setOptionalHeadersToNull() {
        Lesson newLesson = new LessonBuilder(LESSON_DEFAULT).build();
        // attempt to set optional headers to null -> nothing happens
        newLesson.setOptionalHeaders(null);
        assertEquals(newLesson.getOptionalHeaders().size(), 0);
    }

    @Test
    public void addCards() {
        Lesson newLesson = new LessonBuilder(LESSON_DEFAULT)
                .withCards(new CardBuilder().build(), new CardBuilder(CARD_DOG).build())
                .build();

        Assert.assertThrows(IllegalArgumentException.class, () ->
                LESSON_DEFAULT.addCard(CARD_MULTI));

        Assert.assertThrows(IllegalArgumentException.class, () ->
                LESSON_DEFAULT.addCard(CARD_EMPTY));

        newLesson.addCard(List.of(CARD_JAPAN_CORE1, CARD_JAPAN_CORE2, CARD_JAPAN_OPT1));

        newLesson = new LessonBuilder(LESSON_TRUE_FALSE).build();
        newLesson.addCard(List.of(CARD_DOG_CORE1, CARD_DOG_CORE2));
    }


    @Test
    public void deleteCards() {
        Lesson newLesson = new LessonBuilder(LESSON_DEFAULT)
                .withCards(new CardBuilder().build(), new CardBuilder(CARD_DOG).build())
                .build();
        assertEquals(newLesson.getCardCount(), 2);
        newLesson.deleteCard(0);
        assertEquals(newLesson.getCardCount(), 1);
    }

    @Test
    public void cardGetters() {
        Lesson newLesson = new Lesson("Sample lesson", LESSON_DEFAULT.getCoreHeaders(),
                LESSON_DEFAULT.getOptionalHeaders());

        // Should not have cards yet
        assertEquals(newLesson.hasCards(), false);
        assertEquals(newLesson.getCardCount(), 0);
        newLesson.addCard(CARD_JAPAN);

        // Should now have 1 card
        assertEquals(newLesson.hasCards(), true);
        assertEquals(newLesson.getCardCount(), 1);
        assertEquals(newLesson.getCards(), List.of(CARD_JAPAN));
    }

    @Test
    public void cardToString() {
        Lesson newLesson = new LessonBuilder(LESSON_DEFAULT).build();
        // since both lessons are identical, their string representation should be the same
        assertEquals(newLesson.toString(), LESSON_DEFAULT.toString());
    }
}
