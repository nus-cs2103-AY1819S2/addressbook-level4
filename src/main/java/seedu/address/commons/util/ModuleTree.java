package seedu.address.commons.util;

import java.util.ArrayList;

/**
 * A Tree Data Structure for Module Prerequisites
 */
public class ModuleTree {
    private Node headNode;
    private final String code;

    public ModuleTree(String moduleCode) {
        this.code = moduleCode;
        this.headNode = new Node(true, false, code);
    }

    public void addTree(Node subHead) {
        this.headNode.addChild(subHead);
    }

    public String getModuleCode() {
        return this.code;
    }

    public Node getHead() {
        return this.headNode;
    }

    /**
     * Checks against the list of module taken with the prerequisite of the module
     * @param modules
     * @return ArrayList of Modules that is missing
     */
    public ArrayList<String> checkPrerequisites(ArrayList<String> modules) {
        ArrayList<String> missingModules = new ArrayList<>();
        getHead().checkChildren(modules, missingModules);
        return missingModules;
    }

}
