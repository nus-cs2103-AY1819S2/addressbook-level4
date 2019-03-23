package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.tag.Skills;
import seedu.address.model.tag.Specialisation;
import seedu.address.model.tag.Condition;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234312";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_CONDITION = "#friend";
    private static final String INVALID_ORGANIZATION = "h@xx0r";
    private static final String INVALID_NRIC = "A12345678";
    private static final String INVALID_SPECIALISATION = "physio";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "98765432";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_CONDITION_1 = "cancer";
    private static final String VALID_CONDITION_2 = "palliative";
    private static final String VALID_ORGANIZATION = "NUS";
    private static final String VALID_NRIC = "S1234567A";
    private static final String VALID_SPECIALISATION = "PHYSIOTHERAPY";
    private static final String VALID_SPECIALISATION_2 = "NEUROLOGY";

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
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
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
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress(null));
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

    // ========= Tests for parsing Condition from Request =========
    // @author Rohan

    @Test
    public void parseCondition() throws ParseException {
        // null condition
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil
                .parseCondition(null));

        // invalid condition
        Assert.assertThrows(ParseException.class, () -> ParserUtil
                .parseCondition(INVALID_CONDITION));

        // same condition -> returns true
        Condition expectedCondition = new Condition(VALID_CONDITION_1);
        assertEquals(expectedCondition, ParserUtil
                .parseCondition(VALID_CONDITION_1));

        // condition with whitespace trimmed -> returned true
        assertEquals(expectedCondition, ParserUtil.parseCondition(
                WHITESPACE + VALID_CONDITION_1 + WHITESPACE));
    }

    @Test
    public void parseConditions() throws ParseException {
        // null condition set
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil
                .parseConditions(null));

        // list contains invalid condition
        Assert.assertThrows(ParseException.class, () -> ParserUtil
                .parseConditions(Arrays.asList(VALID_CONDITION_1,
                        INVALID_CONDITION)));

        // empty condition set
        assertTrue(ParserUtil.parseConditions(Collections.emptyList()).isEmpty());

        // valid condition returns conditionSet containing conditions
        Set<Condition> expectedConditions = new HashSet<>(Arrays.asList
                (new Condition(VALID_CONDITION_1),
                        new Condition(VALID_CONDITION_2)));
        assertEquals(expectedConditions, ParserUtil.parseConditions(Arrays
                .asList(VALID_CONDITION_1, VALID_CONDITION_2)));
    }

    // ========= Tests for parsing Organisation/NRIC/Specialisation =========
    // @author Lookaz

    @Test
    public void parseOrganization() throws ParseException {
        // null organization
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil
                .parseOrganization(null));

        // invalid organization
        Assert.assertThrows(ParseException.class, () -> ParserUtil
                .parseOrganization(INVALID_ORGANIZATION));

        // same organization -> returns true
        Organization expectedOrganization = new Organization(VALID_ORGANIZATION);
        assertEquals(expectedOrganization, ParserUtil.parseOrganization(VALID_ORGANIZATION));

        // organization with whitespace trimmed -> returned true
        assertEquals(expectedOrganization, ParserUtil.parseOrganization(
                WHITESPACE + VALID_ORGANIZATION + WHITESPACE));
    }

    @Test
    public void parseNric() throws ParseException {
        // null Nric
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil
                .parseNric(null));

        // invalid Nric
        Assert.assertThrows(ParseException.class, () -> ParserUtil
                .parseNric(INVALID_NRIC));

        // same Nric -> returns true
        Nric expectedNric = new Nric(VALID_NRIC);
        assertEquals(expectedNric, ParserUtil.parseNric(VALID_NRIC));

        // Nric with whitespace trimmed -> returned true
        assertEquals(expectedNric, ParserUtil.parseNric(
                WHITESPACE + VALID_NRIC + WHITESPACE));
    }

    @Test
    public void parseSpecialisation() throws ParseException {
        // null specialisation
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil
                .parseSpecialisation(null));

        // invalid specialisation
        Assert.assertThrows(ParseException.class, () -> ParserUtil
                .parseSpecialisation(INVALID_SPECIALISATION));

        // same specialisation -> returns true
        Specialisation expectedSpecialisation = Specialisation.valueOf(VALID_SPECIALISATION);
        assertEquals(expectedSpecialisation, ParserUtil
                .parseSpecialisation(VALID_SPECIALISATION));

        // specialisation with whitespace trimmed -> returned true
        assertEquals(expectedSpecialisation, ParserUtil.parseSpecialisation(
                WHITESPACE + VALID_SPECIALISATION + WHITESPACE));
    }

    @Test
    public void parseSpecialisations() throws ParseException {
        // null specialisation set
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil
                .parseSpecialisations(null));

        // list contains invalid specialisation
        Assert.assertThrows(ParseException.class, () -> ParserUtil
                .parseSpecialisations(Arrays.asList(VALID_SPECIALISATION,
                        INVALID_SPECIALISATION)));

        // empty specialisation set
        assertTrue(ParserUtil.parseSpecialisations(Collections.emptyList())
                .getSkills().isEmpty());

        // valid specialisation returns skills containing specialisations
        Skills expectedSkills = new Skills(Arrays.asList
                (Specialisation.valueOf(VALID_SPECIALISATION),
                        Specialisation.valueOf(VALID_SPECIALISATION_2)));
        assertEquals(expectedSkills, ParserUtil.parseSpecialisations(Arrays
                .asList(VALID_SPECIALISATION, VALID_SPECIALISATION_2)));
    }
}
