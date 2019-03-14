package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.record.Amount;

/**
 * Increases the budget limit for the month or week by specified amount
 */
public class IncreaseCommand extends Command {

    public static final String COMMAND_WORD = "increase";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Increases the budget for week/month "
            + "by the amount specified.\n"
            + "Existing budget will be changed accordingly.\n"
            + "Parameters: " + PREFIX_AMOUNT + "AMOUNT (must be positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "100.50";


    public static final String MESSAGE_ARGUMENTS = "Amount: %1$s";

    private final Amount amount;

    /**
     * @param amount of money to increase budget by
     */
    public IncreaseCommand(Amount amount) {
        requireAllNonNull(amount);

        this.amount = amount;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, amount));
    }

    @Override
    public boolean equals(Object other) {
        // Same object; return true
        if (other == this) {
            return true;
        }

        // instaceof handles null
        if (!(other instanceof IncreaseCommand)) {
            return false;
        }

        IncreaseCommand e = (IncreaseCommand) other;
        return amount.equals(e.amount);
    }
}
