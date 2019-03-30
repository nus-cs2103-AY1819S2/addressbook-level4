package seedu.address.commons.util;

import java.util.ArrayList;

//import seedu.address.model.moduleinfo.ModuleInfo;

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
    public String checkPrerequisites(ArrayList<String> modules) {
        return headNode.checkChildren(modules);
    }

}
