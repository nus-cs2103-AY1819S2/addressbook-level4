package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LISTNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.GenerateAnalyticsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AnalyticsCommandParserTest {

    private AnalyticsCommandParser parser = new AnalyticsCommandParser();

    @Test
    public void parse_invalidListName_returnsGenerateAnalyticsCommand() {
        assertParseFailure(parser, INVALID_LISTNAME, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenerateAnalyticsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_returnsGenerateAnalyticsCommand() {
        try {
            Command analytics = parser.parse(" ");
            assertTrue(analytics instanceof GenerateAnalyticsCommand);
        } catch (ParseException pe) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GenerateAnalyticsCommand.MESSAGE_USAGE), pe.getMessage());
        }
    }
}
