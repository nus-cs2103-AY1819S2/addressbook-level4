package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.finance.logic.commands.SummaryCommand;

public class SummaryCommandParserTest {
    private SummaryCommandParser parser = new SummaryCommandParser();

    @Test
    public void parseAllFieldsPresentSuccess() {
        assertParseSuccess(parser, " #/7 p/m", new SummaryCommand(7, "m"));
        assertParseSuccess(parser, " #/6 p/d", new SummaryCommand(6, "d"));
    }

    @Test
    public void parseNoParamsSuccess() {
        assertParseSuccess(parser, "", new SummaryCommand(7, "d"));
    }

    @Test
    public void parseFieldMissingFailure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE);

        // missing period amount
        assertParseFailure(parser, " p/m", expectedMessage);

        // missing period
        assertParseFailure(parser, " #/7", expectedMessage);
    }

    @Test
    public void parseInvalidValueFailure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SummaryCommand.MESSAGE_PARAMETERS_FORMAT);
        String expectedMessagePeriodAmount = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT,
                SummaryCommand.MESSAGE_PERIOD_AMOUNT_ERROR
        );

        // number less than 0
        assertParseFailure(parser, " #/0 p/m", expectedMessagePeriodAmount);

        // number not an int
        assertParseFailure(parser, " #/1.5 p/m", expectedMessagePeriodAmount);

        // period not d or m
        assertParseFailure(parser, " #/1 p/p", expectedMessage);

        // all params invalid
        assertParseFailure(parser, " #/0 p/p", expectedMessage);
    }
}
