package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;
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
    public void parse_validArgs_returnsExportCommandSimple() {
        File test = new File("data" + File.separator + "test.json");
        try {
            HashSet<Integer> expectedHash = new HashSet<>();
            expectedHash.add(1 - 1);
            ParsedInOut expected = new ParsedInOut(test, "json", expectedHash);
            ParsedInOut actual = parser.parse(" test.json 1").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validArgs_returnsExportCommandPermittedSpecialCharacters() {
        File test = new File("data" + File.separator + "test ! @ # $ % ^ & ( ) _ + - = { } [ ] ; ' , .json");
        try {
            HashSet<Integer> expectedHash = new HashSet<>();
            expectedHash.add(1 - 1);
            ParsedInOut expected = new ParsedInOut(test, "json", expectedHash);
            ParsedInOut actual = parser.parse(" test ! @ # $ % ^ & ( ) _ + - = { } [ ] ; ' , .json 1")
                .getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_invalidArgs_returnsExportCommandForbiddenSpecialCharacters() {
        assertParseFailure(parser, " test < > : \" | ? *.json 1",
            "Special characters such as\n> < : \" | ? *\nare not allowed."
                + "\n" + ExportCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsExportCommandPdf() {
        File test = new File("data" + File.separator + "test.pdf");
        try {
            HashSet<Integer> expectedHash = new HashSet<>();
            expectedHash.add(1 - 1);
            ParsedInOut expected = new ParsedInOut(test, "pdf", expectedHash);
            ParsedInOut actual = parser.parse(" test.pdf 1").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validArgs_returnsExportCommandIndexOutOfBound() {
        File test = new File("data" + File.separator + "test.json");
        try {
            HashSet<Integer> expectedHash = new HashSet<>();
            expectedHash.add(0 - 1);
            ParsedInOut expected = new ParsedInOut(test, "json", expectedHash);
            ParsedInOut actual = parser.parse(" test.json 0").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        File test = new File("data" + File.separator + "test.pdf");
        assertParseFailure(parser, " records.txt 1",
            ParserUtil.MESSAGE_NOT_JSON_OR_PDF + "\n" + ExportCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsExportCommandForwardSlash() {
        File test = new File("data" + File.separator + "testfolder" + File.separator + "test.json");
        try {
            HashSet<Integer> expectedHash = new HashSet<>();
            expectedHash.add(1 - 1);
            ParsedInOut expected = new ParsedInOut(test, "json", expectedHash);
            ParsedInOut actual = parser.parse(" testfolder/test.json 1").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validArgs_returnsExportCommandBackwardSlash() {
        File test = new File("data" + File.separator + "testfolder" + File.separator + "test.json");
        try {
            HashSet<Integer> expectedHash = new HashSet<>();
            expectedHash.add(1 - 1);
            ParsedInOut expected = new ParsedInOut(test, "json", expectedHash);
            ParsedInOut actual = parser.parse(" testfolder\\test.json 1").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validArgs_returnsExportCommandBothSlash() {
        File test = new File("data" + File.separator + File.separator
            + "testfolder" + File.separator + "test.json");
        try {
            HashSet<Integer> expectedHash = new HashSet<>();
            expectedHash.add(1 - 1);
            ParsedInOut expected = new ParsedInOut(test, "json", expectedHash);
            ParsedInOut actual = parser.parse(" /testfolder\\test.json 1").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validArgs_returnsExportCommandMultipleSlash() {
        File test = new File("data" + File.separator + "testfolder" + File.separator + File.separator
            + File.separator + File.separator + File.separator + File.separator + File.separator + File.separator
            + File.separator + File.separator + File.separator + File.separator + File.separator + File.separator
            + File.separator + File.separator + File.separator + File.separator + File.separator + "test.json");
        try {
            HashSet<Integer> expectedHash = new HashSet<>();
            expectedHash.add(1 - 1);
            ParsedInOut expected = new ParsedInOut(test, "json", expectedHash);
            ParsedInOut actual = parser.parse(" testfolder///////////////////test.json 1").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validArgs_returnsExportCommand1Comma3() {
        File test = new File("data" + File.separator + "test.json");
        try {
            HashSet<Integer> expectedHash = new HashSet<>(Arrays.asList(1 - 1, 3 - 1));
            ParsedInOut expected = new ParsedInOut(test, "json", expectedHash);
            ParsedInOut actual = parser.parse(" test.json 1,3").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validArgs_returnsExportCommand1Dash3() {
        File test = new File("data" + File.separator + "test.json");
        try {
            HashSet<Integer> expectedHash = new HashSet<>(Arrays.asList(1 - 1, 2 - 1, 3 - 1));
            ParsedInOut expected = new ParsedInOut(test, "json", expectedHash);
            ParsedInOut actual = parser.parse(" test.json 1-3").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validArgs_returnsExportCommand1Comma3Comma5() {
        File test = new File("data" + File.separator + "test.json");
        try {
            HashSet<Integer> expectedHash = new HashSet<>(Arrays.asList(1 - 1, 3 - 1, 5 - 1));
            ParsedInOut expected = new ParsedInOut(test, "json", expectedHash);
            ParsedInOut actual = parser.parse(" test.json 1,3,5").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validArgs_returnsExportCommand1Comma3Dash5() {
        File test = new File("data" + File.separator + "test.json");
        try {
            HashSet<Integer> expectedHash = new HashSet<>(Arrays.asList(1 - 1, 3 - 1, 4 - 1, 5 - 1));
            ParsedInOut expected = new ParsedInOut(test, "json", expectedHash);
            ParsedInOut actual = parser.parse(" test.json 1,3-5").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            HashSet<Integer> actualHash = actual.getParsedIndex();
            for (Integer i : actualHash) {
                assertTrue(expectedHash.contains(i));
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validArgs_returnsExportCommandAll() {
        File test = new File("data" + File.separator + "test.json");
        try {
            ParsedInOut expected = new ParsedInOut(test, "json");
            ParsedInOut actual = parser.parse(" test.json all").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            assertEquals(actual.getParsedIndex().isEmpty(), expected.getParsedIndex().isEmpty());
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }


    @Test
    public void parse_validArgs_returnsExportCommandEmptyFilename() {
        File test = new File("data" + File.separator + ".json");
        try {
            ParsedInOut expected = new ParsedInOut(test, "json");
            ParsedInOut actual = parser.parse(" .json all").getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            assertEquals(actual.getParsedIndex().isEmpty(), expected.getParsedIndex().isEmpty());
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
}
