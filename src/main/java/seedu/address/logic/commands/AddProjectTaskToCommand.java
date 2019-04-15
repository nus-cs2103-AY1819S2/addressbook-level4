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
import seedu.address.model.project.exceptions.DuplicateProjectTaskException;

/**
 * Adds a task to a project milestone.
 */
public class AddProjectTaskToCommand extends AddToCommand {

    public static final String ADD_PROJECTTASK_KEYWORD = "projecttask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME projecttask n/TASK_NAME m/MILESTONE_INDEX"
            + ": adds the specified project task to the list of tasks in a project's milestone specified by index.\n"
            + "Milestone index must be an integer more than 0 and cannot be larger than maximum integer value which is "
            + " 2,147,483,647.\n"
            + "Example: " + COMMAND_WORD + " Apollo projecttask n/Create feature XYZ m/1";

    public static final String MESSAGE_ADD_PROJECT_TASK_SUCCESS = "Added %1$s to milestone %2$d in %3$s";

    private final Index targetIndex;
    private final ProjectName targetProjectName;
    private final ProjectTask taskToAdd;

    public AddProjectTaskToCommand(ProjectName targetProject, ProjectTask task, Index index) {
        requireAllNonNull(targetProject, task, index);
        this.targetIndex = index;
        this.taskToAdd = task;
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
        if (targetIndex.getZeroBased() >= milestoneList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
        }

        Milestone targetMilestone = milestoneList.get(targetIndex.getZeroBased());
        if (targetMilestone.getProjectTaskList().contains(taskToAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_PROJECT_TASK);
        }

        try {
            targetMilestone.addProjectTask(taskToAdd);
        } catch (DuplicateProjectTaskException e) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_PROJECT_TASK, e);
        }

        model.commitPocketProject();
        return new CommandResult(String.format(MESSAGE_ADD_PROJECT_TASK_SUCCESS, taskToAdd,
                targetIndex.getOneBased(), targetProject.getProjectName()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddProjectTaskToCommand)) {
            return false;
        }

        // state check
        AddProjectTaskToCommand otherCommand = (AddProjectTaskToCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
            && targetProjectName.equals(otherCommand.targetProjectName)
            && taskToAdd.equals(otherCommand.taskToAdd); // state check
    }
}
