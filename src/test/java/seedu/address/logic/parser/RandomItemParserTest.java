package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RandomItemCommand;
import seedu.address.model.apparel.ClothingTypeValue;

// @@author PhilipPhil
public class RandomItemParserTest {
    private RandomItemParser parser = new RandomItemParser();

    @Test
    public void parse_validArgs_returnsRandomItemeCommand() {
        assertParseSuccess(parser, "TOP", new RandomItemCommand(ClothingTypeValue.TOP));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RandomItemCommand.MESSAGE_USAGE));
    }
}
