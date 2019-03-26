package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ListAppCommandParser.FORMAT_DAY;
import static seedu.address.logic.parser.ListAppCommandParser.FORMAT_MONTH;
import static seedu.address.logic.parser.ListAppCommandParser.FORMAT_WEEK;
import static seedu.address.logic.parser.ListAppCommandParser.PREFIX_DATE;
import static seedu.address.logic.parser.ListAppCommandParser.PREFIX_FORMAT;
import static seedu.address.logic.parser.ListAppCommandParser.PREFIX_NRIC;

import java.time.LocalDate;

import org.junit.Test;

import seedu.address.logic.commands.ListAppCommand;
import seedu.address.model.patient.Nric;

public class ListAppCommandParserTest {
    private ListAppCommandParser parser = new ListAppCommandParser();

    private String formatStringDay = FORMAT_DAY;
    private String formatStringWeek = FORMAT_WEEK;
    private String formatStringMonth = FORMAT_MONTH;
    private String nricString = "S9234568C";
    private String dateString = "2019-10-23";

    private Nric nric = new Nric(nricString);
    private LocalDate date = LocalDate.parse(dateString);

    @Test
    public void parse_noFieldsPresent_success() {
        assertParseSuccess(parser, "", new ListAppCommand());
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
                PREFIX_FORMAT + formatStringDay + " "
                        + dateString,
                expectedMessage);

        // missing date and format prefix
        assertParseFailure(parser,
                formatStringDay + " "
                        + dateString,
                expectedMessage);

        // missing nric prefix
        assertParseFailure(parser, nricString,
                expectedMessage);
    }

    @Test
    public void parse_invalidFormatInput_failure() {
        String invalidFormat = "year";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                "Valid keywords for FORMATS: "
                        + FORMAT_DAY + ", "
                        + FORMAT_WEEK + ", "
                        + FORMAT_MONTH);

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
