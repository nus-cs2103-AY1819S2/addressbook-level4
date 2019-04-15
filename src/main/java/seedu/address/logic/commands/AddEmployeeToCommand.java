package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;

/**
 *  Adds an employee input by the user into the employee list associated with each project.
 */
public class AddEmployeeToCommand extends AddToCommand {

    public static final String ADD_EMPLOYEE_KEYWORD = "employee";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME employee"
            + ": adds the employee by the index number used in"
            + "  the displayed employee list into the respective list stored under the stated project.\n"
            + "Parameters: INDEX (must be a positive integer) and cannot be larger than maximum integer value which is "
            + "2,147,483,647 \n"
            + "Example: " + COMMAND_WORD + " Apollo employee 1";

    public static final String MESSAGE_ADDTOPROJECT_EMPLOYEE_SUCCESS = "Added Employee: %1$s to %2$s";

    private final Index targetIndex;
    private final ProjectName targetProjectName;

    public AddEmployeeToCommand(Index targetIndex, ProjectName targetProjectName) {
        this.targetProjectName = targetProjectName;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Employee> lastShownList = model.getFilteredEmployeeList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }
        Employee employeeToAdd = lastShownList.get(targetIndex.getZeroBased());

        Project targetProject = model.getProjectWithName(targetProjectName);
        if (targetProject == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_NAME);
        }

        if (targetProject.containsEmployee(employeeToAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_PROJ_EMPLOYEE);
        }

        model.addEmployeeTo(targetProject, employeeToAdd);
        model.commitPocketProject();
        return new CommandResult(String.format(MESSAGE_ADDTOPROJECT_EMPLOYEE_SUCCESS, employeeToAdd,
            targetProject.getProjectName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEmployeeToCommand // instanceof handles nulls
                && targetIndex.equals(((AddEmployeeToCommand) other).targetIndex)
                && targetProjectName.equals(((AddEmployeeToCommand) other).targetProjectName)); // state check
    }
}
