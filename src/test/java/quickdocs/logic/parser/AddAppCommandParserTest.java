package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import quickdocs.logic.commands.AddAppCommand;
import quickdocs.model.patient.Nric;

/**
 * Contains unit tests for {@code AddAppCommandParser}.
 */
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
                        + AddAppCommandParser.PREFIX_NRIC + nricString + " "
                        + AddAppCommandParser.PREFIX_DATE + dateString + " "
                        + AddAppCommandParser.PREFIX_START + startString + " "
                        + AddAppCommandParser.PREFIX_END + endString + " "
                        + AddAppCommandParser.PREFIX_COMMENT + comment,
                new AddAppCommand(nric, date, start, end, comment));
    }

    @Test
    public void parse_compulsoryPrefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE);

        // missing nric prefix
        assertParseFailure(parser,
                nricString + " "
                        + AddAppCommandParser.PREFIX_DATE + dateString + " "
                        + AddAppCommandParser.PREFIX_START + startString + " "
                        + AddAppCommandParser.PREFIX_END + endString + " "
                        + AddAppCommandParser.PREFIX_COMMENT + comment,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser,
                AddAppCommandParser.PREFIX_NRIC + nricString + " "
                        + dateString + " "
                        + AddAppCommandParser.PREFIX_START + startString + " "
                        + AddAppCommandParser.PREFIX_END + endString + " "
                        + AddAppCommandParser.PREFIX_COMMENT + comment,
                expectedMessage);

        // missing start prefix
        assertParseFailure(parser,
                AddAppCommandParser.PREFIX_NRIC + nricString + " "
                        + AddAppCommandParser.PREFIX_DATE + dateString + " "
                        + startString + " "
                        + AddAppCommandParser.PREFIX_END + endString + " "
                        + AddAppCommandParser.PREFIX_COMMENT + comment,
                expectedMessage);

        // missing end prefix
        assertParseFailure(parser,
                AddAppCommandParser.PREFIX_NRIC + nricString + " "
                        + AddAppCommandParser.PREFIX_DATE + dateString + " "
                        + AddAppCommandParser.PREFIX_START + startString + " "
                        + endString + " "
                        + AddAppCommandParser.PREFIX_COMMENT + comment,
                expectedMessage);

        // missing comment prefix
        assertParseFailure(parser,
                AddAppCommandParser.PREFIX_NRIC + nricString + " "
                        + AddAppCommandParser.PREFIX_DATE + dateString + " "
                        + AddAppCommandParser.PREFIX_START + startString + " "
                        + AddAppCommandParser.PREFIX_END + endString + " "
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
