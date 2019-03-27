package seedu.address.model.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.model.patient.Patient;

/**
 * Manages the list of appointments created.
 */
public class AppointmentManager {
    private final List<Appointment> appointments;

    public AppointmentManager() {
        appointments = new ArrayList<>();
    }

    /**
     * Adds an {@code Appointment} to the ordered list of appointments, in the correct position.
     *
     * @param toAdd the {@code Appointment} to add
     */
    public void addAppointment(Appointment toAdd) {
        //assert !this.hasTimeConflicts(toAdd);
        LocalDate date = toAdd.getDate();
        LocalTime end = toAdd.getEnd();

        if (appointments.isEmpty()) {
            appointments.add(toAdd);
            return;
        }

        for (Appointment app : appointments) {
            if (app.compareTo(toAdd) > 0) {
                int index = appointments.indexOf(app);
                appointments.add(index, toAdd);
                return;
            }
        }

        // toAdd fits at the end of the list
        appointments.add(toAdd);
    }

    /**
     * Checks if there is any conflict in appointment timings, given an appointment.
     *
     * @param otherApp given appointment to check timing against the existing list of appointments
     * @return true if there exists a conflict in timing, else return false
     */
    public boolean hasTimeConflicts(Appointment otherApp) {
        LocalDate date = otherApp.getDate();

        // appointments are sorted by date and time
        for (Appointment app : appointments) {
            if (app.getDate().compareTo(date) == 0) {
                if (hasOverlappingTime(app, otherApp)) {
                    return true;
                }
            } else if (app.getDate().isAfter(date)) {
                break;
            }
        }
        return false;
    }

    /**
     * Checks if two {@code Appointment} has an overlap in timing.
     *
     * @param appA first appointment
     * @param appB second appointment
     * @return true if there is an overlap in timing, else return false
     */
    private boolean hasOverlappingTime(Appointment appA, Appointment appB) {
        LocalTime startA = appA.getStart();
        LocalTime endA = appA.getEnd();
        LocalTime startB = appB.getStart();
        LocalTime endB = appB.getEnd();

        return startA.isBefore(endB) && startB.isBefore(endA);
    }

    public List<Appointment> getAppointmentList() {
        return appointments;
    }

    public Optional<Appointment> getAppointment(LocalDate date, LocalTime start) {
        List<Appointment> filtered = appointments.stream()
                .filter(a -> a.getDate().equals(date))
                .filter(a -> a.getStart().equals(start))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(filtered.get(0));
        }
    }

    /**
     * Returns a {@code String} of appointments with dates between a search range, inclusive.
     * @param start the start date of the search range
     * @param end the end date of the search range
     * @return {@code String} of appointments within the given search range
     */
    public String list(LocalDate start, LocalDate end) {
        StringBuilder sb = new StringBuilder();
        LocalDate date;
        for (Appointment app : appointments) {
            date = app.getDate();
            // Start listing only for appointments with dates between the given range
            if (date.compareTo(start) >= 0 && date.compareTo(end) <= 0) {
                sb.append(app.toString() + "\n");
            }

            // Stop when date of appointment is after given end date, since appointments are already sorted
            if (date.compareTo(end) > 0) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    /**
     * Returns a {@code String} of appointments booked by a {@code Patient}.
     * @param patient the {@code Patient} whose appointments to list
     * @return {@code String} of appointments for the given patient
     */
    public String list(Patient patient) {
        StringBuilder sb = new StringBuilder();
        Patient p;
        for (Appointment app : appointments) {
            p = app.getPatient();
            if (p.equals(patient)) {
                sb.append(app.toString() + "\n");
            }
        }
        return sb.toString();
    }

    public void delete(Appointment app) {
        appointments.remove(app);
    }
}
