package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAJOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PASTJOB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RACE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHOOL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.KNOWNPROGLANG_DESC_PYTHON;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PASTJOB_DESC_PROFESSOR;
import static seedu.address.logic.commands.CommandTestUtil.PASTJOB_DESC_SDE;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RACE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RACE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASTJOB_PROFESSSOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASTJOB_SDE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RACE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.PastJob;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Race;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withPastJobs(VALID_PASTJOB_PROFESSSOR)
            .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + RACE_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB + PASTJOB_DESC_PROFESSOR
                + KNOWNPROGLANG_DESC_PYTHON + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + RACE_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB + PASTJOB_DESC_PROFESSOR
                + KNOWNPROGLANG_DESC_PYTHON + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + RACE_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB + PASTJOB_DESC_PROFESSOR
                + KNOWNPROGLANG_DESC_PYTHON + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + RACE_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB + PASTJOB_DESC_PROFESSOR
                + KNOWNPROGLANG_DESC_PYTHON + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB + PASTJOB_DESC_PROFESSOR
                + KNOWNPROGLANG_DESC_PYTHON + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple schools - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_AMY + SCHOOL_DESC_BOB + MAJOR_DESC_BOB + PASTJOB_DESC_PROFESSOR
                + KNOWNPROGLANG_DESC_PYTHON + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        //multiple majors - last major accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_AMY + MAJOR_DESC_BOB
                + KNOWNPROGLANG_DESC_PYTHON + PASTJOB_DESC_PROFESSOR + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        //multiple race - last race accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_AMY
                + RACE_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB + PASTJOB_DESC_PROFESSOR
                + KNOWNPROGLANG_DESC_PYTHON + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
            .withPastJobs(VALID_PASTJOB_PROFESSSOR, VALID_PASTJOB_SDE)
            .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
            .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB + KNOWNPROGLANG_DESC_PYTHON
                + PASTJOB_DESC_PROFESSOR + PASTJOB_DESC_SDE + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + RACE_DESC_AMY
                + ADDRESS_DESC_AMY + RACE_DESC_AMY + SCHOOL_DESC_AMY + MAJOR_DESC_AMY + PASTJOB_DESC_PROFESSOR
                + KNOWNPROGLANG_DESC_PYTHON,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + VALID_ADDRESS_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // missing school prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + VALID_SCHOOL_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // missing major prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + VALID_MAJOR_BOB,
                expectedMessage);

        // missing race prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_RACE_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_RACE_BOB
                + VALID_ADDRESS_BOB + VALID_SCHOOL_BOB + VALID_MAJOR_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + INVALID_ADDRESS_DESC + SCHOOL_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid school
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_SCHOOL_DESC + MAJOR_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, School.MESSAGE_CONSTRAINTS);

        // invalid major
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + INVALID_MAJOR_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Major.MESSAGE_CONSTRAINTS);

        // invalid race
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_RACE_DESC
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Race.MESSAGE_CONSTRAINTS);

        // invalid past job
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
            + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB
            + INVALID_PASTJOB_DESC + TAG_DESC_FRIEND, PastJob.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + MAJOR_DESC_BOB
                + INVALID_TAG_DESC + TAG_DESC_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + RACE_DESC_BOB
                + INVALID_ADDRESS_DESC + SCHOOL_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + RACE_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
