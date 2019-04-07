package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Labeled;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String CURRENT_STATUS_ID = "#currentStatus";

    private final Labeled currentStatusNode;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        currentStatusNode = getChildNode(CURRENT_STATUS_ID);
    }

    /**
     * Returns the text of the current status displayed in the status bar.
     */
    public String getCurrentStatus() {
        return currentStatusNode.getText();
    }
}
