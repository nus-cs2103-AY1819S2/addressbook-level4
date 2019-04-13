package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndices.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndices.INDEX_SECOND;

import org.junit.Test;

import seedu.address.logic.commands.management.SetLessonTestValuesCommand;

public class SetTestParserTest {

    private SetTestParser parser = new SetTestParser();

    @Test
    public void parse_validArgs_returnsSetTestCommand() {
        assertParseSuccess(parser, "" + INDEX_FIRST.getOneBased() + " "
                        + INDEX_SECOND.getOneBased(),
                new SetLessonTestValuesCommand(INDEX_FIRST, INDEX_SECOND));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Completely wrong input
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetLessonTestValuesCommand.MESSAGE_USAGE));

        // Too few inputs, need 2 exactly
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetLessonTestValuesCommand.MESSAGE_USAGE));

        // Too many inputs, need 2 exactly
        assertParseFailure(parser, "1 2 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetLessonTestValuesCommand.MESSAGE_USAGE));

        // Inputs must be distinct
        assertParseFailure(parser, "1 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetLessonTestValuesCommand.MESSAGE_USAGE));
    }
}
