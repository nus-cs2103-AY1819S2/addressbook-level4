package seedu.finance.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_AMOUNT = new Prefix("$/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");

    /* Flag definitions */
    public static final Flag FLAG_NAME = new Flag("-name");
    public static final Flag FLAG_CATEGORY = new Flag("-cat");
    public static final Flag FLAG_AMOUNT = new Flag("-amount");
    public static final Flag FLAG_DATE = new Flag("-date");
}
