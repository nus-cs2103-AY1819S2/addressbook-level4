package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndices.INDEX_FIRST_LESSON;

import org.junit.Test;

import seedu.address.logic.commands.management.OpenLessonCommand;

public class OpenLessonParserTest {

    private OpenLessonParser parser = new OpenLessonParser();

    @Test
    public void parse_validArgs_returnsOpenCommand() {
        assertParseSuccess(parser, "" + INDEX_FIRST_LESSON.getOneBased(),
                new OpenLessonCommand(INDEX_FIRST_LESSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenLessonCommand.MESSAGE_USAGE));
    }
}
