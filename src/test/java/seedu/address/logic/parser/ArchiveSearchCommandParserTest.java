package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ArchiveSearchCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class ArchiveSearchCommandParserTest {

    private ArchiveSearchCommandParser parser = new ArchiveSearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ArchiveSearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsArchiveSearchCommand() {
        // no leading and trailing whitespaces
        ArchiveSearchCommand expectedArchiveSearchCommand =
                new ArchiveSearchCommand(new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedArchiveSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedArchiveSearchCommand);
    }

}
