package guitests.guihandles;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.Node;

public class TeethPanelHandle extends NodeHandle<Node> {

    public static final String TEETH_PANEL_ID = "#teethpanel";

    private boolean isTeethLoaded = true;

    public TeethPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

    }
}
