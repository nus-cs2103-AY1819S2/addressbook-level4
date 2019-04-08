package seedu.address.model;

import java.util.HashMap;
import java.util.List;

import seedu.address.logic.commands.Command;

public class TransformationSet {

    //List of transformation created by users.
    HashMap<String, List<Command>> transformationMap;

    // Represents a singleton copy of the TransformationSet.
    private static TransformationSet instance = null;

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

    private TransformationSet() {
        this.transformationMap = new HashMap<>();
    }

    public List<Command> findTransformation(String presetName) {
        return transformationMap.get(presetName);
    }

    public void checkDuplicate(String presetName) {

    }

    public void addTransformation(String presetName, List<Command> list) {
        transformationMap.put(presetName, list);
    }

}
