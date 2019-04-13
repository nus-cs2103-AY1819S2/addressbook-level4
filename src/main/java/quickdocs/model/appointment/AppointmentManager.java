package quickdocs.model.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import quickdocs.model.Slot;
import quickdocs.model.patient.Patient;

/**
 * Manages the list of {@code Appointments} created.
 */
public class AppointmentManager {
    public static final LocalTime OPENING_HOUR = LocalTime.parse("09:00");
    public static final LocalTime CLOSING_HOUR = LocalTime.parse("18:00");

    private final List<Appointment> appointments;

    public AppointmentManager() {
        appointments = new ArrayList<>();
    }

    public List<Appointment> getAppointmentList() {
        return appointments;
    }

    public void delete(Appointment app) {
        appointments.remove(app);
    }

    public boolean hasDuplicateAppointment(Appointment app) {
        return appointments.contains(app);
    }


    /**
     * Finds and returns the {@code Appointment} in the list of appointments with the given date and start time,
     * if it exists.
     *
     * @param date the {@code LocalDate} date of the {@code Appointment} to find.
     * @param start the {@code LocalTime} start time of the {@code Appointment} to find.
     * @return the {@code Appointment} found, if it exists, else returns {@code Optional.empty()}.
     */
    public Optional<Appointment> getAppointment(LocalDate date, LocalTime start) {
        List<Appointment> filtered = appointments.stream()
                .filter(a -> a.getDate().equals(date))
                .filter(a -> a.getStart().equals(start))
                .collect(Collectors.toList());

        // filtered cannot contain > 1 appointment as each appointment is uniquely identified by its
        // date and start fields.
        assert filtered.size() <= 1;

        if (filtered.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(filtered.get(0));
        }
    }

    /**
     * Returns a {@code List} of {@code Appointment}s that are within the given search range of dates.
     *
     * @param start the {@code LocalDate start} date of the search range.
     * @param end the {@code LocalDate end} date of the search range.
     * @return {@code List} of {@code Appointment}s that has dates from {@code start} to {@code end}.
     */
    private List<Appointment> getAppointments(LocalDate start, LocalDate end) {
        List<Appointment> validApps = new ArrayList<>();
        LocalDate date;

        for (Appointment app : appointments) {
            date = app.getDate();
            // Add appointments to validApps only with dates between the given range (inclusive)
            if (date.compareTo(start) >= 0 && date.compareTo(end) <= 0) {
                validApps.add(app);
            }

            // Stop adding when date of appointment is after given end date, since appointments are already sorted
            if (date.compareTo(end) > 0) {
                break;
            }
        }
        return validApps;
    }

    /**
     * Returns a {@code List} of {@code Appointment}s that were created for the given {@code Patient}.
     *
     * @param patient the {@code Patient} whose {@code Appointment}s to be retrieved.
     * @return {@code List} of {@code Appointment}s that were created for {@code Patient patient}.
     */
    private List<Appointment> getAppointments(Patient patient) {
        List<Appointment> validApps = new ArrayList<>();

        for (Appointment app : appointments) {
            // Add appointments to validApps only if they were created for the given patient
            if (app.getPatient().equals(patient)) {
                validApps.add(app);
            }
        }
        return validApps;
    }

    /**
     * Adds an {@code Appointment} to the ordered list of appointments, in the correct position.
     *
     * @param toAdd the {@code Appointment} to add.
     */
    public void addAppointment(Appointment toAdd) {
        // checking for time conflicts should have happened in AddAppCommand
        assert !this.hasTimeConflicts(toAdd);
        if (appointments.isEmpty()) {
            appointments.add(toAdd);
            return;
        }

        // place appointment in correct position
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
     * Checks if there are any conflicts in appointment timings between the current list of appointments
     * and the given {@code Appointment}.
     *
     * @param otherApp given {@code Appointment} to check timing against the existing list of appointments.
     * @return {@code true} if there exists a conflict in timing, else return {@code false}.
     */
    public boolean hasTimeConflicts(Appointment otherApp) {
        LocalDate date = otherApp.getDate();

        for (Appointment app : appointments) {
            if (app.getDate().compareTo(date) == 0) {
                if (hasOverlappingTime(app, otherApp)) {
                    return true;
                }
            } else if (app.getDate().isAfter(date)) {
                // terminate loop early since appointments are already sorted by date and time
                break;
            }
        }
        return false;
    }

    /**
     * Checks if two {@code Appointment}s have an overlap in timing.
     *
     * @param appA first {@code Appointment} given.
     * @param appB second {@code Appontment} given.
     * @return {@code true} if there is an overlap in timing, else return {@code false}.
     */
    private boolean hasOverlappingTime(Appointment appA, Appointment appB) {
        LocalTime startA = appA.getStart();
        LocalTime endA = appA.getEnd();
        LocalTime startB = appB.getStart();
        LocalTime endB = appB.getEnd();

        // shortcut to check for overlaps between two intervals
        return startA.isBefore(endB) && startB.isBefore(endA);
    }

    /**
     * Generates a {@code String} of {@code Appointment} details with dates between a search range, inclusive.
     *
     * @param start the {@code LocalDate start} date of the search range.
     * @param end the {@code LocalDate end} date of the search range.
     * @return {@code String} of {@code Appointment} details with dates from {@code start} to {@code end}.
     */
    public String listAppointments(LocalDate start, LocalDate end) {
        List<Appointment> toList = getAppointments(start, end);
        return listAppointments(toList);
    }

    /**
     * Generates a {@code String} of {@code Appointment} details created for a given {@code Patient}.
     *
     * @param patient the {@code Patient} whose {@code Appointment} details to list.
     * @return {@code String} of {@code Appointment} details created for the given {@code Patient}.
     */
    public String listAppointments(Patient patient) {
        List<Appointment> toList = getAppointments(patient);
        return listAppointments(toList);
    }

    /**
     * Generates a {@code String} of {@code Appointment} details given the appointments.
     *
     * @param appsToList the {@code List} of {@code Appointment}s to list.
     * @return {@code String} of {@code Appointment} details.
     */
    public String listAppointments(List<Appointment> appsToList) {
        StringBuilder sb = new StringBuilder();
        // index of the appointment to be displayed
        int i = 1;

        for (Appointment app : appsToList) {
            sb.append(i)
                    .append(") ")
                    .append(app.toString())
                    .append("\n");
            i++;
        }
        return sb.toString();
    }

    /**
     * Generates a {@code List} of free {@code Slot}s within a given search range of dates.
     * A {@code Slot} is free if there are no {@code Appointment}s scheduled during that time slot.
     *
     * @param start the {@code LocalDate start} date of the search range.
     * @param end the {@code LocalDate end} date of the search range.
     * @return {@code List} of free {@code Slot}s within the given search range.
     */
    private List<Slot> getFreeSlots(LocalDate start, LocalDate end) {
        List<Slot> freeSlots = new ArrayList<>();
        List<Appointment> toSearch = getAppointments(start, end);

        // all slots are free
        if (toSearch.isEmpty()) {
            for (LocalDate date = start; date.compareTo(end) <= 0; date = date.plusDays(1)) {
                freeSlots.add(new Slot(date, OPENING_HOUR, CLOSING_HOUR));
            }
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
     * Generates a {@code String} of free {@code Slot}s given a search range of dates.
     * A {@code Slot} is free if there are no {@code Appointment}s scheduled during that time slot.
     *
     * @param start the {@code LocalDate start} date of the search range.
     * @param end the {@code LocalDate end} date of the search range.
     * @return {@code String} of free {@code Slot}s within the given search range.
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

            // Whole day is free
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentManager)) {
            return false;
        }

        AppointmentManager otherManager = (AppointmentManager) other;
        return otherManager.appointments.equals(this.appointments);
    }
}
