package seedu.address.commons.util;

import java.util.ArrayList;

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
     * Add Child ...
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
    public String checkChildren(ArrayList<String> modules) {
        if (isHead()) {
            getChildList().get(HEAD_CHILD_INDEX).checkChildren(modules);
        } else if (nodeValue.equals("OR")) {
            for (int i = 0; i < getChildList().size(); i++) {
                Node currNode = getChildList().get(i);

                if (!currNode.isModule() && (currNode.getValue().equals("OR") || currNode.getValue().equals("AND"))) {
                    currNode.checkChildren(modules);
                } else {
                    for (int j = 0; j < modules.size(); j++) {
                        if (modules.get(i).equals(currNode.getValue())) {
                            return "satisfied";
                        }
                    }
                }
                if (i == getChildList().size() - 1) {
                    return "Unable to find modules for OR";
                }
            }
        } else if (nodeValue.equals("AND")) {
            for (int i = 0; i < getChildList().size(); i++) {
                Node currNode = getChildList().get(i);

                if (!currNode.isModule() && (currNode.getValue().equals("OR") || currNode.getValue().equals("AND"))) {
                    currNode.checkChildren(modules);
                } else {
                    for (int j = 0; j < modules.size(); j++) {
                        if (modules.get(i).equals(currNode.getValue())) {
                            break;
                        }

                        if (j == modules.size() - 1) {
                            return "unable to find match for:" + currNode.getValue();
                        }
                    }
                }
                if (i == getChildList().size() - 1) {
                    return "satisfied";
                }
            }
        } else {
            return "Error";
        }
        return "Error";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Node// instanceof handles nulls
                && nodeValue.equals(((Node) other).nodeValue)); // state check
    }
}
