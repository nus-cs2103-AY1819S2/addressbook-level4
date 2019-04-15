package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.ReadOnlyDeletedSources;
import seedu.address.model.ReadOnlySourceManager;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";

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

    private static final String SOURCE_MANAGER = "SourceManager";
    private static final String DELETED_SOURCES = "DeletedSources";

    @FXML
    private Label syncStatus;
    @FXML
    private Label saveLocationStatus;


    public StatusBarFooter(Path sourceManagerPath, Path deletedSourcesPath,
                           ReadOnlySourceManager sourceManager, ReadOnlyDeletedSources deletedSources) {
        super(FXML);
        sourceManager.addListener(observable -> updateSyncStatus(SOURCE_MANAGER, sourceManagerPath));
        deletedSources.addListener(observable -> updateSyncStatus(DELETED_SOURCES, deletedSourcesPath));
        syncStatus.setText(SYNC_STATUS_INITIAL);
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
    private void updateSyncStatus(String managerType, Path path) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        syncStatus.setText(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        switch(managerType) {
        case SOURCE_MANAGER:
            saveLocationStatus.setText(Paths.get(".").resolve(path).toString());
            break;
        case DELETED_SOURCES:
            saveLocationStatus.setText(Paths.get(".").resolve(path).toString());
            break;
        default:
            // do nothing
        }
    }

}
