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
import seedu.address.model.project.Status;

/**
 * Updates the status of a project task.
 */
public class UpdateProjectTaskCommand extends UpdateCommand {

    public static final String UPDATE_PROJECTTASK_KEYWORD = "projecttask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME projecttask MILESTONE_INDEX "
            + "PROJECTTASK_INDEX complete/ongoing/on hold"
            + ": updates the status of the specified project task in the specified project milestone.\n"
            + "Example: " + COMMAND_WORD + " Apollo projecttask 1 1 complete";

    public static final String MESSAGE_UPDATE_PROJECTTASK_SUCCESS = "Updated project task!";

    private final Index targetMilestoneIndex;
    private final Index targetTaskIndex;
    private final ProjectName targetProjectName;
    private final Status newStatus;

    public UpdateProjectTaskCommand(ProjectName targetProject, Index milestoneIndex, Index taskIndex,
                                    Status newStatus) {
        requireAllNonNull(targetProject, milestoneIndex, taskIndex);
        this.targetMilestoneIndex = milestoneIndex;
        this.targetTaskIndex = taskIndex;
        this.targetProjectName = targetProject;
        this.newStatus = newStatus;
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

        targetTask.updateStatus(newStatus);

        model.commitPocketProject();
        return new CommandResult(MESSAGE_UPDATE_PROJECTTASK_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateProjectTaskCommand)) {
            return false;
        }

        // state check
        UpdateProjectTaskCommand otherCommand = (UpdateProjectTaskCommand) other;
        return targetMilestoneIndex.equals(otherCommand.targetMilestoneIndex)
                && targetTaskIndex.equals(otherCommand.targetTaskIndex)
                && targetProjectName.equals(otherCommand.targetProjectName)
                && newStatus.equals(otherCommand.newStatus); // state check
    }
}
