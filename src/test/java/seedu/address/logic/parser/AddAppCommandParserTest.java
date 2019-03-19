package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_COMMENT;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_DATE;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_END;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_NRIC;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_START;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import seedu.address.logic.commands.AddAppCommand;
import seedu.address.model.patient.Nric;

public class AddAppCommandParserTest {
    private AddAppCommandParser parser = new AddAppCommandParser();

    private String nricString = "S9234568C";
    private String dateString = "2019-10-23";
    private String startString = "16:00";
    private String endString = "17:00";

    private Nric nric = new Nric(nricString);
    private LocalDate date = LocalDate.parse(dateString);
    private LocalTime start = LocalTime.parse(startString);
    private LocalTime end = LocalTime.parse(endString);

    private String comment = "This is a comment";

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser,
                "              "
                        + PREFIX_NRIC + nricString + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment,
                new AddAppCommand(nric, date, start, end, comment));
    }

    @Test
    public void parse_compulsoryPrefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE);

        // missing nric prefix
        assertParseFailure(parser,
                nricString + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser,
                PREFIX_NRIC + nricString + " "
                        + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing start prefix
        assertParseFailure(parser,
                PREFIX_NRIC + nricString + " "
                        + PREFIX_DATE + dateString + " "
                        + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing end prefix
        assertParseFailure(parser,
                PREFIX_NRIC + nricString + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing comment prefix
        assertParseFailure(parser,
                PREFIX_NRIC + nricString + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + comment,
                expectedMessage);

        // all prefix missing
        assertParseFailure(parser,
                nricString + " "
                        + dateString + " "
                        + startString + " "
                        + endString + " "
                        + comment,
                expectedMessage);
    }
}
