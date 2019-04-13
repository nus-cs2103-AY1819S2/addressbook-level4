package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_29;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MONTH_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.MONTH_DESC_3;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2019;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_1998;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_2019;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.RevenueCommand;
import seedu.address.model.statistics.Date;

import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.statistics.Year;
import seedu.address.testutil.StatisticsBuilder;

public class RevenueCommandParserTest {
    private RevenueCommandParser parser = new RevenueCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Revenue expectedRevenue = new StatisticsBuilder().withYear("2019").withMonth("3").withDay("1").build();
        System.out.println(expectedRevenue.toString());

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DAY_DESC_1 + MONTH_DESC_3 + YEAR_DESC_2019,
                new RevenueCommand(expectedRevenue));

        // multiple days - last day accepted
        assertParseSuccess(parser, DAY_DESC_29 + DAY_DESC_1 + MONTH_DESC_3 + YEAR_DESC_2019,
                new RevenueCommand(expectedRevenue));

        // multiple months - last month accepted
        assertParseSuccess(parser, DAY_DESC_1 + MONTH_DESC_2 + MONTH_DESC_3 + YEAR_DESC_2019,
                new RevenueCommand(expectedRevenue));

        // multiple years - last year accepted
        assertParseSuccess(parser, DAY_DESC_1 + MONTH_DESC_3 + YEAR_DESC_1998 + YEAR_DESC_2019,
                new RevenueCommand(expectedRevenue));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevenueCommand.MESSAGE_USAGE);

        // missing day prefix
        assertParseFailure(parser, VALID_DAY_1 + MONTH_DESC_3 + YEAR_DESC_2019, expectedMessage);

        // missing month prefix
        assertParseFailure(parser, DAY_DESC_1 + VALID_MONTH_3 + YEAR_DESC_2019, expectedMessage);

        // missing year prefix
        assertParseFailure(parser, DAY_DESC_1 + MONTH_DESC_3 + VALID_YEAR_2019, expectedMessage);

        // wrong prefix combination
        assertParseFailure(parser, DAY_DESC_1 + YEAR_DESC_2019, expectedMessage);

        // wrong prefix combination
        assertParseFailure(parser, DAY_DESC_1 + MONTH_DESC_3, expectedMessage);

        // wrong prefix combination
        assertParseFailure(parser, MONTH_DESC_3, expectedMessage);

        // wrong prefix combination
        assertParseFailure(parser, DAY_DESC_1, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid date that does not exists
        assertParseFailure(parser, DAY_DESC_29 + MONTH_DESC_2 + YEAR_DESC_2019,
                Date.MESSAGE_CONSTRAINTS);

        // invalid day that does not exists
        assertParseFailure(parser, INVALID_DAY_DESC + MONTH_DESC_2 + YEAR_DESC_2019,
                Day.MESSAGE_CONSTRAINTS);

        // invalid date that does not exists
        assertParseFailure(parser, DAY_DESC_29 + INVALID_MONTH_DESC + YEAR_DESC_2019,
                Month.MESSAGE_CONSTRAINTS);

        // invalid date that does not exists
        assertParseFailure(parser, DAY_DESC_29 + MONTH_DESC_2 + INVALID_YEAR_DESC,
                Year.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + INVALID_DAY_DESC + MONTH_DESC_3 + YEAR_DESC_2019,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevenueCommand.MESSAGE_USAGE));
    }

}
