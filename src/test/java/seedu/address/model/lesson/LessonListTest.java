package seedu.address.model.lesson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.card.Card;
import seedu.address.testutil.LessonListBuilder;
import seedu.address.testutil.TypicalCards;
import seedu.address.testutil.TypicalLessonList;

public class LessonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Lesson lesson = TypicalLessonList.LESSON_DEFAULT;
    private Lesson lessonToAdd = TypicalLessonList.LESSON_TRUE_FALSE;
    private LessonList lessonList = new LessonListBuilder()
            .withLessons(List.of(lesson)).build();
    private int defaultSize = lessonList.getLessons().size();

    @Test
    public void getLessons_lessonListSetup_getsLessonsList() {
        assertNotNull(lessonList.getLessons());
    }

    @Test
    public void getLesson_invalidIndex_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        lessonList.getLesson(-1);
    }

    @Test
    public void getLesson_validLesson_getsLesson() {
        assertEquals(lesson, lessonList.getLesson(0));
    }

    @Test
    public void addLesson_validLesson_hasLesson() {
        // lesson not yet added -> hasLessonWithName is false
        assertFalse(lessonList.hasLessonWithName(lessonToAdd.getName()));

        lessonList.addLesson(lessonToAdd);
        // lesson added -> lessonList.size() + 1
        assertEquals(defaultSize + 1, lessonList.getLessons().size());
        // lesson added -> hasLessonWithName is true
        assertTrue(lessonList.hasLessonWithName(lessonToAdd.getName()));
    }

    @Test
    public void addLesson_nullLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        lessonList.addLesson(null);
    }

    @Test
    public void setLesson_nullLesson_throwsNullPointerException() {
        lessonList.addLesson(lessonToAdd);

        thrown.expect(NullPointerException.class);
        lessonList.setLesson(0, null);
    }

    @Test
    public void setLesson_invalidIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        lessonList.setLesson(defaultSize + 1, lesson);
    }

    @Test
    public void setLesson_validInput_isEqual() {
        assertNotEquals(lessonToAdd, lessonList.getLesson(0));

        lessonList.setLesson(0, lessonToAdd);
        assertEquals(lessonToAdd, lessonList.getLesson(0));
    }

    @Test
    public void deleteLesson_invalidIndex_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        lessonList.deleteLesson(-1);
    }

    @Test
    public void deleteLesson_validIndex_deletesLesson() {
        // add lesson -> lesson added and size of ArrayList updated
        lessonList.addLesson(lessonToAdd);
        assertEquals(defaultSize + 1, lessonList.getLessons().size());

        // delete lesson -> lesson deleted and size of ArrayList updated
        lessonList.deleteLesson(0);
        assertEquals(defaultSize, lessonList.getLessons().size());

        // attempt to access deleted lesson -> illegal argument exception thrown
        thrown.expect(IllegalArgumentException.class);
        lessonList.getLesson(defaultSize);
    }

    @Test
    public void openLesson_validIndex_opensLesson() {
        // open valid lesson at valid index -> openedLesson = Lesson at index 0
        lessonList.openLesson(0);

        // get openedLesson which has been set to lesson at index 0 -> Lesson returned = lesson at index 0
        assertEquals(lessonList.getOpenedLesson(), lessonList.getLesson(0));

        // cards are the same
        assertEquals(lessonList.getOpenedLessonCards(), lessonList.getLesson(0).getCards());

        // core headers are the same
        assertEquals(lessonList.getOpenedLessonCoreHeaders(),
                lessonList.getLesson(0).getCoreHeaders());

        // optional headers are the same
        assertEquals(lessonList.getOpenedLessonOptionalHeaders(),
                lessonList.getLesson(0).getOptionalHeaders());
    }

    @Test
    public void openLesson_invalidIndex_throwsIllegalArgumentException() {
        // open non-existing lesson at invalid index -> IllegalArgumentException thrown
        thrown.expect(IllegalArgumentException.class);
        lessonList.openLesson(-1);
    }

    @Test
    public void noOpenLesson_noOpenedLesson_returnNull() {
        // get openedLesson which has not been set -> return null
        Assert.assertNull(lessonList.getOpenedLesson());
    }

    @Test
    public void openThenCloseLesson_noOpenedLesson_returnNull() {
        // open valid lesson at valid index -> openedLesson = Lesson at index 0
        lessonList.openLesson(0);

        // get openedLesson which has been set to valid lesson -> return valid lesson
        assertNotNull(lessonList.getOpenedLesson());

        // close lesson -> openedLesson set to null
        lessonList.closeLesson();

        // get openedLesson which has been set to null by closeLesson -> return null
        Assert.assertNull(lessonList.getOpenedLesson());
    }

    @Test
    public void openLesson_isThereOpenedLesson_returnTrue() {
        // open valid lesson at valid index -> openedLesson = Lesson at index 0
        lessonList.openLesson(0);

        // is there opened lesson -> return true
        assertTrue(lessonList.isThereOpenedLesson());
    }

    @Test
    public void closeLesson_isThereOpenedLesson_returnFalse() {
        // is there opened lesson -> return false
        assertFalse(lessonList.isThereOpenedLesson());

        // open valid lesson at valid index -> openedLesson = Lesson at index 0
        lessonList.openLesson(0);

        // close lesson -> openedLesson set to null
        lessonList.closeLesson();

        // is there opened lesson -> return false
        assertFalse(lessonList.isThereOpenedLesson());
    }

    @Test
    public void deleteOpenedLesson_noOpenedLesson_returnNull() {
        lessonList.addLesson(lessonToAdd);

        // open valid lesson at valid index -> openedLesson = Lesson at index 0
        lessonList.openLesson(0);
        assertNotNull(lessonList.getOpenedLesson());

        // delete lesson at valid index -> openedLesson = null
        lessonList.deleteLesson(0);

        // get openedLesson which has been set to null by deleteLesson which calls closeLesson -> return null
        Assert.assertNull(lessonList.getOpenedLesson());
    }

    // Card-related commands
    @Test
    public void openLesson_addCard_addSuccessful() {
        Card card = TypicalCards.CARD_CAT;
        lessonList.openLesson(0);
        int size = lessonList.getOpenedLessonCards().size();
        lessonList.addCardToOpenedLesson(card);
        assertEquals(size + 1, lessonList.getOpenedLessonCards().size());
    }

    @Test
    public void deleteCard() {
        lessonList.openLesson(0);
        int size = lessonList.getOpenedLessonCards().size();
        lessonList.deleteCardFromOpenedLesson(0);
        assertEquals(size - 1, lessonList.getOpenedLessonCards().size());
    }

    @Test
    public void deleteCard_invalidIndex_deleteUnsuccessful() {
        lessonList.openLesson(0);
        thrown.expect(IllegalArgumentException.class);
        lessonList.deleteCardFromOpenedLesson(500);
    }

    // Equality check
    @Test
    public void equals() {
        // same object -> returns true
        assertEquals(lessonList, lessonList);

        // same object type, different object -> return false
        LessonList diffLessonList = new LessonListBuilder().withLessons(List.of(lessonToAdd)).build();
        assertNotEquals(lessonList, diffLessonList);

        // different types -> returns false
        assertNotEquals(lessonList, new Object());
    }
}
