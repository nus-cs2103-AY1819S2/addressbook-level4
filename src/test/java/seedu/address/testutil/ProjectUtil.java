package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FUNCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddMilestoneToCommand;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.logic.commands.AddProjectTaskToCommand;
import seedu.address.logic.commands.AddToCommand;
import seedu.address.logic.commands.AddUserStoryToCommand;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectTask;
import seedu.address.model.project.UserStory;


/**
 * A utility class for project.
 */
public class ProjectUtil {

    /**
     * Returns an add command string for adding the {@code project}.
     */
    public static String getAddProjectCommand(Project project) {
        return AddCommand.COMMAND_WORD + " " + AddProjectCommand.ADD_PROJECT_KEYWORD + " "
                + getProjectDetails(project);
    }

    /**
     * Returns the part of command string for the given {@code project}'s details.
     */
    public static String getProjectDetails(Project project) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + project.getProjectName().projectName + " ");
        sb.append(PREFIX_CLIENT + project.getClient().client + " ");
        sb.append(PREFIX_START_DATE + project.getStartDate().date + " ");
        sb.append(PREFIX_DEADLINE + project.getDeadline().date + " ");
        return sb.toString();
    }

    /**
     * Returns an add milestone to command string for adding the {@code milestone} to {@code project}.
     */
    public static String getAddMilestoneToCommand(Project project, Milestone milestone) {
        return AddToCommand.COMMAND_WORD + " " + project.getProjectName().projectName + " "
            + AddMilestoneToCommand.ADD_MILESTONE_KEYWORD + " " + getMilestoneDetails(milestone);
    }

    /**
     * Returns an add user story to command string for adding the {@code userstory} to {@code project}.
     */
    public static String getAddUserStoryToCommand(Project project, UserStory userStory) {
        return AddToCommand.COMMAND_WORD + " " + project.getProjectName().projectName + " "
                + AddUserStoryToCommand.ADD_USERSTORY_KEYWORD + " " + getUserStoryDetails(userStory);
    }

    /**
     * Returns an add project task to command string for adding the {@code task} to {@code milestone}.
     */
    public static String getAddProjectTaskToCommand(Project project, Index index, ProjectTask task) {
        return AddToCommand.COMMAND_WORD + " " + project.getProjectName().projectName + " "
            + AddProjectTaskToCommand.ADD_PROJECTTASK_KEYWORD + " " + getProjectTaskDetails(task, index);
    }

    /**
     * Returns the part of command string for the given {@code milestone}'s details.
     */
    public static String getMilestoneDetails(Milestone milestone) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MILESTONE);
        sb.append(milestone.getMilestoneDescription().description + " ");
        sb.append(PREFIX_DATE);
        sb.append(milestone.getDate().date + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code userstory}'s details.
     */
    public static String getUserStoryDetails(UserStory userStory) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_IMPORTANCE.toString() + userStory.getUserStoryImportance() + " ");
        sb.append(PREFIX_USER.toString() + userStory.getUserStoryUser() + " ");
        sb.append(PREFIX_FUNCTION.toString() + userStory.getUserStoryFunction() + " ");
        sb.append(PREFIX_REASON.toString() + userStory.getUserStoryReason());
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code projectTask}'s details.
     */
    public static String getProjectTaskDetails(ProjectTask task, Index index) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME.toString() + task.getTaskDescription() + " ");
        sb.append(PREFIX_MILESTONE.toString() + index.getOneBased() + " ");
        return sb.toString();
    }
}
