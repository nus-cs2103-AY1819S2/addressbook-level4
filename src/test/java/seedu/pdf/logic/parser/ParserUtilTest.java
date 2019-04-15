package seedu.pdf.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.pdf.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_FIRST_PDF;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.pdf.logic.parser.exceptions.ParseException;
import seedu.pdf.model.pdf.Deadline;
import seedu.pdf.model.pdf.Directory;
import seedu.pdf.model.pdf.Name;
import seedu.pdf.model.tag.Tag;
import seedu.pdf.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "invalidPdfName";
    private static final String INVALID_FILE = "invalid\\path\\abc.pdf";
    private static final String INVALID_DIRECTORY = "invalid\\path";
    private static final String INVALID_DEADLINE = "22-22-2020";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "validPdfName.pdf";
    private static final String VALID_FILE = Paths.get("src", "test", "data",
            "SampleFiles", "NormalFiles", "GitCheatSheet.pdf").toAbsolutePath().toString();
    private static final String VALID_DIRECTORY = Paths.get("src", "test", "data",
            "SampleFiles", "NormalFiles").toAbsolutePath().toString();
    private static final String VALID_DEADLINE_JSON = "2020-11-11/false";
    private static final String VALID_DEADLINE_INPUT = "11-11-2020";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PDF, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PDF, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseFile_invalidValue_throwsParserException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseFile(INVALID_FILE));
    }

    @Test
    public void parseFile_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseFile((String) null));
    }

    @Test
    public void parseFile_validValueWithoutWhitespace_returnsFile() throws Exception {
        File expectedFile = new File(VALID_FILE);
        assertEquals(expectedFile, ParserUtil.parseFile(VALID_FILE));
    }

    @Test
    public void parseFile_validValueWithWhitespace_returnsTrimmedFile() throws Exception {
        String fileWithWhitespace = WHITESPACE + VALID_FILE + WHITESPACE;
        File expectedFile = new File(VALID_FILE);
        assertEquals(expectedFile, ParserUtil.parseFile(fileWithWhitespace));
    }

    @Test
    public void parseDirectory_invalidValue_throwsParserException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDirectory(INVALID_DIRECTORY));
    }

    @Test
    public void parseDirectory_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDirectory((String) null));
    }

    @Test
    public void parseDirectory_validValueWithoutWhitespace_returnsFile() throws Exception {
        Directory expectedFile = new Directory(VALID_DIRECTORY);
        assertEquals(expectedFile, ParserUtil.parseDirectory(VALID_DIRECTORY));
    }

    @Test
    public void parseDirectory_validValueWithWhitespace_returnsTrimmedFile() throws Exception {
        String directoryWithWhitespace = WHITESPACE + VALID_DIRECTORY + WHITESPACE;
        Directory expectedDirectory = new Directory(VALID_DIRECTORY);
        assertEquals(expectedDirectory, ParserUtil.parseDirectory(directoryWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseDeadline_invalidValue_throwsParserException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDeadline(INVALID_DEADLINE));
    }

    @Test
    public void parseDeadline_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDeadline((String) null));
    }

    @Test
    public void parseDeadline_validValueWithoutWhitespace_returnsFile() throws Exception {
        Deadline expectedDeadline = new Deadline(VALID_DEADLINE_JSON);
        assertEquals(expectedDeadline, ParserUtil.parseDeadline(VALID_DEADLINE_INPUT));
    }

    @Test
    public void parseDeadline_validValueWithWhitespace_returnsTrimmedFile() throws Exception {
        String deadlineWithWhitespace = WHITESPACE + VALID_DEADLINE_INPUT + WHITESPACE;
        Deadline expectedDeadline = new Deadline(VALID_DEADLINE_JSON);
        assertEquals(expectedDeadline, ParserUtil.parseDeadline(deadlineWithWhitespace));
    }
}
