package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.WebUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.OpeningHours;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Weblink;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_OPENING_HOURS = "3000 to 2333";
    private static final String INVALID_OPENING_HOURS_2 = "0800 to 0800";
    private static final String INVALID_WEBLINK = "asdasdnasda";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_OPENING_HOURS = "0800 to 2359";
    private static final String VALID_WEBLINK = "https://www.google.com.sg";
    private static final String VALID_WEBLINK_WITHOUT_PROTOCOL = "www.kfc.com.sg";

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
        assertEquals(INDEX_FIRST_RESTAURANT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_RESTAURANT, ParserUtil.parseIndex("  1  "));
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
    public void parsePhone_defaultPhone() throws Exception {
        String defaultPhone = "No phone added";
        Phone expectedPhone = Phone.makeDefaultPhone();
        assertEquals(expectedPhone, ParserUtil.parsePhone(defaultPhone));
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
    public void parseEmail_defaultEmail() throws Exception {
        String defaultEmail = "No email added";
        Email expectedEmail = Email.makeDefaultEmail();
        assertEquals(expectedEmail, ParserUtil.parseEmail(defaultEmail));
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
    public void parseOpeningHours_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseOpeningHours((String) null));
    }

    @Test
    public void parseOpeningHours_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseOpeningHours(INVALID_OPENING_HOURS));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseOpeningHours(INVALID_OPENING_HOURS_2));
    }

    @Test
    public void parseOpeningHours_validValueWithoutWhitespace_returnsOpeningHours() throws Exception {
        OpeningHours expectedOpeningHours = new OpeningHours(VALID_OPENING_HOURS);
        assertEquals(expectedOpeningHours, ParserUtil.parseOpeningHours(VALID_OPENING_HOURS));
    }

    @Test
    public void parseOpeningHours_validValueWithWhitespace_returnsTrimmedOpeningHours() throws Exception {
        String openingHoursWithWhitespace = WHITESPACE + VALID_OPENING_HOURS + WHITESPACE;
        OpeningHours expectedOpeningHours = new OpeningHours(VALID_OPENING_HOURS);
        assertEquals(expectedOpeningHours, ParserUtil.parseOpeningHours(openingHoursWithWhitespace));
    }

    @Test
    public void parseOpeningHours_defaultOpeningHours() throws Exception {
        String defaultOpeningHours = "No opening hours added";
        OpeningHours expectedOpeningHours = OpeningHours.makeDefaultOpening();
        assertEquals(expectedOpeningHours, ParserUtil.parseOpeningHours(defaultOpeningHours));
    }
    @Test
    public void parseWeblink_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseWeblink((String) null));
    }

    @Test
    public void parseWeblink_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseWeblink(INVALID_WEBLINK));
    }

    @Test
    public void parseWeblink_validValueWithoutWhitespace_returnsWeblink() throws Exception {
        Weblink expectedWeblink = new Weblink(VALID_WEBLINK);
        assertEquals(expectedWeblink, ParserUtil.parseWeblink(VALID_WEBLINK));
    }

    @Test
    public void parseWeblink_validValueWithWhitespace_returnsTrimmedWeblink() throws Exception {
        String weblinkWithWhitespace = WHITESPACE + VALID_WEBLINK + WHITESPACE;
        Weblink expectedWeblink = new Weblink(VALID_WEBLINK);
        assertEquals(expectedWeblink, ParserUtil.parseWeblink(weblinkWithWhitespace));
    }

    @Test
    public void parseWeblink_defaultWeblink() throws Exception {
        String defaultWeblink = "No weblink added";
        Weblink expectedWeblink = Weblink.makeDefaultWeblink();
        assertEquals(expectedWeblink, ParserUtil.parseWeblink(defaultWeblink));
    }

    @Test
    public void parseWeblink_withoutProtocol() throws Exception {
        String weblinkWithoutProtocol = VALID_WEBLINK_WITHOUT_PROTOCOL;
        Weblink expectedWeblink = new Weblink(WebUtil.validateAndAppend(VALID_WEBLINK_WITHOUT_PROTOCOL));
        assertEquals(expectedWeblink, ParserUtil.parseWeblink(weblinkWithoutProtocol));
    }
}
