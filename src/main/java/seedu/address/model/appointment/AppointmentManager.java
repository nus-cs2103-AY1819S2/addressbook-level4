package seedu.address.model.appointment;

import java.util.ArrayList;
import java.util.List;

// TODO: constraints when adding appointment
/**
 * Manages the list of appointments created.
 */
public class AppointmentManager {
    private final List<Appointment> appointments;

    public AppointmentManager() {
        appointments = new ArrayList<>();
    }

    public void add(Appointment app) {
        appointments.add(app);
    }

    public boolean duplicateApp(Appointment app) {
        return appointments.contains(app);
    }

    public void delete(Appointment app) {
        appointments.remove(app);
    }
}
