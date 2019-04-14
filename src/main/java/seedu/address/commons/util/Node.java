package seedu.address.commons.util;

import java.util.ArrayList;
/**
 * Node for Prerequisite Tree
 */
public class Node {

    private static final int HEAD_CHILD_INDEX = 0;

    private boolean isHead;
    private Node parent;
    private boolean isModule;
    private String nodeValue;
    private ArrayList<Node> childList;

    public Node(boolean isHead, boolean isModule, String value) {
        this.isHead = isHead;
        this.isModule = isModule;
        this.nodeValue = value;
        childList = new ArrayList<>();
    }

    public boolean isHead() {
        return isHead;
    }

    public boolean isModule() {
        return isModule;
    }

    /**
     * Checks if a particular node has a parent Node
     * @return boolean value if it has a parent or not
     */
    public boolean hasParent() {
        return (parent != null);
    }

    public boolean isDummy() {
        return nodeValue.equals(" ");
    }

    public void setHead(boolean b) {
        this.isHead = b;
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
        if (hasNoChild()) {
            return;
        }
        if (isHead()) {
            childList.get(HEAD_CHILD_INDEX).checkChildren(modules, missingModules);
        } else if ("OR".equals(nodeValue) || " ".equals(nodeValue)) {
            for (int i = 0; i < getChildList().size(); i++) {
                Node currNode = getChildList().get(i);

                if (!currNode.isModule()) {
                    currNode.checkChildren(modules, missingModules);
                } else {
                    for (int j = 0; j < modules.size(); j++) {
                        /* the following condition is to account for modules ending with a letter but not
                        reflected in the json file */
                        if (modules.get(j).contains(currNode.nodeValue)
                                || currNode.nodeValue.contains(modules.get(j))) {
                            return;
                        }
                    }
                    if (i == getChildList().size() - 1) {
                        //gives the last value of the OR list
                        missingModules.add(currNode.nodeValue);
                    }
                }
            }
        } else if ("AND".equals(nodeValue)) {
            for (int i = 0; i < getChildList().size(); i++) {
                boolean found = false;
                Node currNode = getChildList().get(i);
                if (!currNode.isModule()) {
                    currNode.checkChildren(modules, missingModules);
                } else {
                    for (int j = 0; j < modules.size(); j++) {
                        if (modules.get(j).contains(currNode.nodeValue)
                                || currNode.nodeValue.contains(modules.get(j))) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        missingModules.add(currNode.nodeValue);
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
