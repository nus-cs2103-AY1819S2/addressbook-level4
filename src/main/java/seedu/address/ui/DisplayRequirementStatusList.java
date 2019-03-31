package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.course.RequirementStatus;

/**
 * Panel containing the list of CourseRequirements.
 */
public class DisplayRequirementStatusList extends UiPart<Region> {
    private static final String FXML = "DisplayRequirementStatusList.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayRequirementStatusList.class);

    @FXML
    private ListView<RequirementStatus> requirementStatusListView;

    public DisplayRequirementStatusList(ObservableList<RequirementStatus> requirementStatusList) {
        super(FXML);
        requirementStatusListView.setItems(requirementStatusList);
        requirementStatusListView.setCellFactory(listView -> new RequirementStatusListViewCell());
    }

    /**
     * Custom {@code CourseRequirementListCell} that displays the graphics of a
     * {@code CourseRequirement} using a {@code CourseRequirementDisplay}.
     */
    class RequirementStatusListViewCell extends ListCell<RequirementStatus> {
        @Override
        protected void updateItem(RequirementStatus requirementStatus, boolean empty) {
            super.updateItem(requirementStatus, empty);

            if (empty || requirementStatus == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RequirementStatusCard(requirementStatus, getIndex() + 1).getRoot());
            }
        }

    }

}
