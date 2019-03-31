package seedu.address.ui;

import java.util.HashMap;

import javafx.scene.Node;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.DisplaymodCommand;
import seedu.address.logic.commands.DisplayreqCommand;
import seedu.address.logic.commands.RecCommand;

/**
 * Handles which panels to display when the command changes
 */
public class PanelHandler {
    private HashMap<String, Node> panels = new HashMap<>();
    private Logic logic;
    public PanelHandler(Logic logic) {
        super();
        this.logic = logic;
        //Initialize the panels hashmap
        panels.put(DisplaymodCommand.COMMAND_WORD, new DisplayModuleInfoList(logic.getDisplayList(),
                logic.selectedModuleInfoProperty(),
                logic::setSelectedModuleInfo).getRoot());
        panels.put(DisplayreqCommand.COMMAND_WORD,
                new DisplayRequirementStatusList(logic.getRequirementStatusList()).getRoot());
     
        panels.put(RecCommand.COMMAND_WORD, new DisplayRecModuleList(logic.getRecModuleListSorted()).getRoot());
    }

    public Node getCommandPanel(String command) {
        if (command.equals(DisplayreqCommand.COMMAND_WORD)) {
            return new DisplayRequirementStatusList(logic.getRequirementStatusList()).getRoot();
        }
        return panels.get(command);
    }

}
