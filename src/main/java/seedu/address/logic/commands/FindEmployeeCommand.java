package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.employee.EmployeeNameContainsKeywordsPredicate;

/**
 * Finds and lists all employees in PocketProject whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindEmployeeCommand extends FindCommand {

    public static final String FIND_EMPLOYEE_KEYWORD = "employee";

    public static final String MESSAGE_USAGE = FindCommand.COMMAND_WORD + " " + FIND_EMPLOYEE_KEYWORD
        + " [ARGUMENTS]" + "\n"
        + ": Find all employees with names containing [ARGUMENTS]";

    private final EmployeeNameContainsKeywordsPredicate predicate;

    public FindEmployeeCommand(EmployeeNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredEmployeeList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_EMPLOYEES_LISTED_OVERVIEW, model.getFilteredEmployeeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindEmployeeCommand // instanceof handles nulls
            && predicate.equals(((FindEmployeeCommand) other).predicate)); // state check
    }
}
