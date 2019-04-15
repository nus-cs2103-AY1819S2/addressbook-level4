package quickdocs.ui;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import quickdocs.commons.core.LogsCenter;
import quickdocs.model.reminder.Reminder;

/**
 * Panel containing the list of reminders.
 */
public class ReminderListPanel extends UiPart<Region> {
    private static final String FXML = "ReminderListPanel.fxml";
    private static final String APPOINTMENT_BACKGROUND = "derive(lightskyblue, 50%)";
    private static final String MEDICINE_BACKGROUND = "derive(firebrick, 93%)";
    private static final String OTHER_BACKGROUND = "derive(beige, 35%)";
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);
    private final TextArea display;

    @FXML
    private ListView<Reminder> reminderListView;

    public ReminderListPanel(List<Reminder> reminderList, ObservableValue<Reminder> selectedReminder,
                             Consumer<Reminder> onSelectedReminderChange, TextArea display) {
        super(FXML);
        this.display = display;
        reminderListView.setItems((ObservableList<Reminder>) reminderList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
        reminderListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in reminder list panel changed to : '" + newValue + "'");
            onSelectedReminderChange.accept(newValue);
        });
        selectedReminder.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected reminder changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected reminder,
            // otherwise we would have an infinite loop.
            if (Objects.equals(reminderListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                reminderListView.getSelectionModel().clearSelection();
            } else {
                int index = reminderListView.getItems().indexOf(newValue);
                reminderListView.scrollTo(index);
                reminderListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Displays the selected {@code Reminder} information on the main display.
     *
     * @param display the main display on the UI
     * @param reminder the selected {@code Reminder} by mouse click
     */
    private void listReminder(TextArea display, Reminder reminder) {
        String reminderString = "---------------------------------------------------------------------------\n"
                        + "Displaying selected reminder:\n"
                        + "============================================\n"
                        + reminder.toString()
                        + "\n";
        display.appendText(reminderString);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reminder} using a {@code ReminderCard}.
     */
    class ReminderListViewCell extends ListCell<Reminder> {
        @Override
        protected void updateItem(Reminder reminder, boolean empty) {
            super.updateItem(reminder, empty);

            if (empty || reminder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderCard(reminder, getIndex() + 1).getRoot());
                String title = reminder.getTitle();
                Optional<LocalTime> end = Optional.ofNullable(reminder.getEnd());
                String comment = reminder.getComment();

                if (title.startsWith("Appointment with ")
                        && end.isPresent()
                        && !comment.isEmpty()) {
                    // Reminder is for an appointment
                    setStyle("-fx-control-inner-background: " + APPOINTMENT_BACKGROUND + ";");
                } else if (title.startsWith("Quantity of ")
                        && title.endsWith(" is low.")
                        && !end.isPresent()
                        && !comment.isEmpty()) {
                    // Reminder is for low medicine
                    setStyle("-fx-control-inner-background: " + MEDICINE_BACKGROUND + ";");
                } else {
                    setStyle("-fx-control-inner-background: " + OTHER_BACKGROUND + ";");
                }

                setOnMouseClicked(event -> listReminder(display, reminder));
            }
        }
    }
}
