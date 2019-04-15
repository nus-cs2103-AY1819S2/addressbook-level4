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
 * Deletes a project identified using it's displayed index/name from the pocket project.
 */
public class DeleteProjectCommand extends DeleteCommand {

    public static final String DELETE_PROJECT_KEYWORD = "project";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " project"
            + ": Deletes the project identified by the name or index of the project.\n"
            + "Parameters: PROJECT_NAME/PROJECT_INDEX. If using the PROJECT_INDEX, it must be a positive integer and "
            + "cannot be larger than maximum integer value which is 2,147,483,647\n"
            + "Example: " + COMMAND_WORD + " project" + " Apollo\n"
            + "Example: " + COMMAND_WORD + " project" + " 1\n";

    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "Deleted Project: %1$s";

    private final ProjectName projectName;
    private final Index targetIndex;

    public DeleteProjectCommand(ProjectName targetName) {
        this.projectName = targetName;
        this.targetIndex = null;
    }

    public DeleteProjectCommand(Index index) {
        this.targetIndex = index;
        this.projectName = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ProjectName targetName = this.projectName;
        if (targetName == null) {
            List<Project> lastShownList = model.getFilteredProjectList();
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
            }
            targetName = lastShownList.get(targetIndex.getZeroBased()).getProjectName();
        }
        List<Project> projectList = model.getProjectList();
        Project projectToDelete = null;
        boolean found = false;
        for (Project p: projectList) {
            if (p.hasProjectName(targetName)) {
                found = true;
                projectToDelete = p;
                for (Employee employee: p.getEmployees()) {
                    employee.leave(projectToDelete);
                }
                model.deleteProject(projectToDelete);
                model.commitPocketProject();
                break;
            }
        }
        if (!found) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_NAME);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_SUCCESS, projectToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (projectName != null) {
            return other == this // short circuit if same object
                    || (other instanceof DeleteProjectCommand // instanceof handles nulls
                    && projectName.equals(((DeleteProjectCommand) other).projectName));
            // state check
        } else {
            return other == this // short circuit if same object
                    || (other instanceof DeleteProjectCommand // instanceof handles nulls
                    && targetIndex.equals(((DeleteProjectCommand) other).targetIndex)); // state check
        }
    }
}
