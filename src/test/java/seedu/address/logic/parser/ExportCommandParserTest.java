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

    /**
     * Parses the given filename, type and range, then compares them to the expected result.
     * @param filename The file name
     * @param type The file type
     * @param range The index range to parse
     * @param expectedHash The expected parsed index range
     */
    private void parse_validArgs(String filename, String type, String range, HashSet<Integer> expectedHash) {
        File test = new File("data/" + filename + "." + type);
        try {
            ParsedInOut expected;
            if (range.equals("all")) {
                expected = new ParsedInOut(test, type);
            } else {
                expected = new ParsedInOut(test, type, expectedHash);
            }
            ParsedInOut actual = parser.parse(" " + filename + "." + type + " " + range).getParsedInOut();
            assertEquals(actual.getFile(), expected.getFile());
            assertEquals(actual.getType(), expected.getType());
            assertEquals(actual.getArgIsAll(), expected.getArgIsAll());
            if (range.equals("all")) {
                assertEquals(actual.getParsedIndex().isEmpty(), expected.getParsedIndex().isEmpty());
            } else {
                HashSet<Integer> actualHash = actual.getParsedIndex();
                for (Integer i : actualHash) {
                    assertTrue(expectedHash.contains(i));
                }
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_validArgs_returnsExportCommandSimple() {
        parse_validArgs("test", "json", "1",
                new HashSet<>(Arrays.asList(1 - 1)));
    }

    @Test
    public void parse_validArgs_returnsExportCommandPermittedSpecialCharacters() {
        parse_validArgs("test ! @ # $ % ^ & ( ) _ + - = { } [ ] ; ' , .json", "json", "0",
                new HashSet<>(Arrays.asList(0 - 1)));
    }

    @Test
    public void parse_validArgs_returnsExportCommandPdf() {
        parse_validArgs("test", "pdf", "1",
                new HashSet<>(Arrays.asList(1 - 1)));
    }

    @Test
    public void parse_validArgs_returnsExportCommandIndexOutOfBound() {
        parse_validArgs("test", "json", "0",
                new HashSet<>(Arrays.asList(0 - 1)));
    }

    @Test
    public void parse_validArgs_returnsExportCommandForwardSlash() {
        File test = new File("data/testfolder/test.json");
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
        File test = new File("data/testfolder/test.json");
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
        File test = new File("data/testfolder/test.json");
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
        File test = new File("data/testfolder///////////////////test.json");
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
        parse_validArgs("test", "json", "1,3",
                new HashSet<>(Arrays.asList(1 - 1, 3 - 1)));
    }

    @Test
    public void parse_validArgs_returnsExportCommand1Dash3() {
        parse_validArgs("test", "json", "1-3",
                new HashSet<>(Arrays.asList(1 - 1, 2 - 1, 3 - 1)));
    }

    @Test
    public void parse_validArgs_returnsExportCommand1Comma3Comma5() {
        parse_validArgs("test", "json", "1,3,5",
                new HashSet<>(Arrays.asList(1 - 1, 3 - 1, 5 - 1)));
    }


    @Test
    public void parse_validArgs_returnsExportCommand1Comma3Dash5() {
        parse_validArgs("test", "json", "1,3-5",
                new HashSet<>(Arrays.asList(1 - 1, 3 - 1, 4 - 1, 5 - 1)));
    }

    @Test
    public void parse_validArgs_returnsExportCommandAll() {
        parse_validArgs("test", "json", "all", new HashSet<>());
    }


    @Test
    public void parse_validArgs_returnsExportCommandEmptyFilename() {
        parse_validArgs("", "json", "all", new HashSet<>());
    }

    @Test
    public void parse_invalidArgsExportCommandForbiddenSpecialCharacters() {
        assertParseFailure(parser, " test < > : \" | ? *.json 1",
                "Special characters such as\n> < : \" | ? *\nare not allowed."
                        + "\n" + ExportCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidArgsNotJsonOrPdf() {
        assertParseFailure(parser, " records.txt 1",
                ParserUtil.MESSAGE_NOT_JSON_OR_PDF + "\n" + ExportCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidArgsDoubleDash() {
        assertParseFailure(parser, " records.json 1--3",
                ParserUtil.MESSAGE_INVALID_INDEX_RANGE + "\n" + ExportCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidArgsDoubleComma() {
        assertParseFailure(parser, " records.json 1,,3",
                ParserUtil.MESSAGE_INVALID_INDEX_RANGE + "\n" + ExportCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidArgsCommaDashMash() {
        assertParseFailure(parser, " records.json 1,-,3",
                ParserUtil.MESSAGE_INVALID_INDEX_RANGE + "\n" + ExportCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidArgsDashChain() {
        assertParseFailure(parser, " records.json 1-3-5",
                ParserUtil.MESSAGE_INVALID_INDEX_RANGE + "\n" + ExportCommand.MESSAGE_USAGE);
    }
}
