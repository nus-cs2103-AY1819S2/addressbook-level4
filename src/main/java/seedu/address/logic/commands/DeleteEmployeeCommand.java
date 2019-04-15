package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;

/**
 * Deletes a employee identified using it's displayed index from the pocket project.
 */
public class DeleteEmployeeCommand extends DeleteCommand {

    public static final String DELETE_EMPLOYEE_KEYWORD = "employee";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " employee"
            + ": Deletes the employee identified by the index number used in the displayed employee list.\n"
            + "Parameters: INDEX (must be a positive integer) cannot be larger than maximum integer value which is "
            + "2,147,483,647\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EMPLOYEE_SUCCESS = "Deleted Employee: %1$s";

    private final Index targetIndex;

    public DeleteEmployeeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredEmployeeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        Employee employeeToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEmployee(employeeToDelete);
        model.commitPocketProject();
        return new CommandResult(String.format(MESSAGE_DELETE_EMPLOYEE_SUCCESS, employeeToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEmployeeCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteEmployeeCommand) other).targetIndex)); // state check
    }
}
