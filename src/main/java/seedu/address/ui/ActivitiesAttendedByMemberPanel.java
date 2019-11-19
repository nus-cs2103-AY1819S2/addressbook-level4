package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.activity.Activity;

/**
 * Panel containing the list of activities attended by member.
 */
public class ActivitiesAttendedByMemberPanel extends UiPart<Region> {
    private static final String FXML = "ActivitiesAttendedByMemberPanel.fxml";

    @javafx.fxml.FXML
    private ListView<Activity> activitiesAttendedByMember;

    public ActivitiesAttendedByMemberPanel(ObservableList<Activity> activityList) {
        super(FXML);

        getRoot().setOnKeyPressed(Event::consume);

        activitiesAttendedByMember.setItems(activityList);
        activitiesAttendedByMember.setCellFactory(listView -> new ActivityListViewCell());
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
