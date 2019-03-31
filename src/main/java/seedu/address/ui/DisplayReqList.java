package seedu.address.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.course.CourseRequirement;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Panel containing the list of CourseRequirements.
 */
public class DisplayReqList extends UiPart<Region> {
    private static final String FXML = "DisplayReqList.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayReqList.class);

    @FXML
    private ListView<DisplayedRequirement> courseRequirementListView;
    private ObservableList<ModuleInfoCode> moduleInfoCodeList;

    public DisplayReqList(ObservableList<CourseRequirement> courseRequirementList,
                          ObservableList<ModuleInfoCode> moduleInfoCodeList) {
        super(FXML);
        this.moduleInfoCodeList = moduleInfoCodeList;
        ObservableList<DisplayedRequirement> displayedRequirements = FXCollections
                .observableArrayList(courseRequirementList.stream()
                .map(courseRequirement -> new DisplayedRequirement(courseRequirement, moduleInfoCodeList))
                .collect(Collectors.toList()));
        courseRequirementListView.setItems(displayedRequirements);
        courseRequirementListView.setCellFactory(listView -> new DisplayedRequirementListViewCell());
    }

    /**
     * Custom {@code CourseRequirementListCell} that displays the graphics of a
     * {@code CourseRequirement} using a {@code CourseRequirementDisplay}.
     */
    class DisplayedRequirementListViewCell extends ListCell<DisplayedRequirement> {
        @Override
        protected void updateItem(DisplayedRequirement displayedRequirement, boolean empty) {
            super.updateItem(displayedRequirement, empty);

            if (empty || displayedRequirement == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayedRequirementCard(displayedRequirement, getIndex() + 1).getRoot());
            }
        }

    }
    /**
     * A tuple to store CourseRequirement and ModuleInfoCode for display purposes
     */
    class DisplayedRequirement {
        private final CourseRequirement courseRequirement;
        private final ObservableList<ModuleInfoCode> moduleInfoCodeList;

        public DisplayedRequirement(CourseRequirement courseRequirement,
                                    ObservableList<ModuleInfoCode> moduleInfoCodeList) {
            this.courseRequirement = courseRequirement;
            this.moduleInfoCodeList = moduleInfoCodeList;
        }

        public CourseRequirement getCourseRequirement() {
            return courseRequirement;
        }

        public ObservableList<ModuleInfoCode> getModuleInfoCodeList() {
            return moduleInfoCodeList;
        }
    }


}
