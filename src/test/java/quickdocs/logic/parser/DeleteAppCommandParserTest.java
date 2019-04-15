package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import quickdocs.logic.commands.DeleteAppCommand;

/**
 * Contains unit tests for {@code DeleteAppCommandParser}.
 */
public class DeleteAppCommandParserTest {
    private DeleteAppCommandParser parser = new DeleteAppCommandParser();

    private String dateString = "2019-03-15";
    private String startString = "09:00";

    private LocalDate date = LocalDate.parse(dateString);
    private LocalTime start = LocalTime.parse(startString);

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser,
                "              "
                        + DeleteAppCommandParser.PREFIX_DATE + dateString + " "
                        + DeleteAppCommandParser.PREFIX_START + startString + " \n",
                new DeleteAppCommand(date, start));
    }

    @Test
    public void parse_compulsoryPrefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppCommand.MESSAGE_USAGE);

        // missing date prefix
        assertParseFailure(parser,
                dateString + " "
                        + DeleteAppCommandParser.PREFIX_START + startString,
                expectedMessage);

        // missing start prefix
        assertParseFailure(parser,
                DeleteAppCommandParser.PREFIX_DATE + dateString + " "
                        + startString,
                expectedMessage);

        // all prefix missing
        assertParseFailure(parser,
                dateString + " "
                        + startString,
                expectedMessage);
    }

}
