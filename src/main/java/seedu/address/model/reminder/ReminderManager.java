package seedu.address.model.reminder;

import java.util.ArrayList;
import java.util.List;

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
