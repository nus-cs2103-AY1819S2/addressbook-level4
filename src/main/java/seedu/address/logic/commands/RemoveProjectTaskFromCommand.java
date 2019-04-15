package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.ProjectTask;

/**
 * Removes a task from a project milestone.
 */
public class RemoveProjectTaskFromCommand extends RemoveFromCommand {

    public static final String REMOVE_PROJECTTASK_KEYWORD = "projecttask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME projecttask"
            + ": removes the project task identified by the milestone index and project task index numbers"
            + " displayed in the milestones and tasks list of the project.\n"
            + "Parameters: MILESTONE_INDEX PROJECT_TASK_INDEX(must be a positive integer and cannot be larger than "
            + "maximum integer value which is 2,147,483,647\n"
            + "Example: " + COMMAND_WORD + " Apollo projecttask 1 1";

    public static final String MESSAGE_REMOVE_PROJECTTASK_SUCCESS = "Removed %1$s from milestone %2$d in %3$s";

    private final Index targetMilestoneIndex;
    private final Index targetTaskIndex;
    private final ProjectName targetProjectName;

    public RemoveProjectTaskFromCommand(Index milestoneIndex, Index taskIndex, ProjectName targetProject) {
        requireAllNonNull(targetProject, milestoneIndex, taskIndex);
        this.targetMilestoneIndex = milestoneIndex;
        this.targetTaskIndex = taskIndex;
        this.targetProjectName = targetProject;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Project targetProject = model.getProjectWithName(targetProjectName);
        if (targetProject == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_NAME);
        }

        List<Milestone> milestoneList = targetProject.getMilestones();
        if (targetMilestoneIndex.getZeroBased() >= milestoneList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
        }
        Milestone targetMilestone = milestoneList.get(targetMilestoneIndex.getZeroBased());

        List<ProjectTask> taskList = targetMilestone.getProjectTaskList();
        if (targetTaskIndex.getZeroBased() >= taskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECTTASK_DISPLAYED_INDEX);
        }
        ProjectTask targetTask = taskList.get(targetTaskIndex.getZeroBased());

        model.removeProjectTaskFrom(targetProject, targetMilestone, targetTask);
        model.commitPocketProject();
        return new CommandResult(String.format(MESSAGE_REMOVE_PROJECTTASK_SUCCESS, targetTask,
            targetMilestoneIndex.getOneBased(), targetProject.getProjectName()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveProjectTaskFromCommand)) {
            return false;
        }

        // state check
        RemoveProjectTaskFromCommand otherCommand = (RemoveProjectTaskFromCommand) other;
        return targetMilestoneIndex.equals(otherCommand.targetMilestoneIndex)
                && targetTaskIndex.equals(otherCommand.targetTaskIndex)
                && targetProjectName.equals(otherCommand.targetProjectName); // state check
    }
}
