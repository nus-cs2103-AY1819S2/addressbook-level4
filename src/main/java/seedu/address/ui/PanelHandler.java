package seedu.address.ui;

import java.util.HashMap;

import javafx.scene.Node;

import seedu.address.logic.Logic;

/**
 * Handles which panels to display when the command changes
 */
public class PanelHandler {
    private HashMap<String, Node> panels = new HashMap<>();

    public PanelHandler(Logic logic) {
        super();

        //Initialize the panel's hashmap
        panels.put("displaymod", new DisplayModuleInfoList(logic.getDisplayList(), logic.selectedModuleInfoProperty(),
                logic::setSelectedModuleInfo).getRoot());
    }

    public Node getCommandPanel(String command) {
        return panels.get(command);
    }

}
