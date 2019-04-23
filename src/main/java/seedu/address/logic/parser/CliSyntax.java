package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_NRIC = new Prefix("i/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DATE = new Prefix("dt/");
    public static final Prefix PREFIX_CONDITION = new Prefix("c/");
    public static final Prefix PREFIX_HEALTHWORKER = new Prefix("hw/");
    public static final Prefix PREFIX_REQUEST = new Prefix("r/");
    public static final Prefix PREFIX_STATUS = new Prefix("st/");

    // Additional prefixes for HealthWorker
    public static final Prefix PREFIX_ORGANIZATION = new Prefix("o/");
    public static final Prefix PREFIX_SKILLS = new Prefix("s/");

    /* Prefixes for Add/Edit/Delete Command modes */
    public static final Prefix PREFIX_ADD_HEALTHWORKER = new Prefix("1");
    public static final Prefix PREFIX_ADD_PATIENT = new Prefix("3");
    public static final Prefix PREFIX_ADD_REQUEST = new Prefix("2");

    /* Prefixes for setting reminders */
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    public static final Prefix PREFIX_MESSAGE = new Prefix("m/");
}
