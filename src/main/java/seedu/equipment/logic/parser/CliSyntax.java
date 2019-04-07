package seedu.equipment.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_PM = new Prefix("pm/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_SERIALNUMBER = new Prefix("s/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_ASSIGNEE = new Prefix("a/");
    public static final Prefix PREFIX_ID = new Prefix("i/");

    public static final String NAME_SORT_PARAMETER = "name";
    public static final String DATE_SORT_PARAMETER = "date";
    public static final String PHONE_SORT_PARAMETER = "phone";
    public static final String SERIAL_SORT_PARAMETER = "serial";
}
