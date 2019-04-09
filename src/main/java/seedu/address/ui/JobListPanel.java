package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.job.Job;

/**
 * Panel containing the list of persons.
 */
public class JobListPanel extends UiPart<Region> {
    private static final String FXML = "JobListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(JobListPanel.class);

    @FXML
    private ListView<Job> jobListView;

    public JobListPanel(ObservableList<Job> jobList, ObservableValue<Job> selectedJob,
                           Consumer<Job> onSelectedJobChange) {
        super(FXML);
        jobListView.setItems(jobList);
        jobListView.setCellFactory(listView -> new JobListViewCell());
        jobListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in job list panel changed to : '" + newValue + "'");
            onSelectedJobChange.accept(newValue);
        });
        selectedJob.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected job changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected job,
            // otherwise we would have an infinite loop.
            if (Objects.equals(jobListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                jobListView.getSelectionModel().clearSelection();
            } else {
                int index = jobListView.getItems().indexOf(newValue);
                jobListView.scrollTo(index);
                jobListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class JobListViewCell extends ListCell<Job> {
        @Override
        protected void updateItem(Job job, boolean empty) {
            super.updateItem(job, empty);

            if (empty || job == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new JobCard(job, getIndex() + 1).getRoot());
            }
        }
    }

}
