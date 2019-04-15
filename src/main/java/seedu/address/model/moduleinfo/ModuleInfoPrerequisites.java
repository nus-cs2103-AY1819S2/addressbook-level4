package seedu.address.model.moduleinfo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.ModuleTree;
import seedu.address.commons.util.Node;

/**
 * Represents a moduleInfo's Prerequisites
 */
public class ModuleInfoPrerequisites {
    public static final String ENDOFLINE_REGEX = ".*?or\\b|.*?and\\b";
    public static final String MODULECODE_REGEX = "[A-Z]{2,3}\\d{4}[A-Z]{0,3}|equivalent";
    public static final String NEXTPARENT_REGEX = "or\\s[A-Z]{2,3}\\d{4}[A-Z]{0,3}|and\\s[A-Z]{2,3}\\d{4}[A-Z]{0,3}";
    public static final String PREREQUISITE_REGEX = ".*?\\b[A-Z]{2,3}\\d{4}[A-Z]{0,3}\\b.*?";
    public static final String SPLITTER_REGEX = "\\[|\\]|[()]";
    public static final String OPERATION_REGEX = "or|/|,|and|plus|&";
    public static final String CLEANER_REGEX = "[A-Z]{2,3}\\d{4}[A-Z]{0,3}|"
                                               + "[\\p{Punct}\\p{Alpha}]{1,4}\\s[A-Z]{2,3}\\d{4}[A-Z]{0,3}|"
                                               + "[()]|\\[|\\]"
                                               + "|plus\\s\\["
                                               + "|and\\s\\[|or\\s\\["
                                               + "|or\\s\\(|/[A-Z]{2,3}\\d{4}[A-Z]{0,3}|and\\s\\("
                                               + "|or equivalent|plus\\s\\(";
    public static final String OR_REGEX = "OR";
    public static final String AND_REGEX = "AND";
    public static final String NOREQUIREMENT_MESSAGE = "No prerequisites needed";
    private ModuleTree tree;
    private String prerequisitesString;

    public ModuleInfoPrerequisites(String code, String prereq) {
        this.tree = new ModuleTree(code);
        this.prerequisitesString = prereq;
    }

    public String toString() {
        return prerequisitesString;
    }

    public ModuleTree getModuleTree() {
        return this.tree;
    }

    /**
     * generates PreRequisite Tree for this particular module
     */
    public void generatePrerequisiteTree() {
        if (!prerequisitesString.equals(NOREQUIREMENT_MESSAGE) && prerequisitesString.matches(PREREQUISITE_REGEX)) {
            String[] prerequisiteArray = prerequisiteSplitter(prerequisitesString);
            Node tempHead = treeGenerator(prerequisiteArray);
            if (tempHead.getValue().equals(" ")) {
                tempHead = tempHead.getChildList().get(0);
            }
            this.tree.addTree(tempHead);
        }
    }

    /**
     * Splits the prerequisite into an array that can be made into a Tree
     */
    public String[] prerequisiteSplitter(String prerequisitesString) {
        String temp = prerequisitesStringCleaner(prerequisitesString);
        String[] array = temp.split(SPLITTER_REGEX);
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
            if (array[i].matches(PREREQUISITE_REGEX) || array[i].matches(OPERATION_REGEX)) {
                list.add(array[i]);
            }
        }
        String[] test = list.toArray(new String[list.size()]);
        return test;
    }


    /**
     * Removes any non-module Regex or non-operation regex
     * @param prerequisitesString
     * @return
     */
    public String prerequisitesStringCleaner(String prerequisitesString) {
        Pattern regex = Pattern.compile(CLEANER_REGEX);
        Matcher matcher = regex.matcher(prerequisitesString);

        String prereqString = "";
        while (matcher.find()) {
            String temp = matcher.group().trim();
            prereqString = prereqString + " " + temp;
        }
        return prereqString;
    }

    /**
     * Creates a full Tree without the main head of the prerequisite Tree
     * @param array
     * @return
     */
    public Node treeGenerator(String[] array) {
        int i = 0;
        Node currNode;
        Node nextNode;
        Node headNode = new Node(false, false, " ");
        Node prevNode = null;
        while (i < array.length) {
            String currPrereq = array[i].trim();
            if (currPrereq.matches(NEXTPARENT_REGEX)) {
                currNode = createMinorTree(currPrereq);
                if (prevNode == null && i + 1 >= array.length) {
                    Pattern pattern = Pattern.compile(MODULECODE_REGEX);
                    Matcher moduleMatcher = pattern.matcher(currPrereq);

                    while (moduleMatcher.find()) {
                        String code = moduleMatcher.group();
                        Node temp = new Node(false, true, code);
                        headNode.addChild(temp);
                    }
                } else {
                    if (prevNode.equals(headNode)) {
                        currNode.addChild(headNode);
                        headNode = currNode;
                        prevNode = headNode;
                    } else {
                        currNode.addChild(prevNode);
                        headNode.addChild(currNode);
                        prevNode = currNode;
                    }
                }
            }

            if (currPrereq.matches(PREREQUISITE_REGEX) && !currPrereq.matches(NEXTPARENT_REGEX)) {
                currNode = createMinorTree(currPrereq);
                if (array.length - i >= 2 && array[i + 1].matches(OPERATION_REGEX)) {
                    String nextnode = array[i + 1].trim();
                    Node tempHead = new Node (false, false, " ");
                    if ("and".equals(nextnode) || "plus".equals(nextnode)) {
                        tempHead = new Node(false, false, AND_REGEX);
                        tempHead.addChild(currNode);
                    }
                    if ("or".equals(nextnode) || "/".equals(nextnode) || ",".equals(nextnode)) {
                        tempHead = new Node(false, false, OR_REGEX);
                        tempHead.addChild(currNode);
                    }
                    i++;

                    nextnode = array[i + 1];
                    nextNode = createMinorTree(nextnode);
                    tempHead.addChild(nextNode);
                    i++;

                    if (headNode.isDummy()) {
                        headNode = tempHead;

                    } else if (headNode.getValue().equals(tempHead.getValue())) {
                        for (Node child : tempHead.getChildList()) {
                            headNode.addChild(child);
                        }
                    } else {
                        tempHead.addChild(headNode);
                        headNode = tempHead;
                    }
                    prevNode = headNode;

                } else if (i + 1 < array.length && array[i].matches(ENDOFLINE_REGEX)) {
                    nextNode = createMinorTree(array[i + 1]);
                    currNode.addChild(nextNode);
                    prevNode = currNode;
                    i++;

                    if (i + 1 >= array.length) {
                        headNode.addChild(currNode);
                    }
                } else if (i + 1 >= array.length) {
                    headNode.addChild(currNode);
                    prevNode = currNode;
                } else {
                    prevNode = currNode;
                }
            }

            if ("and".equals(currPrereq) || "or".equals(currPrereq)) {
                if ("and".equals(currPrereq)) {
                    currNode = new Node(false, false, AND_REGEX);
                } else {
                    currNode = new Node(false, false, OR_REGEX);
                }

                if (headNode.isDummy() || !currNode.getValue().equals(headNode.getValue())) {
                    if (!headNode.isDummy()) {
                        currNode.addChild(headNode);
                    }
                    headNode = currNode;
                }

                if (prevNode != null && !prevNode.hasParent()) {
                    currNode.addChild(prevNode);
                }
            }
            i++;
        }
        return headNode;
    }

    /**
     * Creates a branch of the main prerequisite Tree
     * @param sequence
     * @return Node pesudoHead
     */
    public Node createMinorTree(String sequence) {
        Node pesudoHead = new Node(false, false, " ");

        Pattern regexOperation = Pattern.compile(OPERATION_REGEX);
        Matcher matcher = regexOperation.matcher(sequence);

        while (matcher.find()) {
            String operation = matcher.group();

            if ("or".equals(operation) || ",".equals(operation) || "/".equals(operation)) {
                pesudoHead = new Node(false, false, OR_REGEX);
            } else {
                pesudoHead = new Node(false, false, AND_REGEX);
            }
        }

        Pattern regexCode = Pattern.compile(MODULECODE_REGEX);
        matcher = regexCode.matcher(sequence);

        while (matcher.find()) {
            String code = matcher.group();
            Node temp = new Node(false, true, code);
            pesudoHead.addChild(temp);
        }

        return pesudoHead;
    }
}
