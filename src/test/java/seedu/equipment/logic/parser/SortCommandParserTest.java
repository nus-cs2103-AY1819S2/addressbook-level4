package seedu.equipment.logic.parser;

import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.equipment.logic.parser.CliSyntax.DATE_SORT_PARAMETER;
import static seedu.equipment.logic.parser.CliSyntax.NAME_SORT_PARAMETER;
import static seedu.equipment.logic.parser.CliSyntax.PHONE_SORT_PARAMETER;
import static seedu.equipment.logic.parser.CliSyntax.SERIAL_SORT_PARAMETER;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.equipment.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.equipment.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        assertParseSuccess(parser, DATE_SORT_PARAMETER, new SortCommand(new NameComparator()));
        assertParseSuccess(parser, NAME_SORT_PARAMETER, new SortCommand(new NameComparator()));
        assertParseSuccess(parser, PHONE_SORT_PARAMETER, new SortCommand(new NameComparator()));
        assertParseSuccess(parser, SERIAL_SORT_PARAMETER, new SortCommand(new NameComparator()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " address", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

}
