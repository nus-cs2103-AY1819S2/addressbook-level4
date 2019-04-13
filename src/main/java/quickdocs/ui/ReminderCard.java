package quickdocs.ui;

import java.time.LocalTime;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import quickdocs.model.reminder.Reminder;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Reminder reminder;

    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label comment;

    public ReminderCard(Reminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        setTitle(reminder);
        title.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14px");
        date.setText(reminder.getDate().toString());
        setTime(reminder);
        comment.setText(reminder.getComment());
    }

    /**
     * Initialises the text of {@code Label time} given the {@code Reminder}.
     *
     * @param reminder the {@code Reminder} to initialise the time
     */
    private void setTime(Reminder reminder) {
        LocalTime start = reminder.getStart();
        String time = start.toString();
        Optional<LocalTime> end = Optional.ofNullable(reminder.getEnd());
        if (end.isPresent()) {
            time += " to "
                    + end.get().toString();
        }
        this.time.setText(time);
    }

    /**
     * Initialises the text of {@code Label title} given the {@code Reminder}.
     *
     * @param reminder the {@code Reminder} to initialise the title
     */
    private void setTitle(Reminder reminder) {
        String title = reminder.getTitle();

        // Shorten title for appointment reminders
        if (title.startsWith("Appointment with ")) {
            int commaIndex = title.indexOf(",");
            if (commaIndex > 0) {
                title = title.substring(0, commaIndex);
            }
            title = title.replace("Appointment", "Apt");
        }

        this.title.setText(title);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && reminder.equals(card.reminder);
    }
}
