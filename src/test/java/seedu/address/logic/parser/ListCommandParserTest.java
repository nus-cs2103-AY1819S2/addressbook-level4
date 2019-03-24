package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListCommandParser;
import seedu.address.logic.commands.ListHealthWorkerCommand;
import seedu.address.logic.commands.request.ListRequestCommand;

public class ListCommandParserTest {

    public static final String INVALID_COMMAND_FORMAT = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            ListCommand.MESSAGE_UAGE);

    ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_invalidCommandMode() {
        // invalid command mode
        assertParseFailure(parser, "invalid", INVALID_COMMAND_FORMAT);

        // no command mode
        assertParseFailure(parser, "", INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_validCommandMode() {
        // list health workers
        assertParseSuccess(parser, "healthworker", new ListHealthWorkerCommand());

        // list health workers - alternative mode
        assertParseSuccess(parser, "h", new ListHealthWorkerCommand());

        // list requests
        assertParseSuccess(parser, "request", new ListRequestCommand());

        // list requests - alternative mode
        assertParseSuccess(parser, "r", new ListRequestCommand());
    }

}
