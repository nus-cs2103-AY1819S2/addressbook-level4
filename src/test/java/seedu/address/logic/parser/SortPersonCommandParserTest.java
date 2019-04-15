package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import com.sun.javafx.tools.packager.PackagerException;
import org.junit.Test;

import seedu.address.logic.commands.SortPersonCommand;
import seedu.address.logic.commands.PersonComparator;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortPersonCommandParserTest {

    private SortPersonCommandParser parser = new SortPersonCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure((Parser) parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsSortPersonCommand_req() throws ParseException {
        assertParseSuccess((Parser) parser, "name seq", new SortPersonCommand(PersonComparator.GetFunction("name"), "name", "seq"));
    }

    @Test
    public void parse_validArgs_returnsSortPersonCommand_rev() throws ParseException {
        assertParseSuccess((Parser) parser, "name rev", new SortPersonCommand(PersonComparator.GetFunction("name"), "name", "rev"));
    }
}
