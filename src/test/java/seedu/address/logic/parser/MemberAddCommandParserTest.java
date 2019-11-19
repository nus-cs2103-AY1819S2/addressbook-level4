package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MATRICNUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MATRICNUMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_RUNNING;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SWIMMING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRICNUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_RUNNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SWIMMING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAROFSTUDY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.YEAROFSTUDY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.YEAROFSTUDY_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.logic.commands.MemberAddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class MemberAddCommandParserTest {
    private MemberAddCommandParser parser = new MemberAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_RUNNING).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB
                        + TAG_DESC_RUNNING,
                new MemberAddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_RUNNING, new MemberAddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_RUNNING, new MemberAddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_RUNNING, new MemberAddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB
                + MAJOR_DESC_BOB + TAG_DESC_RUNNING, new MemberAddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_RUNNING, VALID_TAG_SWIMMING)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_SWIMMING + TAG_DESC_RUNNING, new MemberAddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + MATRICNUMBER_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + GENDER_DESC_AMY + YEAROFSTUDY_DESC_AMY + MAJOR_DESC_AMY,
                new MemberAddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemberAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                        + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + VALID_ADDRESS_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_MATRICNUMBER_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                        + VALID_ADDRESS_BOB + VALID_GENDER_BOB + VALID_YEAROFSTUDY_BOB + VALID_MAJOR_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_SWIMMING + TAG_DESC_RUNNING, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_SWIMMING + TAG_DESC_RUNNING, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_SWIMMING + TAG_DESC_RUNNING, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB
                + TAG_DESC_SWIMMING + TAG_DESC_RUNNING, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_RUNNING, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + MATRICNUMBER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + GENDER_DESC_BOB + YEAROFSTUDY_DESC_BOB + MAJOR_DESC_BOB
                        + TAG_DESC_SWIMMING + TAG_DESC_RUNNING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemberAddCommand.MESSAGE_USAGE));
    }
}
