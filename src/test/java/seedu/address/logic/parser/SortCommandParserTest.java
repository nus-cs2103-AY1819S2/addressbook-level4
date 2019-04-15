package seedu.address.logic.parser;

import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.options.SortOption;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SortCommandParserTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        String userInput = "name";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput)));
        userInput = "color";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput)));
        userInput = "type";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput)));
        // allow extra white space
        userInput = "name ";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput)));
        userInput = "color  ";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput)));
        userInput = "type  ";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput)));
        // allow desc parameter
        userInput = "name";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput), true));
        userInput = "color";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput), true));
        userInput = "type";
        assertParseSuccess(parser, userInput, new SortCommand(SortOption.create(userInput), true));
    }

    @Test
    public void parse_validParam_success() {
        try {
            parser.parse("name descc");
            fail( "Should fail" );
        } catch (ParseException e) {
            // nothing here
        }
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String userInput = "nameX";
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
