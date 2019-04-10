package seedu.knowitall.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String STATUS_IN_HOME_DIRECTORY = "In Home Directory";
    public static final String STATUS_IN_FOLDER = "Inside Folder: %1$s";
    public static final String STATUS_IN_TEST_SESSION = "In Test Session";
    public static final String STATUS_IN_REPORT_DISPLAY = "In Report Display";

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label currentStatus;

    /**
     * Initialise status bar to display user default status to be in home directory when user launch the app.
     */
    public StatusBarFooter() {
        super(FXML);
        currentStatus.setText(STATUS_IN_HOME_DIRECTORY);
    }

    /**
     * Updates status bar to state current user is currently in the home directory.
     */
    public void updateStatusBarInHomeDirectory() {
        updateStatusBar(STATUS_IN_HOME_DIRECTORY);
    }

    /**
     * Updates status bar to state current user is currently in a folder.
     */
    public void updateStatusBarInFolder(String folderName) {
        updateStatusBar(String.format(STATUS_IN_FOLDER, folderName));
    }

    /**
     * Updates status bar to state current user is currently in a test session.
     */
    public void updateStatusBarInTestSession() {
        updateStatusBar(STATUS_IN_TEST_SESSION);
    }

    /**
     * Updates status bar to state current user is currently in a report display.
     */
    public void updateStatusBarInReportDisplay() {
        updateStatusBar(STATUS_IN_REPORT_DISPLAY);
    }

    /**
     * Updates status bar to display the specified status.
     */
    private void updateStatusBar(String status) {
        currentStatus.setText(status);
    }
}
