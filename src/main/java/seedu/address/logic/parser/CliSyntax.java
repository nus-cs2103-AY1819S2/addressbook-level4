package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_MODULE_INFO_CODE = new Prefix("c/");
    public static final Prefix PREFIX_SEMESTER = new Prefix("s/");
    public static final Prefix PREFIX_GRADE = new Prefix("g/");
    public static final Prefix PREFIX_EXPECTED_MIN_GRADE = new Prefix("min/");
    public static final Prefix PREFIX_EXPECTED_MAX_GRADE = new Prefix("max/");
    public static final Prefix PREFIX_LECTURE_HOUR = new Prefix("lec/");
    public static final Prefix PREFIX_TUTORIAL_HOUR = new Prefix("tut/");
    public static final Prefix PREFIX_LAB_HOUR = new Prefix("lab/");
    public static final Prefix PREFIX_PROJECT_HOUR = new Prefix("proj/");
    public static final Prefix PREFIX_PREPARATION_HOUR = new Prefix("prep/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_FINISHED = new Prefix("f/");

    /*Prefix related to finding Module Information */
    public static final Prefix PREFIX_MODCODE = new Prefix("c/");
    public static final Prefix PREFIX_MODNAME = new Prefix("n/");
    public static final Prefix PREFIX_MODDEPT = new Prefix("d/");
}
