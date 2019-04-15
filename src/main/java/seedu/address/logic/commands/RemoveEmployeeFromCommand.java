package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;

/**
 * Removes an employee identified using it's displayed index from a project in the Pocket Project.
 */
public class RemoveEmployeeFromCommand extends RemoveFromCommand {

    public static final String REMOVE_EMPLOYEE_KEYWORD = "employee";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME employee"
            + ": removes the employee identified by the index number"
            + " used in the displayed employee list from the project.\n"
            + "Parameters: INDEX (must be a positive integer) and cannot be larger than maximum integer value which is "
            + "2,147,483,647 \n"
            + "Example: " + COMMAND_WORD + " Apollo employee 1";

    public static final String MESSAGE_REMOVE_EMPLOYEE_SUCCESS = "Removed Employee: %1$s from %2$s";

    private final Index targetIndex;
    private final ProjectName targetProjectName;

    public RemoveEmployeeFromCommand(Index targetIndex, ProjectName targetProjectName) {
        this.targetProjectName = targetProjectName;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Project targetProject = model.getProjectWithName(targetProjectName);
        if (targetProject == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_NAME);
        }
        ObservableList<Employee> targetList = targetProject.getEmployees();
        if (targetIndex.getZeroBased() >= targetList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }
        Employee targetEmployee = targetList.get(targetIndex.getZeroBased());
        targetEmployee.leave(targetProject);
        model.removeEmployeeFrom(targetProject, targetEmployee);
        model.commitPocketProject();
        return new CommandResult(String.format(MESSAGE_REMOVE_EMPLOYEE_SUCCESS, targetEmployee,
                targetProject.getProjectName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveEmployeeFromCommand // instanceof handles nulls
                && targetIndex.equals(((RemoveEmployeeFromCommand) other).targetIndex)
                && targetProjectName.equals(((RemoveEmployeeFromCommand) other).targetProjectName)); // state check
    }
}
