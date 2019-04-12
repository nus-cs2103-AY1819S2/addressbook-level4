package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.ParsedInOut;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ExportCommand code. For example, inputs "records.json" and "1records.txt" take the
 * same path through the ExportCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommandSimple() throws IOException {
        File test = new File("data" + File.separator + "test.json");
        if (!test.exists()) {
            try {
                test.createNewFile();
            } catch (IOException e) {
                throw new IOException("Failed to create test file!");
            }
        }
        try {
            HashSet<Integer> hashSet = new HashSet<>();
            hashSet.add(1);
            assertEquals(parser.parse(" test.json 1").getFile(),
                        new ExportCommand(new ParsedInOut(test, ".json", hashSet)).getFile());
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
        test.delete();
    }

    @Test
    public void parse_validArgs_returnsExportCommandPermittedSpecialCharacters() throws IOException {
        File test = new File("data" + File.separator + "test ! @ # $ % ^ & ( ) _ + - = { } [ ] ; ' , .json");
        if (!test.exists()) {
            try {
                test.createNewFile();
            } catch (IOException e) {
                throw new IOException("Failed to create test file!");
            }
        }
        try {
            HashSet<Integer> hashSet = new HashSet<>();
            hashSet.add(1);
            assertEquals(parser.parse(" test ! @ # $ % ^ & ( ) _ + - = { } [ ] ; ' , .json 1").getFile(),
                new ExportCommand(new ParsedInOut(test, ".json", hashSet)).getFile());
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
        test.delete();
    }



    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " records.txt",
            ParserUtil.MESSAGE_NOT_JSON_OR_PDF + "\n" + ExportCommand.MESSAGE_USAGE);
    }
}
