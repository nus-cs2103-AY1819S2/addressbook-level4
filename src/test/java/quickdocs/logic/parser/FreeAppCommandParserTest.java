package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static quickdocs.logic.parser.FreeAppCommandParser.PREFIX_DATE;
import static quickdocs.logic.parser.FreeAppCommandParser.PREFIX_FORMAT;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import quickdocs.logic.commands.FreeAppCommand;
import quickdocs.logic.parser.exceptions.ParseException;

/**
 * Contains unit tests for {@code FreeAppCommandParser}.
 */
public class FreeAppCommandParserTest {
    private FreeAppCommandParser parser = new FreeAppCommandParser();

    private String formatStringDay = ParserUtil.FORMAT_DAY;
    private String formatStringWeek = ParserUtil.FORMAT_WEEK;
    private String formatStringMonth = ParserUtil.FORMAT_MONTH;
    private String dateString = "2019-10-23";

    private LocalDate date = LocalDate.parse(dateString);

    @Test
    public void parse_noFieldsPresent_success() throws ParseException {
        LocalDate nextMonthDate = LocalDate.now().plusMonths(1);
        List<LocalDate> dates = ParserUtil.parseFormatDate(ParserUtil.FORMAT_MONTH, nextMonthDate);
        assertParseSuccess(parser, "", new FreeAppCommand(dates.get(0), dates.get(1)));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        // list free appointment slots by given search range
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringDay + " "
                        + PREFIX_DATE + dateString,
                new FreeAppCommand(date, date));
    }

    @Test
    public void parse_compulsoryPrefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeAppCommand.MESSAGE_USAGE);

        // missing format prefix
        assertParseFailure(parser,
                formatStringDay + " "
                        + PREFIX_DATE + dateString,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser,
                PREFIX_FORMAT + formatStringWeek + " "
                        + dateString,
                expectedMessage);

        // missing date and format prefix
        assertParseFailure(parser,
                formatStringMonth + " "
                        + dateString,
                expectedMessage);
    }

    @Test
    public void parse_invalidFormatInput_failure() {
        String invalidFormat = "year";
        String expectedMessage = ParserUtil.MESSAGE_INVALID_FORMAT;

        // all prefixes included
        assertParseFailure(parser,
                "              "
                        + PREFIX_FORMAT + invalidFormat + " "
                        + PREFIX_DATE + dateString,
                expectedMessage);
    }

    @Test
    public void parse_formatDateRange_success() {
        // format == day
        LocalDate start = LocalDate.parse("2019-10-23");
        LocalDate end = LocalDate.parse("2019-10-23");
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringDay + " "
                        + PREFIX_DATE + dateString,
                new FreeAppCommand(start, end));

        // format == week
        start = LocalDate.parse("2019-10-21");
        end = LocalDate.parse("2019-10-27");
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringWeek + " "
                        + PREFIX_DATE + dateString,
                new FreeAppCommand(start, end));

        // format == week
        start = LocalDate.parse("2019-10-01");
        end = LocalDate.parse("2019-10-31");
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringMonth + " "
                        + PREFIX_DATE + dateString,
                new FreeAppCommand(start, end));
    }
}
