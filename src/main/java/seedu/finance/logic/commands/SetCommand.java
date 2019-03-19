package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;
import seedu.finance.model.record.Amount;

/**
 * Sets a budget in the finance tracker.
 */
public class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the budget for finance tracker. "
            + "Parameters: "
            + PREFIX_AMOUNT + "AMOUNT "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "$500.50";
    public static final String MESSAGE_SUCCESS = "Budget Set: %1$s";
    public static final String MESSAGE_DUPLICATE_BUDGET = "A budget has already been set.";

    private final Amount amount;

    /**
     * Creates a SetCommand to set the specificed {@code Amount} as budget
     */
    public SetCommand(Amount amount) {
        requireNonNull(amount);

        this.amount = amount;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasBudget()) {
            throw new CommandException(MESSAGE_DUPLICATE_BUDGET);
        }

        model.addBudget(amount);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_SUCCESS, amount));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instaceof handles null
        if (!(other instanceof SetCommand)) {
            return false;
        }

        SetCommand e = (SetCommand) other;
        return amount.equals(e.amount);
    }
}
