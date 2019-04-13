package quickdocs.model;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import quickdocs.commons.core.GuiSettings;
import quickdocs.model.reminder.ReminderWithinDatesPredicate;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
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
    public void getCurrentWeekRemindersPredicate_success() {
        LocalDate start = LocalDate.now().with(previousOrSame(MONDAY));
        LocalDate end = LocalDate.now().with(nextOrSame(SUNDAY));
        ReminderWithinDatesPredicate actualPredicate = new ReminderWithinDatesPredicate(start, end);
        assertEquals(actualPredicate, modelManager.getCurrentWeekRemindersPredicate());
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();
        QuickDocs quickDocs = new QuickDocs();

        // same values -> returns true
        modelManager = new ModelManager(quickDocs, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(quickDocs, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));
    }
}
