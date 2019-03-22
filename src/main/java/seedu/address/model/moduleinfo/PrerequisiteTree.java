package seedu.address.model.moduleinfo;

import java.util.ArrayList;

/**
 * Data Structure to Store PreRequisites
 *  head is the module code
 */
public class PrerequisiteTree {

    private String head;
    private ArrayList<ArrayList<ModuleInfo>> tree;


    public PrerequisiteTree(String head) {
        this.head = head;
        this.tree = new ArrayList<>();
    }

    public void addAndPreRequisite(ArrayList<ModuleInfo> moduleList) {
        tree.add(moduleList);
    }

    public int getAndTreeSize() {
        return tree.size();
    }

    public void addOrPreRequisite(int i, ModuleInfo module) {
        tree.get(i).add(module);
    }

    public int getOrTreeSize(int i) {
        return tree.get(i).size();
    }


}
