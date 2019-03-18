package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Labeled;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String SYNC_STATUS_ID = "#syncStatus";
    private static final String CURRENT_MODE_ID = "#currentMode";

    private final Labeled syncStatusNode;
    private final Labeled currentModeNode;

    private String lastRememberedSyncStatus;
    private String lastRememberedCurrentMode;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        syncStatusNode = getChildNode(SYNC_STATUS_ID);
        currentModeNode = getChildNode(CURRENT_MODE_ID);
    }

    /**
     * Returns the text of the sync status portion of the status bar.
     */
    public String getSyncStatus() {
        return syncStatusNode.getText();
    }

    /**
     * Returns the text of the 'current mode' portion of the status bar.
     */
    public String getCurrentMode() {
        return currentModeNode.getText();
    }

    /**
     * Remembers the content of the sync status portion of the status bar.
     */
    public void rememberSyncStatus() {
        lastRememberedSyncStatus = getSyncStatus();
    }

    /**
     * Returns true if the current content of the sync status is different from the value remembered by the most recent
     * {@code rememberSyncStatus()} call.
     */
    public boolean isSyncStatusChanged() {
        return !lastRememberedSyncStatus.equals(getSyncStatus());
    }

    /**
     * Remembers the content of the 'current mode' portion of the status bar.
     */
    public void rememberCurrentMode() {
        lastRememberedCurrentMode = getCurrentMode();
    }

    /**
     * Returns true if the current content of the 'current mode' is different from the value remembered by the most
     * recent {@code rememberCurrentMode()} call.
     */
    public boolean isCurrentModeChanged() {
        return !lastRememberedCurrentMode.equals(getCurrentMode());
    }
}
