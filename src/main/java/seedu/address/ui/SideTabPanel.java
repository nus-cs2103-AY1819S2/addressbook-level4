package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;

/**
 * The side panel with tabpane for the application.
 */
public class SideTabPanel extends UiPart<Region> {

    public static final String[] SKILL_COLORS = {
        "blue", "indigo", "mint", "pink", "salmon", "teal", "turquoise", "pale-blue", "purple", "yellow"
    };

    private static final String FXML = "SideTabPanel.fxml";

    @FXML
    private TabPane sideTabPanel;

    @FXML
    private Tab employeeTab;

    @FXML
    private Tab projectTab;

    public SideTabPanel(Node employeeListNode, Node projectListNode) {
        super(FXML);
        employeeTab.setContent(employeeListNode);
        projectTab.setContent(projectListNode);
    }

    /**
     * Hashes the {@code name} to get a random value to indicate the colour of the label
     */
    public static String getSkillLabelColor(String name) {
        int index = Math.abs(name.hashCode()) % SKILL_COLORS.length;
        return SKILL_COLORS[index];
    }
}
