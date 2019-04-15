package quickdocs.model.appointment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import static quickdocs.testutil.TypicalAppointments.APP_A;
import static quickdocs.testutil.TypicalPatients.ALICE;
import static quickdocs.testutil.TypicalPatients.BOB;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

/**
 * Contains unit tests for {@code Appointment}.
 */
public class AppointmentTest {
    @Test
    public void createTitle() {
        String actualTitle = APP_A.createTitle();
        String expectedTitle = "Appointment with " + ALICE.getName() + ", " + ALICE.getNric();
        assertEquals(actualTitle, expectedTitle);
    }

    @Test
    public void equals() {
        LocalDate date = APP_A.getDate();
        LocalTime start = APP_A.getStart();
        LocalTime end = APP_A.getEnd();
        String comment = APP_A.getComment();

        // test equality of same object -> returns true
        assertEquals(APP_A, APP_A);

        // test equality of different appointments with same values -> returns true
        Appointment appACopy = new Appointment(ALICE, date, start, end, comment);
        assertEquals(APP_A, appACopy);

        // test equality with null -> returns false
        assertNotEquals(APP_A, null);

        // test equality of different types -> returns false
        assertNotEquals(APP_A, date);

        // test equality of two different appointment object with different patient -> returns false
        Appointment appB = new Appointment(BOB, date, start, end, comment);
        assertNotEquals(APP_A, appB);

        // test equality of two different appointment object with different date -> returns false
        appB = new Appointment(ALICE, date.minusDays(1), start, end, comment);
        assertNotEquals(APP_A, appB);

        // test equality of two different appointment object with different start time -> returns false
        appB = new Appointment(ALICE, date, start.minusHours(1), end, comment);
        assertNotEquals(APP_A, appB);

        // test equality of two different appointment object with different end time -> returns false
        appB = new Appointment(ALICE, date, start, end.plusHours(1), comment);
        assertNotEquals(APP_A, appB);

        // test equality of two different appointment object with different comments -> returns false
        appB = new Appointment(ALICE, date, start, end, comment + "different comment");
        assertNotEquals(APP_A, appB);
    }
}
