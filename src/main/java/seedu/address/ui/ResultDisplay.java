package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {
    public static final String STYLE_CLASS_ERROR = "error";

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    /**
     * @param feedbackToUser the feedback to return to user
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    /**
     * Sets the command box style to indicate a successful command.
     */
    public void setStyleToDefault() {
        resultDisplay.getStyleClass().remove(STYLE_CLASS_ERROR);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    public void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(STYLE_CLASS_ERROR)) {
            return;
        }

        styleClass.add(STYLE_CLASS_ERROR);
    }
}
