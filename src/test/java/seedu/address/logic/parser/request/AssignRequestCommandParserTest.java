package seedu.address.logic.parser.request;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHWORKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUEST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import org.junit.Test;

import seedu.address.logic.commands.request.AssignRequestCommand;

public class AssignRequestCommandParserTest {
    private AssignRequestCommandParser parser = new AssignRequestCommandParser();

    @Test
    public void parse_invalidRequestIndexNonNumber_failure() {
        // invalid index
        assertParseFailure(parser, " " + PREFIX_HEALTHWORKER + "1 " + PREFIX_REQUEST + "a", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidHealthWorkerIndexNonNumber_failure() {
        // invalid index
        assertParseFailure(parser, " " + PREFIX_HEALTHWORKER + "a " + PREFIX_REQUEST + "1", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_missingHealthWorkerIndex_failure() {
        // invalid index
        assertParseFailure(parser, " " + PREFIX_REQUEST + "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AssignRequestCommand.MESSAGE_USAGE));
    }
}
