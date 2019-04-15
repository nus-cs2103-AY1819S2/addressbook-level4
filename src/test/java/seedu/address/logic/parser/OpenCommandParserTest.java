package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.io.File;

import org.junit.Test;

import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.ParsedInOut;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the OpenCommand code. For example, inputs "records.json" and "records.txt" take the
 * same path through the OpenCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class OpenCommandParserTest {

    private OpenCommandParser parser = new OpenCommandParser();

    @Test
    public void parse_validArgs_returnsOpenCommand() {
        File test = new File("data/records.json");
        try {
            assertEquals(parser.parse(" records.json").getFile(),
                            new OpenCommand(new ParsedInOut(test, ".json")).getFile());
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " records.txt",
            ParserUtil.MESSAGE_NOT_JSON_OR_PDF + "\n" + OpenCommand.MESSAGE_USAGE);
    }
}
