package quickdocs.model.reminder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import quickdocs.model.appointment.Appointment;
import quickdocs.model.medicine.Medicine;

/**
 * Manages the list of {@code Reminders} created.
 */
public class ReminderManager {
    private final List<Reminder> reminders;
    private final ObservableList<Reminder> internalList;
    private final ObservableList<Reminder> internalUnmodifiableList;

    public ReminderManager() {
        reminders = new ArrayList<>();
        internalList = FXCollections.observableArrayList();
        internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);
    }

    public List<Reminder> getReminderList() {
        return reminders;
    }

    public ObservableList<Reminder> getObservableReminderList() {
        return internalUnmodifiableList;
    }

    /**
     * Finds and returns the {@code Reminder} created for the given {@code Appointment} in the list of reminders,
     * if it exists.
     *
     * @param appointment the {@code Appointment}'s {@code Reminder} to find.
     * @return the {@code Reminder} found, if it exists, else returns {@code Optional.empty()}.
     */
    public Optional<Reminder> getReminder(Appointment appointment) {
        String title = appointment.createTitle();
        LocalDate date = appointment.getDate();
        LocalTime start = appointment.getStart();

        List<Reminder> filtered = reminders.stream()
                .filter(r -> r.getTitle().equals(title))
                .filter(r -> r.getDate().equals(date))
                .filter(a -> a.getStart().equals(start))
                .collect(Collectors.toList());

        // filtered cannot contain > 1 reminder as each reminder for an appointment is uniquely identified by its
        // title, date, and start fields.
        assert filtered.size() <= 1;

        if (filtered.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(filtered.get(0));
        }
    }

    /**
     * Adds a {@code Reminder} to the ordered list of reminders, in the correct position.
     *
     * @param toAdd the {@code Reminder} to be added.
     */
    public void addReminder(Reminder toAdd) {
        assert !hasDuplicateReminder(toAdd);

        if (reminders.isEmpty()) {
            reminders.add(toAdd);
            internalList.add(toAdd);
            return;
        }

        // place reminder in correct position
        for (Reminder rem : reminders) {
            if (rem.compareTo(toAdd) > 0) {
                int index = reminders.indexOf(rem);
                reminders.add(index, toAdd);
                internalList.add(index, toAdd);
                return;
            }
        }

        // toAdd is to be placed at the end of the list
        reminders.add(toAdd);
        internalList.add(toAdd);
    }

    public boolean hasDuplicateReminder(Reminder rem) {
        return reminders.contains(rem);
    }

    /**
     * Deletes a {@code Reminder} in the list of reminders.
     *
     * @param reminder the {@code Reminder} to be deleted.
     */
    public void delete(Reminder reminder) {
        reminders.remove(reminder);
        internalList.remove(reminder);
    }

    /**
     * Creates and adds a new {@code Reminder} when the given {@code Medicine}'s quantity falls below its threshold.
     *
     * @param medicine The {@code Medicine} to create a {@code Reminder} for.
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
     * Deletes any existing {@code Reminder} corresponding to the given {@code Medicine}, which was created
     * when the {@code Medicine}'s quantity fell below its threshold.
     *
     * @param medicine the {@code Medicine} whose {@code Reminder} to search for.
     * @return {@code true} if the {@code Reminder} was found and deleted, else returns {@code false}.
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ReminderManager)) {
            return false;
        }

        ReminderManager otherManager = (ReminderManager) other;
        return otherManager.reminders.equals(this.reminders);
    }
}
