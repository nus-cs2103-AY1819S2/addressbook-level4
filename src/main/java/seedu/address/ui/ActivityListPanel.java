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
import seedu.address.model.activity.Activity;

/**
 * Panel containing the list of activities.
 */
public class ActivityListPanel extends UiPart<Region> {
    private static final String FXML = "ActivityListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);

    @FXML
    private ListView<Activity> activityListView;

    public ActivityListPanel(ObservableList<Activity> activityList, ObservableValue<Activity> selectedActivity,
                             Consumer<Activity> onSelectedActivityChange) {
        super(FXML);
        activityListView.setItems(activityList);
        activityListView.setCellFactory(listView -> new ActivityListViewCell());
        activityListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in activity list panel changed to : '" + newValue + "'");
            onSelectedActivityChange.accept(newValue);
        });
        selectedActivity.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected activity changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected activity,
            // otherwise we would have an infinite loop.
            if (Objects.equals(activityListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                activityListView.getSelectionModel().clearSelection();
            } else {
                int index = activityListView.getItems().indexOf(newValue);
                activityListView.scrollTo(index);
                activityListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Activity} using a {@code ActivityCard}.
     */
    class ActivityListViewCell extends ListCell<Activity> {
        @Override
        protected void updateItem(Activity activity, boolean empty) {
            super.updateItem(activity, empty);

            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ActivityCard(activity, getIndex() + 1).getRoot());
            }
        }
    }

}
