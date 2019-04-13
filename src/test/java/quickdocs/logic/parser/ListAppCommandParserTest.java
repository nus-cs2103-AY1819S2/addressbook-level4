package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static quickdocs.logic.parser.ListAppCommandParser.PREFIX_DATE;
import static quickdocs.logic.parser.ListAppCommandParser.PREFIX_FORMAT;
import static quickdocs.logic.parser.ListAppCommandParser.PREFIX_NRIC;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import quickdocs.logic.commands.ListAppCommand;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.patient.Nric;

/**
 * Contains unit tests for {@code ListAppCommandParser}.
 */
public class ListAppCommandParserTest {
    private ListAppCommandParser parser = new ListAppCommandParser();

    private String formatStringDay = ParserUtil.FORMAT_DAY;
    private String formatStringWeek = ParserUtil.FORMAT_WEEK;
    private String formatStringMonth = ParserUtil.FORMAT_MONTH;
    private String nricString = "S9234568C";
    private String dateString = "2019-10-23";

    private Nric nric = new Nric(nricString);
    private LocalDate date = LocalDate.parse(dateString);

    @Test
    public void parse_noFieldsPresent_success() throws ParseException {
        List<LocalDate> dates = ParserUtil.parseFormatDate(ParserUtil.FORMAT_WEEK, LocalDate.now());
        assertParseSuccess(parser, "", new ListAppCommand(dates.get(0), dates.get(1)));
    }

    @Test
    public void parse_allRequiredFieldsPresent_success() {
        // whitespace only preamble
        // list appointments by given search range
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringDay + " "
                        + PREFIX_DATE + dateString,
                new ListAppCommand(date, date));

        // list appointments for given patient's nric
        assertParseSuccess(parser,
                "              "
                        + PREFIX_NRIC + nricString,
                new ListAppCommand(nric));
    }

    @Test
    public void parse_allPrefixPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppCommand.MESSAGE_USAGE);

        // all prefixes included
        assertParseFailure(parser,
                PREFIX_FORMAT + formatStringDay + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_NRIC + nricString,
                expectedMessage);
    }

    @Test
    public void parse_compulsoryPrefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppCommand.MESSAGE_USAGE);

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

        // missing nric prefix
        assertParseFailure(parser, nricString,
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
                new ListAppCommand(start, end));

        // format == week
        start = LocalDate.parse("2019-10-21");
        end = LocalDate.parse("2019-10-27");
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringWeek + " "
                        + PREFIX_DATE + dateString,
                new ListAppCommand(start, end));

        // format == week
        start = LocalDate.parse("2019-10-01");
        end = LocalDate.parse("2019-10-31");
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringMonth + " "
                        + PREFIX_DATE + dateString,
                new ListAppCommand(start, end));
    }
}
