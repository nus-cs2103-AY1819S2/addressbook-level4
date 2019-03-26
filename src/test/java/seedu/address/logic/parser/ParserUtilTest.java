package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Semester;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_MODULE_INFO_CODE = "CS123A";
    private static final String INVALID_SEMESTER = "+651234";
    private static final String INVALID_EXPECTED_MAX_GRADE = " ";
    private static final String INVALID_EXPECTED_MIN_GRADE = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_MODULE_INFO_CODE = "CS1231";
    private static final String VALID_SEMESTER = "Y2S2";
    private static final String VALID_EXPECTED_MAX_GRADE = "A_PLUS";
    private static final String VALID_EXPECTED_MIN_GRADE = "F";
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
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.moduleInfoCode((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.moduleInfoCode(INVALID_MODULE_INFO_CODE));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        ModuleInfoCode expectedName = new ModuleInfoCode(VALID_MODULE_INFO_CODE);
        assertEquals(expectedName, ParserUtil.moduleInfoCode(VALID_MODULE_INFO_CODE));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_MODULE_INFO_CODE + WHITESPACE;
        ModuleInfoCode expectedName = new ModuleInfoCode(VALID_MODULE_INFO_CODE);
        assertEquals(expectedName, ParserUtil.moduleInfoCode(nameWithWhitespace));
    }

    @Test
    public void parseSemester_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSemester((String) null));
    }

    @Test
    public void parseSemester_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseSemester(INVALID_SEMESTER));
    }

    @Test
    public void parseSemester_validValueWithoutWhitespace_returnsSemester() throws Exception {
        Semester expectedSemester = Semester.valueOf(VALID_SEMESTER);
        assertEquals(expectedSemester, ParserUtil.parseSemester(VALID_SEMESTER));
    }

    @Test
    public void parseSemester_validValueWithWhitespace_returnsTrimmedSemester() throws Exception {
        String semesterWithWhitespace = WHITESPACE + VALID_SEMESTER + WHITESPACE;
        Semester expectedSemester = Semester.valueOf(VALID_SEMESTER);
        assertEquals(expectedSemester, ParserUtil.parseSemester(semesterWithWhitespace));
    }

    @Test
    public void parseExpectedMaxGrade_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseGrade((String) null));
    }

    @Test
    public void parseExpectedMaxGrade_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseGrade(INVALID_EXPECTED_MAX_GRADE));
    }

    @Test
    public void parseExpectedMaxGrade_validValueWithoutWhitespace_returnsExpectedMaxGrade() throws Exception {
        Grade expectedExpectedMaxGrade = Grade.getGrade(VALID_EXPECTED_MAX_GRADE);
        assertEquals(expectedExpectedMaxGrade, ParserUtil.parseGrade(VALID_EXPECTED_MAX_GRADE));
    }

    @Test
    public void parseExpectedMaxGrade_validValueWithWhitespace_returnsTrimmedExpectedMaxGrade() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_EXPECTED_MAX_GRADE + WHITESPACE;
        Grade expectedExpectedMaxGrade = Grade.getGrade(VALID_EXPECTED_MAX_GRADE);
        assertEquals(expectedExpectedMaxGrade, ParserUtil.parseGrade(addressWithWhitespace));
    }

    @Test
    public void parseExpectedMinGrade_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseGrade((String) null));
    }

    @Test
    public void parseExpectedMinGrade_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseGrade(INVALID_EXPECTED_MIN_GRADE));
    }

    @Test
    public void parseExpectedMinGrade_validValueWithoutWhitespace_returnsExpectedMinGrade() throws Exception {
        Grade expectedExpectedMinGrade = Grade.valueOf(VALID_EXPECTED_MIN_GRADE);
        assertEquals(expectedExpectedMinGrade, ParserUtil.parseGrade(VALID_EXPECTED_MIN_GRADE));
    }

    @Test
    public void parseExpectedMinGrade_validValueWithWhitespace_returnsTrimmedExpectedMinGrade() throws Exception {
        String expectedMinGradeWithWhitespace = WHITESPACE + VALID_EXPECTED_MIN_GRADE + WHITESPACE;
        Grade expectedExpectedMinGrade = Grade.valueOf(VALID_EXPECTED_MIN_GRADE);
        assertEquals(expectedExpectedMinGrade, ParserUtil.parseGrade(expectedMinGradeWithWhitespace));
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
}
