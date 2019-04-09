package seedu.address.model;

import java.util.HashMap;
import java.util.List;

import seedu.address.logic.commands.Command;

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

    public List<Command> findTransformation(String presetName) {
        return transformationMap.get(presetName);
    }

    public boolean isPresent(String presetName) {
        return transformationMap.containsKey(presetName);
    }

    public void addTransformation(String presetName, List<Command> list) {
        transformationMap.put(presetName, list);
    }

}
