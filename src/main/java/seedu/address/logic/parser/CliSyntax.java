package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DIRECTORY = new Prefix("d/");
    public static final Prefix PREFIX_FILE = new Prefix("f/");
    public static final Prefix PREFIX_TAG_NEW = new Prefix("t/");
    public static final Prefix PREFIX_TAG_REMOVE = new Prefix("-t/");
    public static final Prefix PREFIX_DEADLINE_NEW = new Prefix("date/");
    public static final Prefix PREFIX_DEADLINE_COMPLETE = new Prefix("done");

}
