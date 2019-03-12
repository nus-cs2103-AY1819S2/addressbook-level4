package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AlarmCommand;

public class AlarmCommandParserTest {

    private AlarmCommandParser parser = new AlarmCommandParser();

    @Test
    public void parse_validArguments_returnAlarmCommand() {
        assertParseSuccess(parser, "root\\TCM\\MCT 40",
                new AlarmCommand(new String[] {"root", "TCM", "MCT"}, 40));
    }

    @Test
    public void parse_invalidArgument_throwParserException() {
        assertParseFailure(parser, "root",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AlarmCommand.MESSAGE_USAGE));
    }
}
