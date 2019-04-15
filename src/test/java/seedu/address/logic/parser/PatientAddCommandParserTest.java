package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DOB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import seedu.address.logic.commands.PatientAddCommand;
import seedu.address.model.datetime.DateBase;
import seedu.address.model.datetime.DateOfBirth;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Sex;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;

public class PatientAddCommandParserTest {
    private PatientAddCommandParser parser = new PatientAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + NRIC_DESC_BOB + DOB_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SEX_DESC_BOB,
                new PatientAddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + NRIC_DESC_BOB + DOB_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SEX_DESC_AMY,
                new PatientAddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + NRIC_DESC_BOB + DOB_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SEX_DESC_BOB, new PatientAddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + DOB_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SEX_DESC_BOB, new PatientAddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + DOB_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + SEX_DESC_BOB, new PatientAddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PatientAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + NRIC_DESC_BOB + DOB_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SEX_DESC_BOB, expectedMessage);

        // missing nric prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_NRIC_BOB + DOB_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SEX_DESC_BOB, expectedMessage);

        // missing dob prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + VALID_DOB_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SEX_DESC_BOB, expectedMessage);

        // missing sex prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + VALID_DOB_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + VALID_SEX_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_NRIC_BOB + VALID_DOB_BOB + VALID_PHONE_BOB
                        + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + VALID_SEX_BOB, expectedMessage);
    }

    @Test
    public void parse_differentDates() {
        String tempDob;

        DateBase lastYear = DateOfBirth.getToday();
        lastYear.setTo(lastYear.getDay(), lastYear.getMonth(), lastYear.getYear() - 1);

        tempDob = " " + PREFIX_YEAR + lastYear.getRawFormat() + " ";
        Person expectedPerson = new PersonBuilder(BOB).withDob(lastYear.getRawFormat()).build();

        // last year -> valid dob
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + tempDob
                + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SEX_DESC_BOB, new PatientAddCommand(expectedPerson));

        String yesterday = getYesterdayDateString();
        tempDob = " " + PREFIX_YEAR + yesterday + " ";
        expectedPerson = new PersonBuilder(BOB).withDob(yesterday).build();

        // yesterday -> valid dob
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + tempDob
                + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SEX_DESC_BOB, new PatientAddCommand(expectedPerson));

        DateBase today = DateOfBirth.getToday();

        tempDob = " " + PREFIX_YEAR + today.getRawFormat() + " ";
        expectedPerson = new PersonBuilder(BOB).withDob(today.getRawFormat()).build();

        // today -> valid dob
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + tempDob
                + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + SEX_DESC_BOB, new PatientAddCommand(expectedPerson));

        // for the test failures below
        String expectedMessage = DateOfBirth.MESSAGE_CONSTRAINTS_FUTURE_DAY;

        String tomorrow = getTomorrowDateString();
        tempDob = " " + PREFIX_YEAR + tomorrow + " ";

        // tomorrow -> invalid dob
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + tempDob + SEX_DESC_BOB,
                expectedMessage);

        DateBase nextYear = DateOfBirth.getToday();
        nextYear.setTo(nextYear.getDay(), nextYear.getMonth(), nextYear.getYear() + 1);

        tempDob = " " + PREFIX_YEAR + nextYear.getRawFormat() + " ";

        // next year compulsory fields only -> invalid dob
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + tempDob + SEX_DESC_BOB,
                expectedMessage);

        // next year all fields present -> invalid dob
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + tempDob + SEX_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private Date tomorrow() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(yesterday());
    }

    private String getTomorrowDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(tomorrow());
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BOB + DOB_DESC_BOB
                + SEX_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid nric
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_NRIC_DESC + DOB_DESC_BOB
                + SEX_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, Nric.MESSAGE_CONSTRAINTS);

        // invalid dob
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + INVALID_DOB_DESC + SEX_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, DateOfBirth.MESSAGE_CONSTRAINTS);

        // invalid sex
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + DOB_DESC_BOB + INVALID_SEX_DESC
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, Sex.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + DOB_DESC_BOB + NRIC_DESC_BOB
                + SEX_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + DOB_DESC_BOB + NRIC_DESC_BOB
                + SEX_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + DOB_DESC_BOB + NRIC_DESC_BOB
                + SEX_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BOB + DOB_DESC_BOB + NRIC_DESC_BOB
                + SEX_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + NRIC_DESC_BOB + DOB_DESC_BOB
                + NRIC_DESC_BOB + SEX_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB, String.format(MESSAGE_INVALID_COMMAND_FORMAT, PatientAddCommand.MESSAGE_USAGE));
    }
}
