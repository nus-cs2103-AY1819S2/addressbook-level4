package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEmployeeToCommand;
import seedu.address.logic.commands.AddMilestoneToCommand;
import seedu.address.logic.commands.AddProjectTaskToCommand;
import seedu.address.logic.commands.AddToCommand;
import seedu.address.logic.commands.AddUserStoryToCommand;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.MilestoneDescription;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.ProjectTask;
import seedu.address.model.project.ProjectTaskDescription;
import seedu.address.model.project.UserStory;
import seedu.address.model.project.UserStoryFunction;
import seedu.address.model.project.UserStoryImportance;
import seedu.address.model.project.UserStoryReason;
import seedu.address.model.project.UserStoryUser;
import seedu.address.model.util.PocketProjectDate;

public class AddToCommandParserTest {

    private AddToCommandParser parser = new AddToCommandParser();

    @Test
    public void parse_validArgs_returnsAddToCommand() {
        assertParseSuccess(parser, "Project Apollo employee 1",
                new AddEmployeeToCommand(Index.fromOneBased(1), new ProjectName("Project Apollo")));

        assertParseSuccess(parser, "Project X employee 3",
                new AddEmployeeToCommand(Index.fromOneBased(3), new ProjectName("Project X")));

        assertParseSuccess(parser, "Project Apollo milestone m/Completed UG d/23/04/2019",
                new AddMilestoneToCommand(new ProjectName("Project Apollo"), new Milestone(new
                        MilestoneDescription("Completed UG"), new PocketProjectDate("23/04/2019"))));

        assertParseSuccess(parser, "Transformium milestone m/Completed DG Today d/25/09/2019",
                new AddMilestoneToCommand(new ProjectName("Transformium"), new Milestone(new
                    MilestoneDescription("Completed DG Today"), new PocketProjectDate("25/09/2019"))));

        assertParseSuccess(parser, "Project X userstory i/2 as a user i want to do this so that im done",
                new AddUserStoryToCommand(new ProjectName("Project X"), new UserStory(
                        new UserStoryImportance("2"), new UserStoryUser("user"), new UserStoryFunction("do this"),
                new UserStoryReason("im done"))));

        assertParseSuccess(parser, "Project Apollo projecttask n/Fix this bug m/1",
                new AddProjectTaskToCommand(new ProjectName("Project Apollo"),
                        new ProjectTask(new ProjectTaskDescription("Fix this bug")), Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        //wrong format, no keyword
        assertParseFailure(parser, "Project Apollo invalid 1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddToCommand.MESSAGE_USAGE));

        //index of employee cannot be 0
        assertParseFailure(parser, "Project Apollo employee 0",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddEmployeeToCommand.MESSAGE_USAGE));

        //index of employee cannot be non-number
        assertParseFailure(parser, "Project Apollo employee xx",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddEmployeeToCommand.MESSAGE_USAGE));

        //date should be in the format of DD/MM/YYYY
        assertParseFailure(parser, "Project Apollo milestone m/Completed d/233/04/2019",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddMilestoneToCommand.MESSAGE_USAGE));

        //importance of user story should be 1-3
        assertParseFailure(parser, "Project X userstory i/4 as a user i want to do this so that im done",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddUserStoryToCommand.MESSAGE_USAGE));

        //index of milestone should be an integer
        assertParseFailure(parser, "Apollo projecttask n/Fix this bug m/uno",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddProjectTaskToCommand.MESSAGE_USAGE));

        //index of milestone should be bigger than 0
        assertParseFailure(parser, " Project Apollo projecttask n/Fix this bug m/0",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddProjectTaskToCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgs_failure() {

        //missing project name
        assertParseFailure(parser, "employee 1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddToCommand.MESSAGE_USAGE));

        //missing keyword
        assertParseFailure(parser, "Project Apollo 1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddToCommand.MESSAGE_USAGE));

        //missing employee index
        assertParseFailure(parser, "Project Apollo employee",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddToCommand.MESSAGE_USAGE));

        //missing date
        assertParseFailure(parser, "Project Apollo milestone m/Completed",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddMilestoneToCommand.MESSAGE_USAGE));

        //empty missing description
        assertParseFailure(parser, "Project Apollo milestone d/23/04/1996",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddMilestoneToCommand.MESSAGE_USAGE));

        //missing user story
        assertParseFailure(parser, "Project Apollo userstory i/2",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddUserStoryToCommand.MESSAGE_USAGE));

        //missing milestone for add task to project command
        assertParseFailure(parser, "Project Apollo projecttask n/Fix this bug",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddProjectTaskToCommand.MESSAGE_USAGE));

        //missing task name for add task to project command
        assertParseFailure(parser, "Project Apollo projecttask m/1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddProjectTaskToCommand.MESSAGE_USAGE));

    }
}
