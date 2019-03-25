package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.FilterHealthWorkerCommand;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterHealthWorkerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
//        FilterHealthWorkerCommand expectedFilterHealthWorkerCommand =
//                new FilterHealthWorkerCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
//        assertParseSuccess(parser, "Alice Bob", expectedFilterHealthWorkerCommand);

        // multiple whitespaces between keywords
//        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFilterHealthWorkerCommand);
    }

}
