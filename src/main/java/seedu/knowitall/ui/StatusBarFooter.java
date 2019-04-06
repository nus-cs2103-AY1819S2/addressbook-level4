package seedu.knowitall.ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.Date;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.model.VersionedCardFolder;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";

    public static final String STATUS_IN_HOME_DIRECTORY = "In Home Directory";
    public static final String STATUS_IN_FOLDER = "Inside Folder";
    public static final String STATUS_IN_TEST_SESSION = "In Test Session";
    public static final String STATUS_IN_REPORT_DISPLAY = "In Report Display";

    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label syncStatus;
    @FXML
    private Label saveLocationStatus;

    public StatusBarFooter(Path saveLocation, ObservableList<VersionedCardFolder> homeDirectory) {
        super(FXML);
        homeDirectory.addListener((ListChangeListener.Change<? extends VersionedCardFolder> change) ->
                updateSyncStatus());
        syncStatus.setText(SYNC_STATUS_INITIAL);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
    }

    /**
     * Adds a listener to the specified card folder.
     */
    public void addNewCardFolderListener(Path saveLocation, ReadOnlyCardFolder currentCardFolder) {
        currentCardFolder.addListener(observable -> updateSyncStatus());
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    /**
     * Updates "last updated" status to the current time.
     */
    private void updateSyncStatus() {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        syncStatus.setText(String.format(SYNC_STATUS_UPDATED, lastUpdated));
    }

}
