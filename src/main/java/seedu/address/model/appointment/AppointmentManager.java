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

    public void addAppointment(Appointment app) {
        appointments.add(app);
    }

    public boolean duplicateApp(Appointment app) {
        return appointments.contains(app);
    }

    public List<Appointment> getAppointmentList() {
        return appointments;
    }

    /**
     * Returns a {@code String} of appointments created.
     */
    public String list() {
        StringBuilder sb = new StringBuilder();
        for (Appointment app : appointments) {
            sb.append(app.toString() + "\n");
        }
        return sb.toString();
    }

    public void delete(Appointment app) {
        appointments.remove(app);
    }
}
