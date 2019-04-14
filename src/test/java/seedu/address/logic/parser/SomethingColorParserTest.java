package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SomethingColorCommand;
import seedu.address.model.apparel.ColorValue;




// @@author PhilipPhil
public class SomethingColorParserTest {
    private SomethingColorParser parser = new SomethingColorParser();

    @Test
    public void parse_validArgs_returnsRandomItemeCommand() {
        assertParseSuccess(parser, "RED", new SomethingColorCommand(ColorValue.RED));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SomethingColorCommand.MESSAGE_USAGE));
    }
}
