package quickdocs.model.reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import static quickdocs.testutil.TypicalAppointments.APP_A;
import static quickdocs.testutil.TypicalAppointments.APP_B;
import static quickdocs.testutil.TypicalReminders.REM_A;
import static quickdocs.testutil.TypicalReminders.REM_D;
import static quickdocs.testutil.TypicalReminders.REM_E;
import static quickdocs.testutil.TypicalReminders.getTypicalReminders;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

/**
 * Contains unit tests for {@code ReminderManager}. Most methods are already tested by commands related
 * to {@code Reminder}s. Hence {@code ReminderManagerTest} will only test some methods.
 */
public class ReminderManagerTest {
    private ReminderManager remMan = new ReminderManager();
    private List<Reminder> reminders = getTypicalReminders();

    @Test
    public void getReminderFromAppointment() {
        Reminder appAReminder = new Reminder(
                APP_A.createTitle(), APP_A.getComment(), APP_A.getDate(), APP_A.getStart(), APP_A.getEnd());
        for (Reminder rem : reminders) {
            remMan.addReminder(rem);
        }
        remMan.addReminder(appAReminder);

        // reminder for given appointment found
        Optional<Reminder> expectedRem = remMan.getReminder(APP_A);
        assertEquals(appAReminder, expectedRem.get());

        // reminder for given appointment not found
        expectedRem = remMan.getReminder(APP_B);
        assertEquals(Optional.empty(), expectedRem);
    }

    @Test
    public void equals() {
        // add some sample reminders into the manager
        ReminderManager remManA = remMan;
        for (Reminder rem : reminders) {
            remManA.addReminder(rem);
        }

        // test equality of same object -> returns true
        assertEquals(remManA, remManA);

        // test equality of different ReminderManagers with same reminders added -> returns true
        ReminderManager remManACopy = new ReminderManager();
        for (Reminder rem : reminders) {
            remManACopy.addReminder(rem);
        }
        assertEquals(remManA, remManACopy);

        // test equality of different ReminderManagers with 0 reminders added -> returns true
        ReminderManager remManB = new ReminderManager();
        ReminderManager remManBCopy = new ReminderManager();
        assertEquals(remManB, remManBCopy);

        // test equality with null -> returns false
        assertNotEquals(remManA, null);

        // test equality of different types -> returns false
        assertNotEquals(remManA, REM_A);

        // test equality of different ReminderManagers which contains different reminders -> returns false
        remManB = new ReminderManager();
        remManB.addReminder(REM_D);
        remManB.addReminder(REM_E);
        assertNotEquals(remManA, remManB);

        // test equality of different ReminderManagers which contains 0 reminders -> returns false
        remManB = new ReminderManager();
        assertNotEquals(remManA, remManB);
    }
}
