package quickdocs.ui;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import quickdocs.model.reminder.Reminder;
import quickdocs.commons.core.LogsCenter;

/**
 * Panel containing the list of persons.
 */
public class ReminderListPanel extends UiPart<Region> {
    private static final String FXML = "ReminderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);

    @FXML
    private ListView<Reminder> reminderListView;

    public ReminderListPanel(List<Reminder> reminderList, ObservableValue<Reminder> selectedReminder,
                             Consumer<Reminder> onSelectedReminderChange) {
        super(FXML);
        reminderListView.setItems((ObservableList<Reminder>) reminderList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
        reminderListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in reminder list panel changed to : '" + newValue + "'");
            onSelectedReminderChange.accept(newValue);
        });
        selectedReminder.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected reminder changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected person,
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
            }
        }
    }

}
