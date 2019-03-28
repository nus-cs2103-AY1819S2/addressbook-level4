package seedu.address.model.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.model.Slot;
import seedu.address.model.patient.Patient;

/**
 * Manages the list of appointments created.
 */
public class AppointmentManager {
    public static final LocalTime OPENING_HOUR = LocalTime.parse("09:00");
    public static final LocalTime CLOSING_HOUR = LocalTime.parse("18:00");

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

        // toAdd is to be placed at the end of the list
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

    private List<Appointment> getAppointments(LocalDate start, LocalDate end) {
        List<Appointment> validApps = new ArrayList<>();
        LocalDate date;
        for (Appointment app : appointments) {
            date = app.getDate();
            // Start listing only for appointments with dates between the given range
            if (date.compareTo(start) >= 0 && date.compareTo(end) <= 0) {
                validApps.add(app);
            }

            // Stop when date of appointment is after given end date, since appointments are already sorted
            if (date.compareTo(end) > 0) {
                break;
            }
        }
        return validApps;
    }

    /**
     * Returns a {@code String} of appointments with dates between a search range, inclusive.
     * @param start the start date of the search range
     * @param end the end date of the search range
     * @return {@code String} of appointments within the given search range
     */
    public String listAppointments(LocalDate start, LocalDate end) {
        StringBuilder sb = new StringBuilder();
        List<Appointment> toList = getAppointments(start, end);
        for (Appointment app : toList) {
            sb.append(app.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a {@code String} of appointments booked by a {@code Patient}.
     * @param patient the {@code Patient} whose appointments to list
     * @return {@code String} of appointments for the given patient
     */
    public String listAppointments(Patient patient) {
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

    private List<Slot> getFreeSlots(LocalDate start, LocalDate end) {
        List<Slot> freeSlots = new ArrayList<>();
        List<Appointment> toSearch = getAppointments(start, end);
        // no free slots for given range
        if (toSearch.isEmpty()) {
            return freeSlots;
        }

        Slot slot;
        LocalDate date = start;
        int index = 0;
        Appointment app = toSearch.get(index);
        Appointment prevApp = new Appointment(
                app.getPatient(), app.getDate().minusDays(1), app.getStart(), app.getEnd(), app.getComment());
        // loop through all search dates from the start
        while (date.compareTo(end) <= 0) {

            // no appointments for given date
            if (!date.isEqual(app.getDate())) {
                slot = new Slot(date, OPENING_HOUR, CLOSING_HOUR);
                freeSlots.add(slot);
            }

            LocalTime startTime;
            LocalTime endTime;
            // there are appointments in given date
            while (date.isEqual(app.getDate())) {

                // start a new slot for the day
                if (prevApp.getDate().isBefore(date)) {
                    startTime = OPENING_HOUR;
                } else {
                    startTime = prevApp.getEnd();
                }
                endTime = app.getStart();

                // do not add a free slot if the start time and end time are the same
                if (startTime != endTime) {
                    slot = new Slot(date, startTime, endTime);
                    freeSlots.add(slot);
                }
                prevApp = app;

                // no more next appointment, create last slot for the day
                if (toSearch.size() == index + 1 || date.isBefore(toSearch.get(index + 1).getDate())) {
                    startTime = app.getEnd();
                    endTime = CLOSING_HOUR;

                    // do not add a free slot if the start time and end time are the same
                    if (startTime != endTime) {
                        slot = new Slot(date, startTime, endTime);
                        freeSlots.add(slot);
                    }

                    // no more appointments in toSearch
                    if (toSearch.size() == index + 1) {
                        break;
                    }
                }
                index++;
                app = toSearch.get(index);
            }

            date = date.plusDays(1);
        }

        return freeSlots;
    }

    /**
     * Returns a {@code String} of free {@code Slot} to be used as appointment slots, given a search range.
     *
     * @param start the starting date of the range to search
     * @param end the ending date of the range to search
     * @return a string of free slots
     */
    public String listFreeSlots(LocalDate start, LocalDate end) {
        List<Slot> freeSlots = getFreeSlots(start, end);
        StringBuilder sb = new StringBuilder();
        LocalDate date = start.minusDays(1);
        for (Slot slot : freeSlots) {
            // start listing for a new date
            if (slot.getDate().isAfter(date)) {
                date = slot.getDate();
                sb.append("\n")
                        .append(date)
                        .append(": ");
            } else {
                sb.append(", ");
            }

            if (slot.getStart().equals(OPENING_HOUR) && slot.getEnd().equals(CLOSING_HOUR)) {
                sb.append("All slots are free");
            } else {
                sb.append(slot.getStart())
                        .append(" to ")
                        .append(slot.getEnd());
            }
        }

        return sb.toString();
    }

    public void delete(Appointment app) {
        appointments.remove(app);
    }
}
