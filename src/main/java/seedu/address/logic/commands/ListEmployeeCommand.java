package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EMPLOYEES;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all employees in the employees list to the user.
 */
public class ListEmployeeCommand extends ListCommand {

    public static final String LIST_EMPLOYEE_KEYWORD = "employee";

    public static final String MESSAGE_SUCCESS = "Listed all employees. Click on the employee tab to view result.";

    public static final String MESSAGE_USAGE = ListCommand.COMMAND_WORD
            + " "
            + LIST_EMPLOYEE_KEYWORD
            + ": Lists all employees registered in Pocket Project";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListEmployeeCommand); // instanceof handles nulls

    }
}
