package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import quickdocs.logic.commands.AddDirectoryCommand;

public class AddDirectoryCommandParseTest {

    private AddDirectoryCommandParser parser = new AddDirectoryCommandParser();

    @Test
    public void validArgument_returnsAddDirectoryCommand() {
        assertParseSuccess(parser, "root\\test1\\test2 test3",
                new AddDirectoryCommand(new String[] {"root", "test1", "test2"}, "test3"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDirectoryCommand.MESSAGE_USAGE));
    }

    @Test
    public void invalidArgument_throwsParseException() {
        assertParseFailure(parser, "root\\test1 test\\1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDirectoryCommand.MESSAGE_USAGE));
    }
}
