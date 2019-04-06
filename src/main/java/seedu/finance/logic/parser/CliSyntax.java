package seedu.finance.logic.parser;

import seedu.finance.logic.commands.CommandFlag;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_AMOUNT = new Prefix("$/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("r/");
    public static final Prefix PREFIX_FILE = new Prefix("f/");
    public static final Prefix PREFIX_PERIOD = new Prefix("p/");
    public static final Prefix PREFIX_PERIOD_AMOUNT = new Prefix("#/");

    /* CommandFlag definitions */
    public static final CommandFlag COMMAND_FLAG_NAME = new CommandFlag("-name");
    public static final CommandFlag COMMAND_FLAG_CATEGORY = new CommandFlag("-cat");
    public static final CommandFlag COMMAND_FLAG_AMOUNT = new CommandFlag("-amount");
    public static final CommandFlag COMMAND_FLAG_DATE = new CommandFlag("-date");

    public static final CommandFlag COMMAND_FLAG_ASCENDING = new CommandFlag("-asc");
    public static final CommandFlag COMMAND_FLAG_DESCENDING = new CommandFlag("-desc");

}
