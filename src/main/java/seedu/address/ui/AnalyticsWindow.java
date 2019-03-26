package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class AnalyticsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(AnalyticsWindow.class);
    private static final String FXML = "AnalyticsWindow.fxml";

    @FXML
    private TextArea analyticsDisplay;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public AnalyticsWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new HelpWindow.
     */
    public AnalyticsWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
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
    public void show(String analytics) {
        logger.fine("Showing analytics results.");
        getRoot().show();
        analyticsDisplay.setText(analytics);
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
