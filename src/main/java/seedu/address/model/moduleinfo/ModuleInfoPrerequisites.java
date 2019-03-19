package seedu.address.model.moduleinfo;

/**
 * Represents a moduleInfo's Prerequisites
 */
public class ModuleInfoPrerequisites {
    public static final String MODULECODE_REGEX = "[A-Z]{2,3}\\d{4}[A-Z]{0,3}";
    public static final String AND_REGEX = "and";
    public static final String EQUIVALENT_REGEX = "its equivalent";
    public static final String NOREQUIREMENT_MESSAGE = "No prerequisites needed";

    private PrerequisiteTree Tree;
    private String prerequisitesString;

    public ModuleInfoPrerequisites(String Code, String prereq) {
        this.Tree = new PrerequisiteTree(Code);
        this.prerequisitesString = prereq;
    }

    /**
     * generates PreReuqisite Tree for this particular module
     */
    public void generatePrerequisiteTree() {
        if (!prerequisitesString.equals(NOREQUIREMENT_MESSAGE)) {

        }
    }






}
