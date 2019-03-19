package seedu.address.model.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<Appointment> getAppointment(LocalDate date, LocalTime start) {
        List<Appointment> filtered = appointments.stream()
                .filter(a -> a.getDate().equals(date))
                .filter(a -> a.getStartTime().equals(start))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(filtered.get(0));
        }
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
