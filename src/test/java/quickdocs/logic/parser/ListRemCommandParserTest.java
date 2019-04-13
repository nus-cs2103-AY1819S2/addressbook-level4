package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static quickdocs.logic.parser.ListRemCommandParser.PREFIX_DATE;
import static quickdocs.logic.parser.ListRemCommandParser.PREFIX_FORMAT;
import static quickdocs.logic.parser.ListRemCommandParser.PREFIX_INDEX;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import quickdocs.commons.core.index.Index;
import quickdocs.logic.commands.ListRemCommand;
import quickdocs.logic.parser.exceptions.ParseException;

/**
 * Contains unit tests for {@code ListRemCommandParser}.
 */
public class ListRemCommandParserTest {
    private ListRemCommandParser parser = new ListRemCommandParser();

    private String formatStringDay = ParserUtil.FORMAT_DAY;
    private String formatStringWeek = ParserUtil.FORMAT_WEEK;
    private String formatStringMonth = ParserUtil.FORMAT_MONTH;
    private String dateString = "2019-10-23";
    private String indexString = "1";
    private LocalDate date = LocalDate.parse(dateString);
    private Index targetIndex = Index.fromOneBased(1);

    @Test
    public void parse_noFieldsPresent_success() throws ParseException {
        List<LocalDate> dates = ParserUtil.parseFormatDate(ParserUtil.FORMAT_WEEK, LocalDate.now());
        assertParseSuccess(parser, "", new ListRemCommand(dates.get(0), dates.get(1)));
    }

    @Test
    public void parse_allFieldsPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRemCommand.MESSAGE_USAGE);

        assertParseFailure(parser,
                "              "
                        + PREFIX_FORMAT + formatStringDay + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_INDEX + indexString,
                expectedMessage);
    }

    @Test
    public void parse_allRequiredFieldsPresent_success() {
        // whitespace only preamble
        // list reminders by format and date
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringDay + " "
                        + PREFIX_DATE + dateString,
                new ListRemCommand(date, date));

        // list single reminder by given index
        assertParseSuccess(parser,
                "              "
                        + PREFIX_INDEX + indexString,
                new ListRemCommand(targetIndex));
    }

    @Test
    public void parse_compulsoryPrefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRemCommand.MESSAGE_USAGE);

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

        // missing index prefix
        assertParseFailure(parser, indexString,
                expectedMessage);
    }

    // Parsing for index is mainly tested in ParserUtilTest
    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, PREFIX_INDEX + "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRemCommand.MESSAGE_USAGE));
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
                new ListRemCommand(start, end));

        // format == week
        start = LocalDate.parse("2019-10-21");
        end = LocalDate.parse("2019-10-27");
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringWeek + " "
                        + PREFIX_DATE + dateString,
                new ListRemCommand(start, end));

        // format == week
        start = LocalDate.parse("2019-10-01");
        end = LocalDate.parse("2019-10-31");
        assertParseSuccess(parser,
                "              "
                        + PREFIX_FORMAT + formatStringMonth + " "
                        + PREFIX_DATE + dateString,
                new ListRemCommand(start, end));
    }
}
