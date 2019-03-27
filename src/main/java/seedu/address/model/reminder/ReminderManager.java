package seedu.address.model.reminder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicine.Medicine;

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
        LocalTime start = appointment.getStartTime();

        List<Reminder> filtered = reminders.stream()
                .filter(r -> r.getTitle().equals(title))
                .filter(r -> r.getDate().equals(date))
                .filter(a -> a.getStartTime().equals(start))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(filtered.get(0));
        }
    }

    /**
     * create and add a new reminder when medicine amount falls below threshold for some medicine
     * @param medicine
     */
    public void reminderForMedicine(Medicine medicine) {
        if (medicine.isSufficient()) {
            deleteExistingMedicineReminder(medicine);
        } else {
            String title = String.format(Medicine.REMINDER_TITLE_IF_INSUFFICIENT, medicine.name);
            String comment = String.format(Medicine.REMINDER_COMMENT_IF_INSUFFICIENT,
                    medicine.getQuantity(), medicine.getThreshold());
            LocalDate date = LocalDate.now();
            LocalTime startTime = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(),
                    LocalTime.now().getSecond());
            Reminder reminder = new Reminder(title, comment, date, startTime, null);
            deleteExistingMedicineReminder(medicine);
            addReminder(reminder);
        }
    }

    /**
     * delete any existing medicine reminder corresponding to the same medicine
     * @param medicine the medicine we are concerned with
     * @return whether something is deleted
     */
    public boolean deleteExistingMedicineReminder(Medicine medicine) {
        String title = String.format(Medicine.REMINDER_TITLE_IF_INSUFFICIENT, medicine.name);
        boolean changed = false;
        int i = 0;
        while (i < reminders.size()) {
            Reminder exReminder = reminders.get(i);
            if (exReminder.getTitle().equals(title)) {
                delete(exReminder);
                changed = true;
                i--;
            }
            i++;
        }
        return changed;
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
