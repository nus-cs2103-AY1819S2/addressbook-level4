/* @@author thamsimun */
package seedu.address.model;

import java.util.HashMap;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.WaterMarkCommand;

/**
 * Represents a TransformationSet of preset commands.
 * TransformationSet manages the preset commands for users to use.
 * Uses singleton pattern to ensure only a single instance of TransformationSet is used the whole runtime of
 * the application.
 */
public class TransformationSet {


    // Represents a singleton copy of the TransformationSet.
    private static TransformationSet instance = null;

    //List of transformation created by users.
    private HashMap<String, List<Command>> transformationMap;

    private TransformationSet() {
        this.transformationMap = new HashMap<>();
    }

    /**
     * Gets the current instance of TransformationSet.
     *
     * @return Returns the singleton TransformationSet instance.
     */
    public static TransformationSet getInstance() {
        if (instance == null) {
            instance = new TransformationSet();
        }
        return instance;
    }

    /**
     * Check if there is a watermarkcommand in the list of command found with the key.
     * @param presetName name of the preset
     * @return true if there is a watermarkcommand, else false
     */
    public boolean hasWaterMarkCommand(String presetName) {
        List<Command> commandList = transformationMap.get(presetName);
        for (Command command: commandList) {
            if (command instanceof WaterMarkCommand) {
                return true;
            }
        }
        return false;
    }

    public List<Command> findTransformation(String presetName) {
        return transformationMap.get(presetName);
    }

    public boolean isPresent(String presetName) {
        return transformationMap.containsKey(presetName);
    }

    public void addTransformation(String presetName, List<Command> list) {
        transformationMap.put(presetName, list);
    }

    public void clear() {
        transformationMap.clear();
    }

}
/* @@author*/
