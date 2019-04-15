package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.storage.ParsedInOut;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_FILENAME = "records.txt";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_FILENAME = "folder/records.json";
    private static final String VALID_FILENAME2 = "folder\\records.json";
    private static final String VALID_FILENAME3 = "folder/records.pdf";
    private static final String VALID_FILENAME4 = "folder\\records.pdf";

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
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseOpenSave_validFileNameJson() throws Exception {
        ParsedInOut test = new ParsedInOut(
            new File("data/folder/records.json"), "json");
        ParsedInOut actual = ParserUtil.parseOpenSave(" " + VALID_FILENAME);
        assertEquals(test.getFile().toPath(), actual.getFile().toPath());
        assertEquals(test.getType(), actual.getType());
    }

    @Test
    public void parseOpenSave_validFileNameJson2() throws Exception {
        ParsedInOut test = new ParsedInOut(
            new File("data/folder/records.json"), "json");
        ParsedInOut actual = ParserUtil.parseOpenSave(" " + VALID_FILENAME2);
        assertEquals(test.getFile().toPath(), actual.getFile().toPath());
        assertEquals(test.getType(), actual.getType());
    }

    @Test
    public void parseOpenSave_validFileNamePdf() throws Exception {
        ParsedInOut test = new ParsedInOut(
            new File("data/folder/records.pdf"), "pdf");
        ParsedInOut actual = ParserUtil.parseOpenSave(" " + VALID_FILENAME3);
        assertEquals(test.getFile().toPath(), actual.getFile().toPath());
        assertEquals(test.getType(), actual.getType());
    }

    @Test
    public void parseOpenSave_validFileNamePdf2() throws Exception {
        ParsedInOut test = new ParsedInOut(
            new File("data/folder/records.pdf"), "pdf");
        ParsedInOut actual = ParserUtil.parseOpenSave(" " + VALID_FILENAME4);
        assertEquals(test.getFile().toPath(), actual.getFile().toPath());
        assertEquals(test.getType(), actual.getType());
    }

    @Test
    public void parseOpenSave_invalidFileName() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseOpenSave(INVALID_FILENAME);
    }

    @Test
    public void parseImportExport_validFileNameJson_singleIndex() throws Exception {
        ParsedInOut actual = ParserUtil.parseImportExport(" " + VALID_FILENAME + " 3");
        assertEquals(new File("data/folder/records.json"), actual.getFile());
        assertEquals("json", actual.getType());;
        assertTrue(actual.getParsedIndex().contains(3 - 1));
        assertFalse(actual.getParsedIndex().contains(4 - 1));
    }

    @Test
    public void parseImportExport_validFileNameJson2_singleIndex() throws Exception {
        ParsedInOut actual = ParserUtil.parseImportExport(" " + VALID_FILENAME2 + " 3");
        assertEquals(new File("data/folder/records.json"), actual.getFile());
        assertEquals("json", actual.getType());
        assertTrue(actual.getParsedIndex().contains(3 - 1));
        assertFalse(actual.getParsedIndex().contains(4 - 1));
    }

    @Test
    public void parseImportExport_validFileNamePdf_singleIndex() throws Exception {
        ParsedInOut actual = ParserUtil.parseImportExport(" " + VALID_FILENAME3 + " 3");
        assertEquals(new File("data/folder/records.pdf"), actual.getFile());
        assertEquals("pdf", actual.getType());
        assertTrue(actual.getParsedIndex().contains(3 - 1));
        assertFalse(actual.getParsedIndex().contains(4 - 1));
    }

    @Test
    public void parseImportExport_validFileNamePdf2_singleIndex() throws Exception {
        ParsedInOut actual = ParserUtil.parseImportExport(" " + VALID_FILENAME4 + " 3");
        assertEquals(new File("data/folder/records.pdf"), actual.getFile());
        assertEquals("pdf", actual.getType());
        assertTrue(actual.getParsedIndex().contains(3 - 1));
        assertFalse(actual.getParsedIndex().contains(4 - 1));
    }

    @Test
    public void parseImportExport_validFileName_commaRange() throws Exception {
        ParsedInOut actual = ParserUtil.parseImportExport(" " + VALID_FILENAME + " 1,3,5");
        assertEquals(new File("data/folder/records.json"), actual.getFile());
        assertEquals("json", actual.getType());
        assertTrue(actual.getParsedIndex().contains(1 - 1));
        assertTrue(actual.getParsedIndex().contains(3 - 1));
        assertTrue(actual.getParsedIndex().contains(5 - 1));
        assertFalse(actual.getParsedIndex().contains(4 - 1));
    }

    @Test
    public void parseImportExport_validFileName_dashRange() throws Exception {
        ParsedInOut actual = ParserUtil.parseImportExport(" " + VALID_FILENAME + " 3-5");
        assertEquals(new File("data/folder/records.json"), actual.getFile());
        assertEquals("json", actual.getType());
        assertFalse(actual.getParsedIndex().contains(1 - 1));
        assertTrue(actual.getParsedIndex().contains(3 - 1));
        assertTrue(actual.getParsedIndex().contains(4 - 1));
        assertTrue(actual.getParsedIndex().contains(5 - 1));
    }

    @Test
    public void parseImportExport_validFileName_combinedRange() throws Exception {
        ParsedInOut actual = ParserUtil.parseImportExport(" " + VALID_FILENAME + " 1,3-5");
        assertEquals(new File("data/folder/records.json"), actual.getFile());
        assertEquals("json", actual.getType());
        assertTrue(actual.getParsedIndex().contains(1 - 1));
        assertFalse(actual.getParsedIndex().contains(2 - 1));
        assertTrue(actual.getParsedIndex().contains(3 - 1));
        assertTrue(actual.getParsedIndex().contains(4 - 1));
        assertTrue(actual.getParsedIndex().contains(5 - 1));
    }

    @Test
    public void parseImportExport_validFileName_all() throws Exception {
        ParsedInOut actual = ParserUtil.parseImportExport(" " + VALID_FILENAME + " all");
        assertEquals(new File("data/folder/records.json"), actual.getFile());
        assertEquals("json", actual.getType());
        assertTrue(actual.getArgIsAll());
    }

    @Test
    public void parseImportExport_validFileName_invalidRange() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseImportExport(" " + "folder/records.json" + " a,b");
    }

    @Test
    public void parseImportExport_validFileName_noRange() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseImportExport(" " + "folder/records.json");
    }

    @Test
    public void parseImportExport_invalidFileName_validRange() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseImportExport(INVALID_FILENAME + " 1,3-5");
    }

    @Test
    public void parseImportExport_invalidFileName_invalidRange() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseImportExport(INVALID_FILENAME + " a,b");
    }

    @Test
    public void parseImportExport_invalidFileName_noRange() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseImportExport(INVALID_FILENAME);
    }
}
