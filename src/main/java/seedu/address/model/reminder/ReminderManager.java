package seedu.address.model.reminder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.model.appointment.Appointment;

/**
 * Manages the list of reminders created.
 */
public class ReminderManager {
    private final List<Reminder> reminders;

    public ReminderManager() {
        reminders = new ArrayList<>();
    }

    public void addReminder(Reminder rem) {
        reminders.add(rem);
    }

    public boolean duplicateReminder(Reminder rem) {
        return reminders.contains(rem);
    }

    public List<Reminder> getReminderList() {
        return reminders;
    }

    public void delete(Reminder reminder) {
        reminders.remove(reminder);
    }

    public Optional<Reminder> getReminder(Appointment appointment) {
        String title = Reminder.createTitle(appointment);
        LocalDate date = appointment.getDate();
        LocalTime start = appointment.getStart();

        List<Reminder> filtered = reminders.stream()
                .filter(r -> r.getTitle().equals(title))
                .filter(r -> r.getDate().equals(date))
                .filter(a -> a.getStart().equals(start))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(filtered.get(0));
        }
    }

    /**
     * Returns a {@code String} of reminders created.
     */
    public String list() {
        StringBuilder sb = new StringBuilder();
        for (Reminder rem : reminders) {
            sb.append(rem.toString() + "\n");
        }
        return sb.toString();
    }
}
