package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.exceptions.CategoryBudgetExceedTotalBudgetException;
import seedu.finance.model.exceptions.SpendingInCategoryBudgetExceededException;

/**
 * Allocates a certain amount to a category
 */
public class AllocateCommand extends Command {

    public static final String COMMAND_WORD = "allocate";
    public static final String COMMAND_ALIAS = "allo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set the budget amount for a particular category "
            + "by indicating the amount and the category.\n"
            + "Existing budget for the category will be overwritten by the input\n"
            + "Parameters: " + PREFIX_AMOUNT + "AMOUNT " + PREFIX_CATEGORY + "CATEGORY\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_AMOUNT + "100.10 "
            + PREFIX_CATEGORY + "Food\n";

    public static final String MESSAGE_SUCCESS = "%s category budget set to $%.2f";

    private CategoryBudget categoryBudget;

    public AllocateCommand(CategoryBudget catBudget) {
        requireAllNonNull(catBudget);
        categoryBudget = catBudget;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            requireNonNull(model);
            model.addCategoryBudget(categoryBudget);
            model.commitFinanceTracker();
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    categoryBudget.getCategory(), categoryBudget.getTotalBudget()),
                    true, false, false);

        } catch (CategoryBudgetExceedTotalBudgetException e) {
            throw new CommandException(e.getMessage());
        } catch (SpendingInCategoryBudgetExceededException f) {
            throw new CommandException(f.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handle nulls
        if (!(other instanceof AllocateCommand)) {
            return false;
        }

        //state check
        AllocateCommand allocateCommand = (AllocateCommand) other;
        return this.categoryBudget.equals(((AllocateCommand) other).categoryBudget);
    }


}
