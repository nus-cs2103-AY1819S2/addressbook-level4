package quickdocs.model.util;

import java.time.LocalDate;
import java.time.LocalTime;

import quickdocs.model.appointment.Appointment;
import quickdocs.model.patient.Patient;

/**
 * Sample {@code Appointment} data to initialise model
 */
public class SampleAppUtil {
    public static Appointment[] getSampleAppointments(Patient[] patients) {
        return new Appointment[]{
            new Appointment(patients[0], LocalDate.of(2019, 03, 15),
                    LocalTime.of(9, 0), LocalTime.of(10, 0), "Weekly checkup"),
            new Appointment(patients[1], LocalDate.of(2019, 04, 16),
                    LocalTime.of(10, 0), LocalTime.of(11, 0), "Monthly checkup"),
            new Appointment(patients[2], LocalDate.of(2019, 05, 17),
                    LocalTime.of(11, 30), LocalTime.of(12, 0), "Weekly checkup")
        };
    }
}
