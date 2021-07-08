package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {
    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_COORDINATES = new Prefix("c/");
    public static final Prefix PREFIX_ORIENTATION = new Prefix("r/");

    private CliSyntax() {}
}
