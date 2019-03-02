package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.AddRemCommandParser.PREFIX_COMMENT;
import static seedu.address.logic.parser.AddRemCommandParser.PREFIX_DATE;
import static seedu.address.logic.parser.AddRemCommandParser.PREFIX_END;
import static seedu.address.logic.parser.AddRemCommandParser.PREFIX_START;
import static seedu.address.logic.parser.AddRemCommandParser.PREFIX_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import seedu.address.logic.commands.AddRemCommand;
import seedu.address.model.reminder.Reminder;

public class AddRemCommandParserTest {
    private AddRemCommandParser parser = new AddRemCommandParser();

    private String title = "Refill Medicine ABC";
    private String comment = "This is a comment";
    private String dateString = "2019-05-22";
    private String startString = "13:00";
    private String endString = "14:00";

    private LocalDate date = LocalDate.parse(dateString);
    private LocalTime start = LocalTime.parse(startString);
    private LocalTime end = LocalTime.parse(endString);
    private Reminder toAdd = new Reminder(title, comment, date, start, end);

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser,
                "              "
                        + PREFIX_TITLE + title + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment,
                new AddRemCommand(toAdd));
    }

    @Test
    public void parse_compulsoryPrefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemCommand.MESSAGE_USAGE);

        // missing index prefix
        assertParseFailure(parser,
                title + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser,
                PREFIX_TITLE + title + " "
                        + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing start prefix
        assertParseFailure(parser,
                PREFIX_TITLE + title + " "
                        + PREFIX_DATE + dateString + " "
                        + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing end prefix
        assertParseFailure(parser,
                PREFIX_TITLE + title + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing comment prefix
        assertParseFailure(parser,
                PREFIX_TITLE + title + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + comment,
                expectedMessage);

        // all prefix missing
        assertParseFailure(parser,
                title + " "
                        + dateString + " "
                        + startString + " "
                        + endString + " "
                        + comment,
                expectedMessage);
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        // missing endTime
        toAdd = new Reminder(title, comment, date, start, null);
        assertParseSuccess(parser,
                " " + PREFIX_TITLE + title + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_COMMENT + comment,
                new AddRemCommand(toAdd));

        // missing comment
        toAdd = new Reminder(title, null, date, start, end);
        assertParseSuccess(parser,
                " " + PREFIX_TITLE + title + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString,
                new AddRemCommand(toAdd));

        // missing endTime and comment
        toAdd = new Reminder(title, null, date, start, null);
        assertParseSuccess(parser,
                " " + PREFIX_TITLE + title + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString,
                new AddRemCommand(toAdd));
    }
}
