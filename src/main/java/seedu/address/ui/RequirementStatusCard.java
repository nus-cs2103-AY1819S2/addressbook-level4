package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.course.RequirementStatus;


/**
 * An UI component that displays information of a {@code CourseRequirement}.
 */
public class RequirementStatusCard extends UiPart<Region> {
    private static final String FXML = "RequirementStatusCard.fxml";
    private static final String IS_FULFILLED = "Requirement fulfilled";
    private static final String IS_NOT_FULFILLED = "Requirement not fulfilled";
    private static final String PERCENTAGE_DISPLAY = "Percentage Completed: %.2f";
    public final RequirementStatus requirementStatus;

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
    private ProgressBar percentageFulfilledBar;
    @FXML
    private ProgressIndicator percentageFulfilledIndicator;

    public RequirementStatusCard(RequirementStatus requirementStatus,
                                 int displayedIndex) {
        super(FXML);
        this.requirementStatus = requirementStatus;
        this.courseRequirementName.setText(requirementStatus.getCourseRequirement().getCourseReqName());
        this.courseRequirementDescription.setText(requirementStatus.getCourseRequirement().getCourseReqDesc());
        this.courseReqType.setText(requirementStatus.getCourseRequirement().getType().toString());
        if (requirementStatus.isFulfilled()) {
            this.courseReqIsFulfilled.setText(IS_FULFILLED);
        } else {
            this.courseReqIsFulfilled.setText(IS_NOT_FULFILLED);
        }
        this.percentageFulfilledBar.setProgress(requirementStatus.getPercentageFulfilled());
        this.percentageFulfilledIndicator.setProgress(requirementStatus.getPercentageFulfilled());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RequirementStatusCard)) {
            return false;
        }

        // state check
        RequirementStatusCard card = (RequirementStatusCard) other;
        return courseRequirementName.getText().equals(card.courseRequirementName.getText())
                && requirementStatus.equals(card.requirementStatus);
    }
}
