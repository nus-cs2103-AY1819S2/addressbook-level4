package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_NRIC = new Prefix("ic/");
    public static final Prefix PREFIX_YEAR = new Prefix("dob/");
    public static final Prefix PREFIX_TITLE = new Prefix("ti/");
    public static final Prefix PREFIX_STARTDATE = new Prefix("sd/");
    public static final Prefix PREFIX_ENDDATE = new Prefix("ed/");
    public static final Prefix PREFIX_DESC = new Prefix("desc/");
}
