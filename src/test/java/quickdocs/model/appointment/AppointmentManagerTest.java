package quickdocs.model.appointment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static quickdocs.testutil.TypicalAppointments.APP_A;
import static quickdocs.testutil.TypicalAppointments.APP_C;
import static quickdocs.testutil.TypicalAppointments.APP_E;
import static quickdocs.testutil.TypicalAppointments.getTypicalAppointments;

import java.util.List;

import org.junit.Test;

/**
 * Contains unit tests for {@code AppointmentManager}. Most methods are already tested by commands related
 * to {@code Appointment}s. Hence {@code AppointmentManagerTest} will only test for equals() method.
 */
public class AppointmentManagerTest {
    @Test
    public void equals() {
        // add some sample appointments into the manager
        AppointmentManager appManA = new AppointmentManager();
        List<Appointment> appointments = getTypicalAppointments();
        for (Appointment app : appointments) {
            appManA.addAppointment(app);
        }

        // test equality of same object -> returns true
        assertEquals(appManA, appManA);

        // test equality of different AppointmentManagers with same appointments added -> returns true
        AppointmentManager appManACopy = new AppointmentManager();
        for (Appointment app : appointments) {
            appManACopy.addAppointment(app);
        }
        assertEquals(appManA, appManACopy);

        // test equality of different AppointmentManagers with 0 appointments added -> returns true
        AppointmentManager appManB = new AppointmentManager();
        AppointmentManager appManBCopy = new AppointmentManager();
        assertEquals(appManB, appManBCopy);

        // test equality with null -> returns false
        assertNotEquals(appManA, null);

        // test equality of different types -> returns false
        assertNotEquals(appManA, APP_A);

        // test equality of different AppointmentManagers which contains different appointments -> returns false
        appManB = new AppointmentManager();
        appManB.addAppointment(APP_C);
        appManB.addAppointment(APP_E);
        assertNotEquals(appManA, appManB);

        // test equality of different AppointmentManagers which contains 0 appointments -> returns false
        appManB = new AppointmentManager();
        assertNotEquals(appManA, appManB);
    }
}
