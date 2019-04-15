package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;

/**
 * Removes a milestone identified using it's displayed index from a project in the Pocket Project.
 */

public class RemoveMilestoneFromCommand extends RemoveFromCommand {

    public static final String REMOVE_MILESTONE_KEYWORD = "milestone";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME milestone"
            + ": removes the milestone identified by the index number"
            + " used in the displayed milestone list from the project.\n"
            + "Parameters: INDEX (must be a positive integer) and cannot be larger than maximum integer value which is "
            + "2,147,483,647 \n"
            + "Example: " + COMMAND_WORD + " Apollo milestone 1";

    public static final String MESSAGE_REMOVE_MILESTONE_SUCCESS = "Removed milestone: %1$s from %2$s";

    private final Index targetIndex;
    private final ProjectName targetProjectName;

    public RemoveMilestoneFromCommand(Index targetIndex, ProjectName targetProject) {
        this.targetProjectName = targetProject;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Project targetProject = model.getProjectWithName(targetProjectName);
        if (targetProject == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_NAME);
        }
        List<Milestone> targetList = targetProject.getMilestones();
        if (targetIndex.getZeroBased() >= targetList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
        }
        Milestone targetMilestone = targetList.get(targetIndex.getZeroBased());
        model.removeMilestoneFrom(targetProject, targetMilestone);
        model.commitPocketProject();
        return new CommandResult(String.format(MESSAGE_REMOVE_MILESTONE_SUCCESS, targetMilestone,
                targetProject.getProjectName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveMilestoneFromCommand // instanceof handles nulls
                && targetIndex.equals(((RemoveMilestoneFromCommand) other).targetIndex)
                && targetProjectName.equals(((RemoveMilestoneFromCommand) other).targetProjectName)); // state check
    }
}
