package seedu.address.model.modelmanager.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Lessons;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;

public class ManagementModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ManagementModelManager modelManager = new ManagementModelManager();

    private Lesson getTestLesson() {
        ArrayList<String> testFields = new ArrayList<>();
        testFields.add("test 1");
        testFields.add("test 2");
        Lesson lesson = new Lesson("test", 2, testFields);

        return lesson;
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Lessons().getLessons(), modelManager.getLessons());
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
    public void lessonsTests() {
        /*
         * Note: This is an integration test that verifies the ManagementModel is properly wired to the
         * {@link Lessons} class.
         * More extensive testing of Lessons functionality is done in {@link Lessons} class.
         */
        Lesson testLesson = getTestLesson();
        assertEquals(0, modelManager.getLessons().size());
        modelManager.addLesson(getTestLesson());
        assertEquals(testLesson, modelManager.getLesson(0));
        testLesson.setName("other name");
        modelManager.setLesson(0, testLesson);
        assertEquals(testLesson, modelManager.getLesson(0));
        modelManager.deleteLesson(0);
        assertEquals(0, modelManager.getLessons().size());
    }

    @Test
    public void userTests() {
        /*
         * Note: This is an integration test that verifies the ManagementModel is properly wired to the
         * {@link User} class.
         * More extensive testing of User functionality is done in {@link User} class.
         *
         * TODO
         */
        CardSrsData testCardSrsData = new CardSrsData(1, 1, 1, Instant.now());

        User testUser = modelManager.getUser();
        assertEquals(new User(), testUser);
        modelManager.addCardSrsData(testCardSrsData);
        assertEquals(testCardSrsData, modelManager.getCardSrsData(testCardSrsData.getHashCode()));
        modelManager.setCardSrsData(testCardSrsData);
        assertEquals(testCardSrsData, modelManager.getCardSrsData(testCardSrsData.getHashCode()));
        modelManager.deleteCardSrsData(testCardSrsData);
        assertEquals(0, testUser.getCards().size());

    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();
        Lessons lessons = new Lessons();
        User user = new User();

        // same values -> returns true
        modelManager = new ManagementModelManager(userPrefs, lessons, user);
        ManagementModelManager modelManagerCopy = new ManagementModelManager(userPrefs, lessons, user);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager == null);

        // different types -> returns false
        assertFalse(modelManager.equals(5));
    }
}
