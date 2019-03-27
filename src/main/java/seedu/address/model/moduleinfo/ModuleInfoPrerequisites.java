package seedu.address.model.moduleinfo;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.ModuleTree;
import seedu.address.commons.util.Node;

/**
 * Represents a moduleInfo's Prerequisites
 */
public class ModuleInfoPrerequisites {
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

    /**
     * generates PreRequisite Tree for this particular module
     */
    public void generatePrerequisiteTree() {
        if (!prerequisitesString.equals(NOREQUIREMENT_MESSAGE)) {
            if (prerequisitesString.matches(PREREQUISITE_REGEX)) {
                String[] prerequisiteArray = prerequisiteSplitter(prerequisitesString);
                Node tempHead = treeGenerator(prerequisiteArray);
                this.tree.addTree(tempHead);
            }
        }
    }

    /**
     * Splits the prerequisite into an array that can be made into a Tree
     */
    public String[] prerequisiteSplitter(String prerequisitesString) {
        String temp = prerequisitesStringCleaner(prerequisitesString);
        String[] array = temp.split(SPLITTER_REGEX);

        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals("") || !array[i].equals(" ")) {
                array[i].trim();
                array[index++] = array[i];
            }
        }
        return Arrays.copyOf(array, index);
    }


    /**
     *
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
     *
     * @param array
     * @return
     */
    public Node treeGenerator(String[] array) {
        int i = 0;
        Node currNode;
        Node nextNode;
        Node headNode = new Node(false, false, "-");
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
                if (array.length - i >= 2 && (array[i + 1].trim().equals("and") || array[i + 1].trim().equals("or"))) {
                    String nextnode = array[i + 1].trim();
                    if (nextnode.equals("and")) {
                        headNode = new Node(false, false, AND_REGEX);
                        headNode.addChild(currNode);
                    }
                    if (nextnode.equals("or")) {
                        headNode = new Node(false, false, OR_REGEX);
                        headNode.addChild(currNode);
                    }
                    i++;

                    nextnode = array[i + 1];
                    if (headNode != null) {
                        nextNode = createMinorTree(nextnode);
                        headNode.addChild(nextNode);
                        i++;
                        prevNode = headNode;
                    }

                } else if (i + 1 >= array.length) {
                    headNode.addChild(currNode);
                    prevNode = currNode;
                } else {
                    prevNode = currNode;
                }
            }

            if (currPrereq.equals("and") || currPrereq.equals("or")) {
                if (currPrereq.equals("and")) {
                    currNode = new Node(false, false, AND_REGEX);
                } else {
                    currNode = new Node(false, false, OR_REGEX);
                }

                if (headNode == null || !currNode.getValue().equals(headNode.getValue())) {
                    if (headNode != null) {
                        currNode.addChild(headNode);
                    }
                    headNode = currNode;
                }
            }
            i++;

        }
        System.out.println(headNode.getValue());
        return headNode;
    }

    /**
     *
     * @param sequence
     * @return
     */
    public Node createMinorTree(String sequence) {
        Node pesudoHead = new Node(false, false, "");

        Pattern regexOperation = Pattern.compile(OPERATION_REGEX);
        Matcher matcher = regexOperation.matcher(sequence);

        while (matcher.find()) {
            String operation = matcher.group();

            if (operation.equals("or") || operation.equals(",") || operation.equals("/")) {
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
