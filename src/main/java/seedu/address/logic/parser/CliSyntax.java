package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_MODULE_INFO_CODE = new Prefix("c/");
    public static final Prefix PREFIX_SEMESTER = new Prefix("s/");
    public static final Prefix PREFIX_GRADE = new Prefix("g/");
    public static final Prefix PREFIX_EXPECTED_MIN_GRADE = new Prefix("ming/");
    public static final Prefix PREFIX_EXPECTED_MAX_GRADE = new Prefix("maxg/");
    public static final Prefix PREFIX_MIN_CAP = new Prefix("mincap/");
    public static final Prefix PREFIX_MAX_CAP = new Prefix("maxcap/");
    public static final Prefix PREFIX_MIN_LECTURE_HOUR = new Prefix("minlec/");
    public static final Prefix PREFIX_MAX_LECTURE_HOUR = new Prefix("maxlec/");
    public static final Prefix PREFIX_MIN_TUTORIAL_HOUR = new Prefix("mintut/");
    public static final Prefix PREFIX_MAX_TUTORIAL_HOUR = new Prefix("maxtut/");
    public static final Prefix PREFIX_MIN_LAB_HOUR = new Prefix("minlab/");
    public static final Prefix PREFIX_MAX_LAB_HOUR = new Prefix("maxlab/");
    public static final Prefix PREFIX_MIN_PROJECT_HOUR = new Prefix("minproj/");
    public static final Prefix PREFIX_MAX_PROJECT_HOUR = new Prefix("maxproj/");
    public static final Prefix PREFIX_MIN_PREPARATION_HOUR = new Prefix("minprep/");
    public static final Prefix PREFIX_MAX_PREPARATION_HOUR = new Prefix("maxprep/");
    public static final Prefix PREFIX_LECTURE_HOUR = new Prefix("lec/");
    public static final Prefix PREFIX_TUTORIAL_HOUR = new Prefix("tut/");
    public static final Prefix PREFIX_LAB_HOUR = new Prefix("lab/");
    public static final Prefix PREFIX_PROJECT_HOUR = new Prefix("proj/");
    public static final Prefix PREFIX_PREPARATION_HOUR = new Prefix("prep/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_FINISHED = new Prefix("f/");
    public static final Prefix PREFIX_UNFULFILLED = new Prefix(("u/"));

    /*Prefix related to finding Module Information */
    public static final Prefix PREFIX_MODCODE = new Prefix("c/");
    public static final Prefix PREFIX_MODNAME = new Prefix("n/");
}
