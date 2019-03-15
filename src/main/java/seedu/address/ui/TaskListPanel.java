package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.task.Task;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<Task> taskListView;
    @FXML
    private Label header;

    public TaskListPanel(ObservableList<Task> taskList) {
        super(FXML);
        header.setText("Tasks");
        header.setMaxWidth(Double.MAX_VALUE);
        //  header.setAlignment(Pos.CENTER_LEFT);
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        /*
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in person list panel changed to : '" + newValue + "'");
            onSelectedPersonChange.accept(newValue);
        });
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected person changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected person,
            // otherwise we would have an infinite loop.
            if (Objects.equals(taskListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                taskListView.getSelectionModel().clearSelection();
            } else {
                int index = taskListView.getItems().indexOf(newValue);
                taskListView.scrollTo(index);
                taskListView.getSelectionModel().clearAndSelect(index);
            }
        });*/
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }

}
