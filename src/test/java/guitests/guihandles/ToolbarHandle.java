package guitests.guihandles;

import javafx.scene.Node;

/**
 * Provides a handle to the toolbar of the app.
 */
public class ToolbarHandle extends NodeHandle<Node> {
    public static final String TOOLBAR_ID = "#toolbar";

    public ToolbarHandle(Node toolbarNode) {
        super(toolbarNode);
    }

    /**
     * Opens the {@code HelpWindow} using the menu bar in {@code MainWindow}.
     */
    public void openHelpWindow() {
        guiRobot.clickOn("#helpButton");
    }
}
