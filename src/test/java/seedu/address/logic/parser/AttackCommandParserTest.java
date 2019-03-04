package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.AttackCommand;
import seedu.address.model.cell.Coordinates;

/**
 * Test scope:
 *
 *
 */
public class AttackCommandParserTest {

    private AttackCommandParser parser = new AttackCommandParser();

    @Test
    public void parse_validArgs_returnsAttackCommand() {
        assertParseSuccess(parser,
            "a1", new AttackCommand(new Coordinates("a1")));
    }

    @Test
    public void parse_invalidColumn_throwsParseException() {
        assertParseFailure(parser,
            "$1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttackCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRow_throwsParseException() {
        assertParseFailure(parser,
            "bb", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttackCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRowColumn_throwsParseException() {
        assertParseFailure(parser,
            "7c", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttackCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        assertParseFailure(
            parser, "asfdafdgar", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttackCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser,
            "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttackCommand.MESSAGE_USAGE));
    }
}
