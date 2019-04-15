package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.Status;
import seedu.address.model.project.UserStory;

/**
 * Updates the status of an user story.
 */
public class UpdateUserStoryCommand extends UpdateCommand {

    public static final String UPDATE_STORY_KEYWORD = "userstory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME userstory INDEX complete/ongoing/on hold"
            + ": updates the status of the specified user story in the specified project.\n"
            + "Example: " + COMMAND_WORD + " Apollo userstory 1 ongoing";

    public static final String MESSAGE_UPDATE_STORY_SUCCESS = "Updated user story!";

    private final Index targetIndex;
    private final ProjectName targetProjectName;
    private final Status newStatus;

    public UpdateUserStoryCommand(ProjectName targetProject, Index index, Status newStatus) {
        requireAllNonNull(targetProject, index);
        this.targetIndex = index;
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

        List<UserStory> targetList = targetProject.getUserStories();
        if (targetIndex.getZeroBased() >= targetList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_USERSTORY_DISPLAYED_INDEX);
        }
        UserStory targetStory = targetList.get(targetIndex.getZeroBased());
        targetStory.updateStatus(newStatus);

        model.commitPocketProject();
        return new CommandResult(String.format(MESSAGE_UPDATE_STORY_SUCCESS, targetStory,
                targetProject.getProjectName()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateUserStoryCommand)) {
            return false;
        }

        // state check
        UpdateUserStoryCommand otherCommand = (UpdateUserStoryCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && targetProjectName.equals(otherCommand.targetProjectName)
                && newStatus.equals(otherCommand.newStatus); // state check
    }
}
