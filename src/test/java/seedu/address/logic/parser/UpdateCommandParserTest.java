package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateProjectTaskCommand;
import seedu.address.logic.commands.UpdateUserStoryCommand;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.Status;

public class UpdateCommandParserTest {

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_validArgs_returnsUpdateCommand() {
        assertParseSuccess(parser, "Project Apollo userstory 1 ongoing",
                new UpdateUserStoryCommand(new ProjectName("Project Apollo"), Index.fromOneBased(1),
                        new Status("ongoing")));
        assertParseSuccess(parser, "Project X userstory 1 on hold",
                new UpdateUserStoryCommand(new ProjectName("Project X"), Index.fromOneBased(1),
                        new Status("on hold")));
        assertParseSuccess(parser, "Project Apollo projecttask 1 1 Complete",
                new UpdateProjectTaskCommand(new ProjectName("Project Apollo"), Index.fromOneBased(1),
                        Index.fromOneBased(1), new Status("complete")));
        assertParseSuccess(parser, "Project XYZ projecttask 1 2 on hold",
                new UpdateProjectTaskCommand(new ProjectName("Project XYZ"), Index.fromOneBased(1),
                        Index.fromOneBased(2), new Status("on hold")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "Project Apollo invalid 1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE)); //wrong format
        assertParseFailure(parser, "Project Apollo userstory 1 invalid",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        UpdateUserStoryCommand.MESSAGE_USAGE)); //wrong status
        assertParseFailure(parser, "Apollo projecttask 1 2 invalid",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        UpdateProjectTaskCommand.MESSAGE_USAGE)); //wrong status
        assertParseFailure(parser, "Apollo projecttask 1 0 complete",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        UpdateProjectTaskCommand.MESSAGE_USAGE)); //invalid index

    }
}
