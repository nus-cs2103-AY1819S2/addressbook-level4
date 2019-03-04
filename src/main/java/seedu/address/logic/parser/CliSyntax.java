package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/"); // TODO: remove
    public static final Prefix PREFIX_EMAIL = new Prefix("e/"); // TODO: remove
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/"); // TODO: remove
    public static final Prefix PREFIX_TAG = new Prefix("t/"); // TODO: remove

    public static final Prefix PREFIX_COLOR = new Prefix("c/");
    public static final Prefix PREFIX_TYPE = new Prefix("t/");
}
