package guitests.guihandles;

import javafx.scene.Node;

/**
 * Provides a handle for Teethpanel
 */
public class TeethPanelHandle extends NodeHandle<Node> {

    public static final String TEETH_PANEL_ID = "#teethpanel";

    private boolean isTeethLoaded = true;

    public TeethPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

    }
}
