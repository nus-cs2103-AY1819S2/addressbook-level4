package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SetCourseCommand;
import seedu.address.model.course.CourseName;

public class SetCourseCommandParserTest {
    private SetCourseCommandParser parser = new SetCourseCommandParser();

    @Test
    public void parse_validArgs_returnsSetCourseCommand() {
        assertParseSuccess(parser, "Computer Science AI",
                new SetCourseCommand(new CourseName("Computer Science AI")));
    }

    @Test
    public void parse_invalid_throwsParseException() {
        assertParseFailure(parser, "****12312Invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCourseCommand.MESSAGE_USAGE));
    }

}
