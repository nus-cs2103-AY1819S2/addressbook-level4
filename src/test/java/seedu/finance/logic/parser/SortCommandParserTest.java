package seedu.finance.logic.parser;

import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.finance.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", SortCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "-name", new SortCommand(new RecordNameComparator()));
        assertParseSuccess(parser, "-amount", new SortCommand(new RecordAmountComparator()));
        assertParseSuccess(parser, "-date", new SortCommand(new RecordDateComparator()));
        assertParseSuccess(parser, "-cat", new SortCommand(new RecordCategoryComparator()));
    }

    @Test
    public void parse_multipleFlags_throwsParseException() {
        assertParseFailure(parser, "-name -amount", SortCommand.MESSAGE_NOT_SORTED);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-description", SortCommand.MESSAGE_USAGE);
    }
}
