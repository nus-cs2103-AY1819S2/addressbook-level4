package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.DeleteAppCommandParser.PREFIX_DATE;
import static seedu.address.logic.parser.DeleteAppCommandParser.PREFIX_START;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import seedu.address.logic.commands.DeleteAppCommand;

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
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " \n",
                new DeleteAppCommand(date, start));
    }

    @Test
    public void parse_compulsoryPrefixMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppCommand.MESSAGE_USAGE);

        // missing date prefix
        assertParseFailure(parser,
                dateString + " "
                        + PREFIX_START + startString,
                expectedMessage);

        // missing start prefix
        assertParseFailure(parser,
                PREFIX_DATE + dateString + " "
                        + startString,
                expectedMessage);

        // all prefix missing
        assertParseFailure(parser,
                dateString + " "
                        + startString,
                expectedMessage);
    }

}
