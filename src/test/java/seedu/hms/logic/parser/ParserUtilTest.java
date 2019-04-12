package seedu.hms.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;
import static seedu.hms.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BookingManager;
import seedu.hms.model.ReservationManager;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Address;
import seedu.hms.model.customer.DateOfBirth;
import seedu.hms.model.customer.Email;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.customer.Name;
import seedu.hms.model.customer.Phone;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.model.tag.Tag;
import seedu.hms.model.util.TimeRange;
import seedu.hms.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_DATE_OF_BIRTH = "123/1/1459";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ID = "+3422";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_DATE_OF_BIRTH = "02/02/1999";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_ID = "2345525";
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
        assertEquals(INDEX_FIRST_CUSTOMER, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_CUSTOMER, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
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
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
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
    public void parseIdNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseIdNum(null));
    }

    @Test
    public void parseIdInvalidValueThrowsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIdNum(INVALID_ID));
    }

    @Test
    public void parseIdValidValueWithoutWhitespaceReturnsId() throws Exception {
        IdentificationNo expectedId = new IdentificationNo(VALID_ID);
        assertEquals(expectedId, ParserUtil.parseIdNum(VALID_ID));
    }

    @Test
    public void parseIdValidValueWithWhitespaceReturnsTrimmedId() throws Exception {
        String idWithWhitespace = WHITESPACE + VALID_ID + WHITESPACE;
        IdentificationNo expectedId = new IdentificationNo(VALID_ID);
        assertEquals(expectedId, ParserUtil.parseIdNum(idWithWhitespace));
    }


    @Test
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress(null));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnshms() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedhms() throws Exception {
        String hmsWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(hmsWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail(null));
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
    public void parseDateOfBirthNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDateOfBirth(null));
    }

    @Test
    public void parseDateOfBirthInvalidValueThrowsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDateOfBirth(INVALID_DATE_OF_BIRTH));
    }

    @Test
    public void parseDateOfBirthValidValueWithoutWhitespaceReturnsDateOfBirth() throws Exception {
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DATE_OF_BIRTH);
        assertEquals(expectedDateOfBirth, ParserUtil.parseDateOfBirth(VALID_DATE_OF_BIRTH));
    }

    @Test
    public void parseDateOfBirthValidValueWithWhitespaceReturnsTrimmedDateOfBirth() throws Exception {
        String dateOfBirthWithWhitespace = WHITESPACE + VALID_DATE_OF_BIRTH + WHITESPACE;
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DATE_OF_BIRTH);
        assertEquals(expectedDateOfBirth, ParserUtil.parseDateOfBirth(dateOfBirthWithWhitespace));
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
    public void parseCapacity_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseCapacity("-1");
    }

    @Test
    public void parseRate_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseRate("-1");
    }

    @Test
    public void parseTiming_invalidArgs_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTiming("0");
    }

    @Test
    public void parseTiming_invalidHours_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTiming("15-14");
        ParserUtil.parseTiming("0-25");
    }

    @Test
    public void parseDatesThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseDates("14/05/2019");
    }

    @Test
    public void parseRoomTypeThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseRoom("SQUARE ROOM", new ReservationManager(
            new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs()));
    }

    @Test
    public void parseRoomType_validValue_returnsRoomType() throws Exception {
        RoomType roomType = new RoomType(100, "Single room", 500.0);
        assertEquals(roomType, ParserUtil.parseRoom(roomType.getName(), new ReservationManager(
            new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs())));
    }

    @Test
    public void parseServiceTypeThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseService("SALOON", new BookingManager(
            new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs()));
    }

    @Test
    public void parseServiceType_validValue_returnsServiceType() throws Exception {
        ServiceType serviceType = new ServiceType(50, new TimeRange(8, 22), "Gym", 7.0);
        assertEquals(serviceType, ParserUtil.parseService(serviceType.getName(), new BookingManager(
            new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs())));
    }

    @Test
    public void parseType_validValueWithoutWhitespace_returnsType() throws Exception {
        String type = "SERVICE";
        assertEquals(type, ParserUtil.parseType("SERVICE"));
    }

    @Test
    public void parseTypeThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseType("QW ERTYUI O OPPY YTRR");
    }

}
