package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.UserStory;

/**
 * Removes an user story identified using it's displayed index from a project in the Pocket Project.
 */

public class RemoveUserStoryFromCommand extends RemoveFromCommand {

    public static final String REMOVE_USERSTORY_KEYWORD = "userstory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME userstory"
            + ": removes the user story identified by the index number"
            + " used in the displayed user stories list from the project.\n"
            + "Parameters: INDEX (must be a positive integer) and cannot be larger than maximum integer value which is "
            + "2,147,483,647 \n"
            + "Example: " + COMMAND_WORD + " Apollo userstory 1";

    public static final String MESSAGE_REMOVE_USERSTORY_SUCCESS = "Removed user story: %1$s from %2$s";

    private final Index targetIndex;
    private final ProjectName targetProjectName;

    public RemoveUserStoryFromCommand(Index targetIndex, ProjectName targetProject) {
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
        List<UserStory> targetList = targetProject.getUserStories();
        if (targetIndex.getZeroBased() >= targetList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_USERSTORY_DISPLAYED_INDEX);
        }
        UserStory targetStory = targetList.get(targetIndex.getZeroBased());
        model.removeUserStoryFrom(targetProject, targetStory);
        model.commitPocketProject();
        return new CommandResult(String.format(MESSAGE_REMOVE_USERSTORY_SUCCESS, targetStory,
                targetProject.getProjectName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveUserStoryFromCommand // instanceof handles nulls
                && targetIndex.equals(((RemoveUserStoryFromCommand) other).targetIndex)
                && targetProjectName.equals(((RemoveUserStoryFromCommand) other).targetProjectName)); // state check
    }
}
