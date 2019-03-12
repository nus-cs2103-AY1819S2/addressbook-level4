package seedu.address.logic.commands;

/**
 * An abstract command to purchase medicine
 */
public abstract class PurchaseMedicineCommand extends Command {

    public static final String COMMAND_WORD = "buyMed";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Purchases an existing medicine for certain quantity with certain cost. "
            + "Parameters: "
            + "[Medicine path separated by \\] "
            + "[Quantity of the purchase] "
            + "[Cost of the purchase]\n"
            + "Example: " + COMMAND_WORD + " "
            + "root\\TCM\\Herbs\\herb1 50 600\n"
            + "Or\n"
            + "Parameters: "
            + "[Medicine name] "
            + "[Quantity of the purchase] "
            + "[Cost of the purchase]\n"
            + "Example: " + COMMAND_WORD + " "
            + "herb1 50 600";

    public static final String MESSAGE_SUCCESS = "Purchase successful.\n";
}
