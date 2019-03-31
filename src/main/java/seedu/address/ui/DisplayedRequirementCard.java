package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.course.CourseRequirement;
import seedu.address.model.moduleinfo.ModuleInfoCode;


/**
 * An UI component that displays information of a {@code CourseRequirement}.
 */
public class DisplayedRequirementCard extends UiPart<Region> {
    private static final String FXML = "DisplayedRequirementCard.fxml";

    public final CourseRequirement courseRequirement;
    public final ObservableList<ModuleInfoCode> moduleInfoCodeList;
    @FXML
    private HBox cardPane;
    @FXML
    private Label courseRequirementName;
    @FXML
    private Label courseRequirementDescription;
    @FXML
    private Label courseReqType;
    @FXML
    private Label courseReqIsFulfilled;
    @FXML
    private Label percentageFulfilled;

    public DisplayedRequirementCard(DisplayReqList.DisplayedRequirement displayedRequirement,
                                    int displayedIndex) {
        super(FXML);
        this.courseRequirement = displayedRequirement.getCourseRequirement();
        this.moduleInfoCodeList = displayedRequirement.getModuleInfoCodeList();
        courseRequirementName.setText(courseRequirement.getCourseReqName());
        courseRequirementDescription.setText(courseRequirement.getCourseReqDesc());
        courseReqType.setText(courseRequirement.getType().toPrintedString());
        courseReqIsFulfilled.setText("Requirement is fulfilled");
        percentageFulfilled.setText(String.format("Percentage completed: %f.2", 0.5));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayedRequirementCard)) {
            return false;
        }

        // state check
        DisplayedRequirementCard card = (DisplayedRequirementCard) other;
        return courseRequirementName.getText().equals(card.courseRequirementName.getText())
                && courseRequirement.equals(card.courseRequirement);
    }
}
