package seedu.hms.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_SERVICE = new Prefix("s/");
    public static final Prefix PREFIX_TIMING = new Prefix(":/");
    public static final Prefix PREFIX_ROOM = new Prefix("r/");
    public static final Prefix PREFIX_DATES = new Prefix("d/");
    public static final Prefix PREFIX_PAYER = new Prefix("$/");
    public static final Prefix PREFIX_CUSTOMERS = new Prefix("c/");
    public static final Prefix PREFIX_COMMENT = new Prefix("com/");
    public static final Prefix PREFIX_IDENTIFICATION_NUMBER = new Prefix("id/");
    public static final Prefix PREFIX_DATE_OF_BIRTH = new Prefix("dob/");

}
