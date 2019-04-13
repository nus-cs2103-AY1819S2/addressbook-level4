package seedu.hms.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.logic.stats.Stats;

/**
 * The individual window for displaying statistics.
 */
public class StatsWindow extends UiPart<Stage> {
    private static final String FXML = "StatsWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(StatsWindow.class);

    public final Stats stats;

    @FXML
    private TextArea statsTextArea;

    public StatsWindow(Stage root, Stats stats) {
        super(FXML, root);
        this.stats = stats;
        requireNonNull(stats);

        statsTextArea.setStyle("-fx-font-family: monospace");
        updateText();
        // Update the text area
        stats.getHms().addListener(observable -> updateText());
    }

    public StatsWindow(Stats stats) {
        this(new Stage(), stats);
    }

    /**
     * Generate a string for the given stats.
     * @param stats The stats to be formatted as a string.
     * @return A string representing the stats.
     */
    public String generateText(Stats stats) {
        return stats.toTextReport();
    }

    /**
     * Update the content of the Stats window.
     */
    public void updateText() {
        Platform.runLater(() -> {
            statsTextArea.setText(generateText(stats));
            statsTextArea.setEditable(false);
        });
    }

    /**
     * Shows the stats window.
     */
    public void show() {
        logger.fine("Showing the stats.");
        getRoot().show();
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
