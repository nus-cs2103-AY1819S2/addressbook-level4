package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;
import seedu.finance.model.budget.Budget;
import seedu.finance.model.exceptions.CategoryBudgetExceedTotalBudgetException;

/**
 * Sets a budget in the finance tracker.
 */
public class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the budget for finance tracker. "
            + "Parameters: "
            + PREFIX_AMOUNT + "AMOUNT "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "500.50";
    public static final String MESSAGE_SUCCESS = "Budget Set: $%1$s";

    private final String amount;

    /**
     * Creates a SetCommand to set the specified {@code Amount} as budget
     */
    public SetCommand(String amount) {
        requireNonNull(amount);

        this.amount = amount;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            Budget budget = new Budget(Double.parseDouble(amount));
            model.addBudget(budget);
            model.commitFinanceTracker();
            return new CommandResult(String.format(MESSAGE_SUCCESS, amount), true, false, false);
        } catch (CategoryBudgetExceedTotalBudgetException cte) {
            throw new CommandException(cte.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof SetCommand)) {
            return false;
        }

        SetCommand e = (SetCommand) other;
        return amount.equals(e.amount);
    }
}
