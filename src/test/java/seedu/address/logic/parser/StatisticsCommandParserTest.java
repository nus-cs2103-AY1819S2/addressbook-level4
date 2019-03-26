package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.StatisticsCommand;

class StatisticsCommandParserTest {

    private StatisticsCommandParser parser = new StatisticsCommandParser();
    private YearMonth from = YearMonth.of(2019, 1);
    private YearMonth to = YearMonth.of(2019, 1);

    @Test
    public void parse_validArgs_returnsStatisticsCommand() {
        assertParseSuccess(parser, "0119",
                new StatisticsCommand(from, to));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
    }
}
