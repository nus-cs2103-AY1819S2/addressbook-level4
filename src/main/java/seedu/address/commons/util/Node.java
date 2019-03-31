package seedu.address.commons.util;

import java.util.ArrayList;
/**
 * Nodes for Prerequisite Tree
 */
public class Node {
    private static final int HEAD_CHILD_INDEX = 0;

    private boolean head;
    private Node parent;
    private boolean isModule;
    private String nodeValue;
    private ArrayList<Node> childList;

    public Node(boolean isHead, boolean isModule, String value) {
        this.head = isHead;
        this.isModule = isModule;
        this.nodeValue = value;
        childList = new ArrayList<>();
    }

    public boolean isHead() {
        return head;
    }

    public boolean isModule() {
        return isModule;
    }

    /**
     * Checks if a particular node has a parent Node
     * @return boolean value if it has a parent or not
     */
    public boolean hasParent() {
        return (parent == null);
    }

    public boolean isDummy() {
        return (getValue().equals(" "));
    }

    public void setHead(boolean b) {
        this.head = b;
    }

    public String getValue() {
        return nodeValue;
    }

    private void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Add Child to the current Node
     */
    public void addChild(Node child) {
        child.setParent(this);
        childList.add(child);
    }

    public boolean hasNoChild() {
        return childList.isEmpty();
    }

    public ArrayList<Node> getChildList() {
        return this.childList;
    }

    public void addChildList(ArrayList<Node> list) {
        this.childList = list;
    }

    /**
     * Method to check for nodes matching
     */
    public void checkChildren(ArrayList<String> modules, ArrayList<String> missingModules) {

        if (isHead() || "Requires:".equals(getValue())) {
            getChildList().get(HEAD_CHILD_INDEX).checkChildren(modules, missingModules);
        } else if ("OR".equals(nodeValue)) {
            for (int i = 0; i < getChildList().size(); i++) {
                Node currNode = getChildList().get(i);

                if (!currNode.isModule() && (currNode.getValue().equals("OR") || currNode.getValue().equals("AND"))) {
                    currNode.checkChildren(modules, missingModules);
                } else {
                    for (int j = 0; j < modules.size(); j++) {
                        if (modules.get(j).equals(currNode.getValue()) && currNode.isModule()) {
                            return;
                        }
                    }
                }
                if (i == getChildList().size() - 1 && currNode.isModule()) {
                    //gives the last value of the OR list
                    missingModules.add(currNode.getValue());
                }
            }
        } else if ("AND".equals(nodeValue)) {
            for (int i = 0; i < getChildList().size(); i++) {
                Node currNode = getChildList().get(i);
                if (!currNode.isModule() && (currNode.getValue().equals("OR") || currNode.getValue().equals("AND"))) {
                    currNode.checkChildren(modules, missingModules);
                } else {

                    for (int j = 0; j < modules.size(); j++) {
                        if (modules.get(j).equals(currNode.getValue())) {
                            break;
                        }
                        if (j == modules.size() - 1 && currNode.isModule()) {
                            missingModules.add(currNode.getValue());
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Node// instanceof handles nulls
                && nodeValue.equals(((Node) other).nodeValue)) // state check
                && childList.size() == ((Node) other).childList.size();
    }
}
