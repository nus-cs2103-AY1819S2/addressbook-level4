package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FUNCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.UserStory;
import seedu.address.model.project.exceptions.DuplicateUserStoryException;

/**
 * Adds a user story to a project in the projects list.
 */
public class AddUserStoryToCommand extends AddToCommand {

    public static final String ADD_USERSTORY_KEYWORD = "userstory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " PROJECT_NAME "
            + ADD_USERSTORY_KEYWORD + " "
            + PREFIX_USER + "[user] "
            + PREFIX_FUNCTION + "[function] "
            + PREFIX_REASON + "[reason] "
            + PREFIX_IMPORTANCE + "IMPORTANCE (positive integer from 1 - 3)"
            + ": add the specified user story to the project.\n"
            + "Example: "
            + COMMAND_WORD + " Project Apollo "
            + ADD_USERSTORY_KEYWORD + " "
            + PREFIX_USER + "project manager "
            + PREFIX_FUNCTION + "add employees into a project "
            + PREFIX_REASON + "track which employees are in a project "
            + PREFIX_IMPORTANCE + "2";

    public static final String MESSAGE_ADD_USER_STORY_SUCCESS = "Added user story: %1$s into %2$s";
    public static final String MESSAGE_DUPLICATE_USER_STORY = "This user story already exists in the project.";

    private final ProjectName targetProjectName;
    private final UserStory userStoryToAdd;

    public AddUserStoryToCommand(ProjectName targetProject, UserStory userStory) {
        requireNonNull(userStory);
        this.userStoryToAdd = userStory;
        this.targetProjectName = targetProject;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Project targetProject = model.getProjectWithName(targetProjectName);
        if (targetProject == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_NAME);
        }

        List<UserStory> userStoryList = targetProject.getUserStories();
        if (userStoryList.contains(userStoryToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_USER_STORY);
        }
        try {
            targetProject.addUserStory(userStoryToAdd);
        } catch (DuplicateUserStoryException e) {
            throw new CommandException(MESSAGE_DUPLICATE_USER_STORY);
        }

        model.commitPocketProject();
        return new CommandResult(String.format(MESSAGE_ADD_USER_STORY_SUCCESS, userStoryToAdd,
                targetProject.getProjectName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddUserStoryToCommand // instanceof handles nulls
                && targetProjectName.equals(((AddUserStoryToCommand) other).targetProjectName))
                && userStoryToAdd.equals(((AddUserStoryToCommand) other).userStoryToAdd); // state check
    }


}
