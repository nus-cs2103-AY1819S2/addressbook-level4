package seedu.address.ui;

import java.util.HashMap;

import javafx.scene.Node;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.DisplaymodCommand;
import seedu.address.logic.commands.RecCommand;

/**
 * Handles which panels to display when the command changes
 */
public class PanelHandler {
    private HashMap<String, Node> panels = new HashMap<>();

    public PanelHandler(Logic logic) {
        super();

        // Initialize the hashmap
        panels.put(DisplaymodCommand.COMMAND_WORD,
                new DisplayModuleInfoList(logic.getDisplayList(), logic.selectedModuleInfoProperty(),
                logic::setSelectedModuleInfo).getRoot());
        panels.put(RecCommand.COMMAND_WORD, new DisplayRecModuleList(logic.getRecModuleListSorted()).getRoot());
    }

    public Node getCommandPanel(String command) {
        return panels.get(command);
    }

}
