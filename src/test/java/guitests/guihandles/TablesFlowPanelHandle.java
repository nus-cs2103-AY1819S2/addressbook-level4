package guitests.guihandles;

import java.util.Set;

import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class TablesFlowPanelHandle extends NodeHandle<FlowPane> {

    public static final String TABLE_FLOW_PANEL_ID = "#tableFlowPane";

    private static final String CARD_PANE_ID = "#tableCardPane";

    public TablesFlowPanelHandle(FlowPane tablesPanelNode) {
        super(tablesPanelNode);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the flowpane are definitely in the scene graph, while some nodes that are not
     * visible in the flowpane may also be in the scene graph.
     */
    public Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

}
