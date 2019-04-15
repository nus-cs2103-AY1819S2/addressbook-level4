package seedu.address.model.modelmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.UserPrefs.DARK_THEME;
import static seedu.address.model.UserPrefs.LIGHT_THEME;

import java.time.Instant;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;
import seedu.address.testutil.TypicalCards;
import seedu.address.testutil.TypicalLessonList;

public class ManagementModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Lesson lesson = TypicalLessonList.LESSON_DEFAULT;
    private Card card = TypicalCards.CARD_CAT;
    private ManagementModelManager modelManager = new ManagementModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new LessonList().getLessons(), modelManager.getLessons());
        assertEquals(new User(), modelManager.getUser());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void changeTheme_changeToDark_success() {
        modelManager.changeTheme();
        assertEquals(DARK_THEME, modelManager.getTheme());
    }

    @Test
    public void changeTheme_changeTwiceToLight_success() {
        modelManager.changeTheme();
        modelManager.changeTheme();
        assertEquals(LIGHT_THEME, modelManager.getTheme());
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();
        LessonList lessonList = new LessonList();
        User user = new User();

        // same values -> returns true
        modelManager = new ManagementModelManager(userPrefs, lessonList, user);
        ManagementModelManager modelManagerCopy = new ManagementModelManager(userPrefs, lessonList, user);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotNull(modelManager);

        // different types -> returns false
        assertNotEquals(modelManager, 5);
    }

    // SRS testing
    @Test
    public void userTests() {
        /*
         * Note: This is an integration test that verifies the ManagementModel is properly wired to the
         * {@link User} class.
         * More extensive testing of User functionality is done in {@link User} class.
         *
         * TODO
         */
        CardSrsData testCardSrsData = new CardSrsData(1, 1, 1,
                Instant.now(), false);

        User testUser = modelManager.getUser();
        assertEquals(new User(), testUser);
        modelManager.addCardSrsData(testCardSrsData);
        assertEquals(testCardSrsData, modelManager.getCardSrsData(testCardSrsData.getHashCode()));
        modelManager.setCardSrsData(testCardSrsData);
        assertEquals(testCardSrsData, modelManager.getCardSrsData(testCardSrsData.getHashCode()));
        modelManager.deleteCardSrsData(testCardSrsData);
        assertEquals(0, testUser.getCards().size());

    }

    // Integration tests which tests {@link ManagementModel}, {@link LessonList} and {@link Lesson}.
    @Test
    public void addLesson_validLesson_modelManagerUpdated() {
        // No lessons in modelManager by default
        assertEquals(0, modelManager.getLessons().size());
        assertFalse(modelManager.hasLessonWithName(lesson.getName()));

        modelManager.addLesson(lesson);
        // Added lesson -> lesson is in modelManager
        assertEquals(lesson, modelManager.getLesson(0));
        assertEquals(1, modelManager.getLessons().size());
        assertTrue(modelManager.hasLessonWithName(lesson.getName()));
    }

    @Test
    public void addLesson_invalidLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.addLesson(null);
    }

    @Test
    public void deleteLesson_validIndex_modelManagerUpdated() {
        modelManager.addLesson(lesson);
        int size = modelManager.getLessons().size();

        modelManager.deleteLesson(0);
        assertEquals(size - 1, modelManager.getLessons().size());
    }

    @Test
    public void deleteLesson_invalidIndex_throwsNullPointerException() {
        thrown.expect(IllegalArgumentException.class);
        modelManager.deleteLesson(-1);
    }

    @Test
    public void getLessonList_noLessonAddedYet_returnEmptyLessonList() {
        assertEquals(modelManager.getLessonList(), new LessonList());
    }

    @Test
    public void setLessonList_validLessonList_modelManagerUpdated() {
        LessonList testList = new LessonList();
        testList.addLesson(lesson);

        // Empty lessonList given no lessons added yet
        assertEquals(modelManager.getLessonList(), new LessonList());

        // Set lessonList to lessonList with lesson -> lessonList set
        modelManager.setLessonList(testList);
        assertEquals(testList, modelManager.getLessonList());
    }

    @Test
    public void setLessonList_invalidLessonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setLessonList(null);
    }

    @Test
    public void setLesson_validLesson_lessonSet() {
        modelManager.addLesson(lesson);
        assertEquals(modelManager.getLesson(0), lesson);

        Lesson newLesson = TypicalLessonList.LESSON_TRUE_FALSE;

        // set new lesson at index 0 -> get lesson at index 0 returns new lesson
        modelManager.setLesson(0, newLesson);
        assertNotEquals(modelManager.getLesson(0), lesson);
        assertEquals(modelManager.getLesson(0), newLesson);
    }

    @Test
    public void setLesson_invalidLesson_throwsNullPointerException() {
        modelManager.addLesson(lesson);
        assertEquals(modelManager.getLesson(0), lesson);

        // set invalid lesson at valid index -> throw exception
        thrown.expect(NullPointerException.class);
        modelManager.setLesson(0, null);
    }

    @Test
    public void getOpenedLesson_hasOpenedLesson_returnOpenedLesson() {
        modelManager.addLesson(lesson);
        modelManager.openLesson(0); // Open added lesson
        assertEquals(modelManager.getOpenedLesson(), lesson);
    }

    @Test
    public void getOpenedLesson_noOpenedLesson_returnNull() {
        assertNull(modelManager.getOpenedLesson());
    }

    @Test
    public void openThenCloseLesson_noOpenedLesson_returnNull() {
        modelManager.addLesson(lesson);
        modelManager.openLesson(0); // Open added lesson
        assertEquals(modelManager.getOpenedLesson(), lesson);

        modelManager.closeLesson();
        // openedLesson closed -> return null
        assertNull(modelManager.getOpenedLesson());
    }

    @Test
    public void openLesson_validLesson_thereIsLesson() {
        modelManager.addLesson(lesson);
        // no opened lesson -> return false
        assertFalse(modelManager.isThereOpenedLesson());

        modelManager.openLesson(0); // Open added lesson
        // opened lesson -> return true
        assertTrue(modelManager.isThereOpenedLesson());
    }

    @Test
    public void getOpenedCards_openedLesson_returnOpenedCards() {
        modelManager.addLesson(lesson);
        modelManager.openLesson(0); // Open added lesson
        // opened lesson -> return true
        assertNotNull(modelManager.getOpenedLessonCards());
    }

    @Test
    public void getOpenedCards_noOpenedLesson_returnNull() {
        thrown.expect(NullPointerException.class);
        assertNull(modelManager.getOpenedLessonCards());
    }

    @Test
    public void getOpenedLessonCoreHeaders_openedLesson_returnHeaders() {
        modelManager.addLesson(lesson);
        modelManager.openLesson(0); // Open added lesson
        // opened lesson -> return true
        assertNotNull(modelManager.getOpenedLessonCoreHeaders());
    }

    @Test
    public void getOpenedLessonCoreHeaders_noOpenedLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        assertNull(modelManager.getOpenedLessonCoreHeaders());
    }

    @Test
    public void getOpenedLessonOptHeaders_openedLesson_returnHeaders() {
        modelManager.addLesson(lesson);
        modelManager.openLesson(0); // Open added lesson
        // opened lesson -> return true
        assertNotNull(modelManager.getOpenedLessonOptionalHeaders());
    }

    @Test
    public void getOpenedLessonOptHeaders_noOpenedLesson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        assertNull(modelManager.getOpenedLessonOptionalHeaders());
    }

    @Test
    public void addCardToOpenedLesson_validCard_lessonUpdated() {
        modelManager.addLesson(lesson);
        modelManager.openLesson(0); // Open added lesson
        int size = modelManager.getOpenedLesson().getCardCount();

        // Add card to opened lesson -> opened lesson's card count + 1
        modelManager.addCardToOpenedLesson(card);
        assertEquals(size + 1, modelManager.getOpenedLesson().getCardCount());
    }

    @Test
    public void addCardToOpenedLesson_invalidCard_throwsNullPointerException() {
        modelManager.addLesson(lesson);
        modelManager.openLesson(0); // Open added lesson

        // Add invalid card to opened lesson -> null pointer exception thrown
        thrown.expect(NullPointerException.class);
        modelManager.addCardToOpenedLesson(null);
    }

    @Test
    public void deleteCardFromOpenedLesson_validIndex_lessonUpdated() {
        modelManager.addLesson(lesson);
        modelManager.openLesson(0); // Open added lesson
        int size = modelManager.getOpenedLesson().getCardCount();

        // Delete card from opened lesson -> opened lesson's card count - 1
        modelManager.deleteCardFromOpenedLesson(0);
        assertEquals(size - 1, modelManager.getOpenedLesson().getCardCount());
    }

    @Test
    public void deleteCardFromOpenedLesson_invalidIndex_throwsIllegalArgumentException() {
        modelManager.addLesson(lesson);
        modelManager.openLesson(0); // Open added lesson

        // Delete card from opened lesson at invalid index -> illegal argument exception thrown
        thrown.expect(IllegalArgumentException.class);
        modelManager.deleteCardFromOpenedLesson(-1);
    }

    @Test
    public void setTestForOpenedLesson_validIndices_setSuccessful() {
        modelManager.addLesson(lesson);
        modelManager.openLesson(0); // Open added lesson

        // Valid indices -> successful set
        modelManager.setOpenedLessonTestValues(1, 0);
        assertEquals(modelManager.getOpenedLesson().getQuestionCoreIndex(), 1);
        assertEquals(modelManager.getOpenedLesson().getAnswerCoreIndex(), 0);
    }
    @Test
    public void setTestForOpenedLesson_invalidIndex_throwsIllegalArgumentException() {
        modelManager.addLesson(lesson);
        modelManager.openLesson(0); // Open added lesson

        // Invalid index -> illegal argument exception thrown
        thrown.expect(IllegalArgumentException.class);
        modelManager.setOpenedLessonTestValues(5, 0);
    }
}
