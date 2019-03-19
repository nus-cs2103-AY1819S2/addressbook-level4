package seedu.address.model.moduleinfo;

import java.util.ArrayList;

/**
 * Data Structure to Store PreRequisites
 *  head is the module code
 */
public class PrerequisiteTree {

    private String head;
    private ArrayList<ArrayList<ModuleInfo>> Tree;


    public PrerequisiteTree(String head) {
        this.head = head;
        this.Tree = new ArrayList<>();
    }

    public void addAndPreRequisite(ArrayList<ModuleInfo> moduleList) {
        Tree.add(moduleList);
    }

    public int getAndTreeSize() {
        return Tree.size();
    }

    public void addOrPreRequisite(int i, ModuleInfo module) {
        Tree.get(i).add(module);
    }

    public int getOrTreeSize(int i) {
        return Tree.get(i).size();
    }


}
