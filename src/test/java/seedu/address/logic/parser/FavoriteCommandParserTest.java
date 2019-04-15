package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.FavoriteCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

public class FavoriteCommandParserTest {

    private FavoriteCommandParser parser = new FavoriteCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavoriteCommand.MESSAGE_USAGE));
    }
}
