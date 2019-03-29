package seedu.address.testutil;

import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.TypicalPatients.CHUCK;
import static seedu.address.testutil.TypicalPatients.EVE;
import static seedu.address.testutil.TypicalPatients.getTypicalPatients;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.QuickDocs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APP_A = new Appointment(
            ALICE,
            LocalDate.parse("2019-10-23"),
            LocalTime.parse("12:00"),
            LocalTime.parse("13:00"),
            "Weekly Checkup"
    );

    public static final Appointment APP_B = new Appointment(
            BOB,
            LocalDate.parse("2019-10-24"),
            LocalTime.parse("13:00"),
            LocalTime.parse("14:00"),
            "Monthly Checkup"
    );

    public static final Appointment APP_C = new Appointment(
            CHUCK,
            LocalDate.parse("2019-10-25"),
            LocalTime.parse("14:00"),
            LocalTime.parse("15:00"),
            "Knee Injury"
    );

    public static final Appointment APP_E = new Appointment(
            EVE,
            LocalDate.parse("2019-10-26"),
            LocalTime.parse("15:00"),
            LocalTime.parse("16:00"),
            "Monthly Checkup"
    );

    /**
     * Returns a {@code QuickDocs} with all the typical appointments.
     */
    public static QuickDocs getTypicalAppointmentsQuickDocs() {
        QuickDocs qd = new QuickDocs();
        for (Patient patient : getTypicalPatients()) {
            qd.getPatientManager().addPatient(patient);
        }

        for (Appointment appointment : getTypicalAppointments()) {
            qd.getAppointmentManager().addAppointment(appointment);
        }
        return qd;
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(APP_A, APP_B, APP_C));
    }
}
