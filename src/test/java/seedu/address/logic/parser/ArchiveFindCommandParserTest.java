package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ArchiveFindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class ArchiveFindCommandParserTest {

    private ArchiveFindCommandParser parser = new ArchiveFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsArchiveFindCommand() {
        // no leading and trailing whitespaces
        ArchiveFindCommand expectedArchiveFindCommand =
                new ArchiveFindCommand(new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedArchiveFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedArchiveFindCommand);
    }

}
