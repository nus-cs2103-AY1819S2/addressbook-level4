package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label priority;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label linkedPatient;


    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        title.setText(task.getTitle().toString() + " ");
        priority.setText(task.getPriority().getPriorityType().toUpperCase());
        priority.getStyleClass().add(task.getPriority().getPriorityType());
        priority.setStyle("-fx-text-fill: black; -fx-font-weight: bold");
        startDate.setText("Start Date: " + task.getStartDate().toString());
        endDate.setText("End Date:  " + task.getEndDate().toString());
        startTime.setText("Start Time: " + task.getStartTime().toString());
        endTime.setText("End Time: " + task.getEndTime().toString());
        if (task.getLinkedPatient() != null) {
            linkedPatient.setText("Patient involved: " + task.getLinkedPatient().getLinkedPatientName()
                                + " ( " + task.getLinkedPatient().getLinkedPatientNric() + " )");
        } else {
            linkedPatient.setText("Task is not linked to a patient");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
