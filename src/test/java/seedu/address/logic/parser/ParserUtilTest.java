package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.isLeapYear;
import static seedu.address.logic.parser.ParserUtil.isValidDate;
import static seedu.address.logic.parser.ParserUtil.isValidDateRange;
import static seedu.address.logic.parser.ParserUtil.isValidValueRange;
import static seedu.address.logic.parser.ParserUtil.parseBlockOutDates;
import static seedu.address.logic.parser.ParserUtil.parseMaxInterviewsADay;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final int POSITIVE_MAX_INTERVIEWS = 3;
    private static final String POSITIVE_MAX_INTERVIEWS_STRING = "3";
    private static final String WHITESPACE_MAX_INTERVIEWS = " 3 ";
    private static final String INVALID_MAX_INTERVIEWS_NONINTEGER = "HELLO";
    private static final String INVALID_MAX_INTERVIEWS_NEGINTEGER = "-1";

    private static final String VALID_BLOCK_OUT_DATES = "01/01/2020 - 04/01/2020";
    private static final List<Calendar> BLOCK_OUT_DATES = new ArrayList<>();
    private static final String INVALID_BLOCK_OUTDATES = "00/01/2020 - 03/01/2020";
    private static final String VALID_DATE = "01/01/2020";
    private static final String VALID_DATE_31 = "31/01/2020";
    private static final String VALID_DATE_30 = "30/04/2020";
    private static final String VALID_DATE_LEAP_YEAR = "29/02/2020";
    private static final String VALID_DATE_RANGE = "01/01/2020 - 31/12/2020";
    private static final String INVALID_DATE_RANGE_SYNTAX = "-1/01/2020 - 31/12/2020";
    private static final String INVALID_DATE_RANGE_INVALID_DATE = "00/01/2020 - 31/12/2020";
    private static final String INVALID_DATE_SYNTAX = "1/1/2020";
    private static final String INVALID_DATE_NON_LEAP_YEAR = "29/02/2019";
    private static final String INVALID_DATE_MONTH = "01/13/2020";
    private static final String INVALID_DAY_32 = "32/01/2020";
    private static final String INVALID_DAY_31 = "31/04/2020";
    private static final String INVALID_DAY_0 = "00/01/2020";

    private static final int LEAP_YEAR_DIVISIBLE_BY_FOUR_BUT_NOT_HUNDRED = 2020;
    private static final int LEAP_YEAR_DIVISIBLE_BY_FOUR_AND_HUNDRED_AND_FOUR_HUNDRED = 2000;
    private static final int YEAR_DIVISIBLE_BY_FOUR_AND_HUNDRED_BUT_NOT_FOUR_HUNDRED = 2100;
    private static final int NON_LEAP_YEAR = 2001;

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
    public void parseMaxInterviewsADay_positiveMaxInterviews_returnsMaxInterviewsADay() throws Exception {
        int expected = POSITIVE_MAX_INTERVIEWS;
        int actual = parseMaxInterviewsADay(POSITIVE_MAX_INTERVIEWS_STRING);
        assertEquals(expected, actual);
    }

    @Test
    public void parseMaxInterviewsADay_whitespaceMaxInterviews_returnsMaxInterviewsADay() throws Exception {
        int expected = POSITIVE_MAX_INTERVIEWS;
        int actual = parseMaxInterviewsADay(WHITESPACE_MAX_INTERVIEWS);
        assertEquals(expected, actual);
    }

    @Test
    public void parseMaxInterviewsADay_invalidMaxInterviewsNonInteger_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        parseMaxInterviewsADay(INVALID_MAX_INTERVIEWS_NONINTEGER);
    }

    @Test
    public void parseMaxInterviewsADay_invalidMaxInterviewsNegInteger_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        parseMaxInterviewsADay(INVALID_MAX_INTERVIEWS_NEGINTEGER);
    }

    @Test
    public void parseBlockOutDates_validBlockOutDates_returnsBlockOutDates() throws Exception {
        List<Calendar> actual = parseBlockOutDates(VALID_BLOCK_OUT_DATES);
        Calendar date = new GregorianCalendar(2020, 0, 1, 0, 0, 0);
        date.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 4; i++) {
            BLOCK_OUT_DATES.add(date);
            date = (Calendar) date.clone();
            date.add(Calendar.DATE, 1);
        }
        assertEquals(BLOCK_OUT_DATES, actual);
    }

    @Test
    public void parseBlockOutDates_invalidBlockOutDates_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        parseBlockOutDates(INVALID_BLOCK_OUTDATES);
    }

    @Test
    public void isValidDate_validDate_returnsTrue() {
        boolean actual = isValidDate(VALID_DATE);
        assertTrue(actual);
    }

    @Test
    public void isValidDate_validDate31_returnsTrue() {
        boolean actual = isValidDate(VALID_DATE_31);
        assertTrue(actual);
    }

    @Test
    public void isValidDate_validDate30_returnsTrue() {
        boolean actual = isValidDate(VALID_DATE_30);
        assertTrue(actual);
    }

    @Test
    public void isValidDate_validDateLeapYear_returnsTrue() {
        boolean actual = isValidDate(VALID_DATE_LEAP_YEAR);
        assertTrue(actual);
    }

    @Test
    public void isValidDate_invalidDateMonth_returnsFalse() {
        boolean actual = isValidDate(INVALID_DATE_MONTH);
        assertFalse(actual);
    }

    @Test
    public void isValidDate_invalidDay32_returnsFalse() {
        boolean actual = isValidDate(INVALID_DAY_32);
        assertFalse(actual);
    }

    @Test
    public void isValidDate_invalidDay31_returnsFalse() {
        boolean actual = isValidDate(INVALID_DAY_31);
        assertFalse(actual);
    }

    @Test
    public void isValidDate_invalidDateSyntax_returnsFalse() {
        boolean actual = isValidDate(INVALID_DATE_SYNTAX);
        assertFalse(actual);
    }

    @Test
    public void isValidDate_invalidDateLeapYear_returnsFalse() {
        boolean actual = isValidDate(INVALID_DATE_NON_LEAP_YEAR);
        assertFalse(actual);
    }

    @Test
    public void isValidDate_invalidDate0_returnsFalse() {
        boolean actual = isValidDate(INVALID_DAY_0);
        assertFalse(actual);
    }

    @Test
    public void isValidDateRange_validDateRange_returnsTrue() {
        boolean actual = isValidDateRange(VALID_DATE_RANGE);
        assertTrue(actual);
    }

    @Test
    public void isValidDateRange_invalidDateRangeSyntax_returnsFalse() {
        boolean actual = isValidDate(INVALID_DATE_RANGE_SYNTAX);
        assertFalse(actual);
    }

    @Test
    public void isValidDateRange_invalidDateRangeinvalidDate_returnsFalse() {
        boolean actual = isValidDate(INVALID_DATE_RANGE_INVALID_DATE);
        assertFalse(actual);
    }

    @Test
    public void isLeapYear_leapYear_returnsTrue() {
        boolean actual = isLeapYear(LEAP_YEAR_DIVISIBLE_BY_FOUR_BUT_NOT_HUNDRED);
        assertTrue(actual);
    }

    @Test
    public void isLeapYear_centurialLeapYear_returnsTrue() {
        boolean actual = isLeapYear(LEAP_YEAR_DIVISIBLE_BY_FOUR_AND_HUNDRED_AND_FOUR_HUNDRED);
        assertTrue(actual);
    }

    @Test
    public void isLeapYear_centurialLeapYear_returnsFalse() {
        boolean actual = isLeapYear(YEAR_DIVISIBLE_BY_FOUR_AND_HUNDRED_BUT_NOT_FOUR_HUNDRED);
        assertFalse(actual);
    }

    @Test
    public void isLeapYear_yearNotDivisibleByFour_returnsFalse() {
        boolean actual = isLeapYear(NON_LEAP_YEAR);
        assertFalse(actual);
    }
    @Test
    public void isValidValueRange_fixArgus_returnsTrue() {
        boolean actual = isValidValueRange("3.0-5");
        assertTrue(actual);
    }
}
