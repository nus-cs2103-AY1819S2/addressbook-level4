package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a analytics page
 */
public class InterviewsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(InterviewsWindow.class);
    private static final String FXML = "InterviewsWindow.fxml";

    @FXML
    private TextArea interviewsDisplay;

    /**
     * Creates a new AnalyticsWindow.
     *
     * @param root Stage to use as the root of the AnalyticsWindow.
     */
    public InterviewsWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new AnalyticsWindow.
     */
    public InterviewsWindow() {
        this(new Stage());
    }

    /**
     * Shows the Analytics window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show(String interviews) {
        logger.fine("Showing interviews results.");
        getRoot().show();
        interviewsDisplay.setText(interviews);
    }

    /**
     * Returns true if the interviews window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the interviews window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the interviews window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}

