package seedu.address.logic.commands;

/**
 * An abstract class, parenting SetPriceViaPathCommand and SetPriceWoPathCommand
 */
public abstract class SetPriceCommand extends Command {

    public static final String COMMAND_WORD = "price";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set the price for a specific medicine "
            + "given by the path/name. "
            + "Parameters: "
            + "[Medicine path separated by \\] "
            + "[Price]\n"
            + "Example: " + COMMAND_WORD + " "
            + "root\\TCM\\healroot 25.7\n"
            + "Or\n"
            + "Parameters: "
            + "[Name of Medicine] "
            + "[Price]\n"
            + "Example: " + COMMAND_WORD + " "
            + "healroot 25.7";

    public static final String MESSAGE_SUCCESS = "%1$s's price has been set to %2$s.\n";
}
