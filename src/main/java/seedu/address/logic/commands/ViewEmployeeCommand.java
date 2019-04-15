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
 * Selects a employee identified using it's displayed index from the employee list.
 */
public class ViewEmployeeCommand extends ViewCommand {

    public static final String VIEW_EMPLOYEE_KEYWORD = "employee";

    public static final String MESSAGE_USAGE = ViewCommand.COMMAND_WORD
            + " "
            + VIEW_EMPLOYEE_KEYWORD
            + ": Selects the employee identified by the index number used in the displayed employee list.\n"
            + "Parameters: INDEX (must be a positive integer) and cannot be larger than maximum integer value which is "
            + "2,147,483,647 \n"
            + "Example: "
            + ViewCommand.COMMAND_WORD
            + " "
            + VIEW_EMPLOYEE_KEYWORD + " 1";

    public static final String MESSAGE_VIEW_EMPLOYEE_SUCCESS = "Viewing employee details at index: %1$s";

    private final Index targetIndex;

    public ViewEmployeeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Employee> filteredEmployeeList = model.getFilteredEmployeeList();

        if (targetIndex.getZeroBased() >= filteredEmployeeList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        model.setSelectedEmployee(filteredEmployeeList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_VIEW_EMPLOYEE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewEmployeeCommand // instanceof handles nulls
                && targetIndex.equals(((ViewEmployeeCommand) other).targetIndex)); // state check
    }
}
