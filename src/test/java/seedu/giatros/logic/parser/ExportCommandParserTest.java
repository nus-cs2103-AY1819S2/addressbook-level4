package seedu.giatros.logic.parser;

import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_PATH;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_PATH;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.giatros.commons.core.EventsCenter;
import seedu.giatros.commons.events.ui.accounts.LoginEvent;
import seedu.giatros.logic.commands.ExportCommand;
import seedu.giatros.ui.testutil.AccountCreator;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @BeforeClass
    public static void setupBeforeClass() {
        EventsCenter.getInstance().post(new LoginEvent(new AccountCreator("staff").build()));
    }

    @Test
    public void parse_invalid_throwsParseException() {
        // invalid dest
        assertParseFailure(parser, PREFIX_WITH_INVALID_PATH, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.INVALID_PATH));

        // invalid arg
        assertParseFailure(parser, " 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));


        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PREFIX_WITH_VALID_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

    }
}
