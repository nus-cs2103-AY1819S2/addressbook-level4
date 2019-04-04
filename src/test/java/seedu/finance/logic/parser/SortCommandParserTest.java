package seedu.finance.logic.parser;

import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_NAME;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.finance.logic.commands.SortCommand;
import seedu.finance.logic.parser.comparator.RecordAmountComparator;
import seedu.finance.logic.parser.comparator.RecordCategoryComparator;
import seedu.finance.logic.parser.comparator.RecordDateComparator;
import seedu.finance.logic.parser.comparator.RecordNameComparator;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", SortCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, COMMAND_FLAG_NAME.getFlag(), new SortCommand(new RecordNameComparator()));
        assertParseSuccess(parser, COMMAND_FLAG_AMOUNT.getFlag(), new SortCommand(new RecordAmountComparator()));
        assertParseSuccess(parser, COMMAND_FLAG_DATE.getFlag(), new SortCommand(new RecordDateComparator()));
        assertParseSuccess(parser, COMMAND_FLAG_CATEGORY.getFlag(), new SortCommand(new RecordCategoryComparator()));
    }

    @Test
    public void parse_multipleFlags_throwsParseException() {
        assertParseFailure(parser, COMMAND_FLAG_NAME + " " + COMMAND_FLAG_AMOUNT, SortCommand.MESSAGE_NOT_SORTED);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-description", SortCommand.MESSAGE_USAGE);
    }
}
