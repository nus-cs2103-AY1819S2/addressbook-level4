package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORGANIZATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SKILLS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODE_HEALTHWORKER;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SKILLS_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddHealthWorkerCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.tag.Specialisation;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.HealthWorkerBuilder;
import seedu.address.testutil.PersonBuilder;

// TODO: Modify tests to include command mode after all commands implemented using command mode.
public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddPersonCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddPersonCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddPersonCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddPersonCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddPersonCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddPersonCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddPersonCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
    }

    // =================== Tests for parseAddHealthWorker ===================
    // @author Lookaz

    @Test
    public void parseAddHealthWorker() {
        HealthWorker expectedWorker = new HealthWorkerBuilder(ANDY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODE_HEALTHWORKER
                + NAME_DESC_ANDY + PHONE_DESC_ANDY + EMAIL_DESC_ANDY + ADDRESS_DESC_ANDY
                + ORGANIZATION_DESC_ANDY + NRIC_DESC_ANDY + SKILLS_DESC_ANDY,
                new AddHealthWorkerCommand(expectedWorker));

        // multiple names - last name accepted
        assertParseSuccess(parser, MODE_HEALTHWORKER + NAME_DESC_BETTY
                + NAME_DESC_ANDY + PHONE_DESC_ANDY + EMAIL_DESC_ANDY + ADDRESS_DESC_ANDY
                + ORGANIZATION_DESC_ANDY + NRIC_DESC_ANDY + SKILLS_DESC_ANDY,
                new AddHealthWorkerCommand(expectedWorker));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY
                + PHONE_DESC_BETTY + PHONE_DESC_ANDY + EMAIL_DESC_ANDY + ADDRESS_DESC_ANDY
                + ORGANIZATION_DESC_ANDY + NRIC_DESC_ANDY + SKILLS_DESC_ANDY,
                new AddHealthWorkerCommand(expectedWorker));

        // multiple emails - last email accepted
        assertParseSuccess(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + EMAIL_DESC_BETTY + EMAIL_DESC_ANDY + ADDRESS_DESC_ANDY + ORGANIZATION_DESC_ANDY
                + NRIC_DESC_ANDY + SKILLS_DESC_ANDY, new AddHealthWorkerCommand(expectedWorker));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + EMAIL_DESC_ANDY + ADDRESS_DESC_BETTY + ADDRESS_DESC_ANDY + ORGANIZATION_DESC_ANDY
                + NRIC_DESC_ANDY + SKILLS_DESC_ANDY, new AddHealthWorkerCommand(expectedWorker));

        // multiple NRIC - last NRIC accepted
        assertParseSuccess(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + EMAIL_DESC_ANDY + ADDRESS_DESC_ANDY + ORGANIZATION_DESC_ANDY + NRIC_DESC_BETTY
                + NRIC_DESC_ANDY + SKILLS_DESC_ANDY, new AddHealthWorkerCommand(expectedWorker));

        // multiple organizations - last organization accepted
        assertParseSuccess(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + EMAIL_DESC_ANDY + ADDRESS_DESC_ANDY + ORGANIZATION_DESC_BETTY + ORGANIZATION_DESC_ANDY
                + NRIC_DESC_ANDY + SKILLS_DESC_ANDY, new AddHealthWorkerCommand(expectedWorker));

        // Missing prefix tests
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHealthWorkerCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + " " + VALID_NAME_ANDY + PHONE_DESC_ANDY + EMAIL_DESC_ANDY
                + ADDRESS_DESC_ANDY + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + VALID_PHONE_ANDY + EMAIL_DESC_ANDY
                + ADDRESS_DESC_ANDY + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY + VALID_EMAIL_ANDY
                + ADDRESS_DESC_ANDY + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY + EMAIL_DESC_ANDY
                + VALID_ADDRESS_ANDY + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, expectedMessage);

        // missing organization prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY + EMAIL_DESC_ANDY
                + ADDRESS_DESC_ANDY + NRIC_DESC_ANDY + VALID_ORGANIZATION_ANDY + SKILLS_DESC_ANDY, expectedMessage);

        // missing nric prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY + EMAIL_DESC_ANDY
                + ADDRESS_DESC_ANDY + VALID_NRIC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, expectedMessage);

        // missing skills prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY + EMAIL_DESC_ANDY
                + ADDRESS_DESC_ANDY + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY, expectedMessage);

        // invalid name
        assertParseFailure(parser, MODE_HEALTHWORKER + " " + INVALID_NAME_DESC + PHONE_DESC_ANDY
                + EMAIL_DESC_ANDY + ADDRESS_DESC_ANDY + NRIC_DESC_ANDY
                + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + INVALID_PHONE_DESC
                + EMAIL_DESC_ANDY + ADDRESS_DESC_ANDY + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY
                + SKILLS_DESC_ANDY, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + INVALID_EMAIL_DESC + ADDRESS_DESC_ANDY + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY
                + SKILLS_DESC_ANDY, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + EMAIL_DESC_ANDY + INVALID_ADDRESS_DESC + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY
                + SKILLS_DESC_ANDY, Address.MESSAGE_CONSTRAINTS);

        // invalid organization
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + EMAIL_DESC_ANDY + ADDRESS_DESC_ANDY + NRIC_DESC_ANDY + INVALID_ORGANIZATION_DESC
                + SKILLS_DESC_ANDY, Organization.MESSAGE_CONSTRAINTS);

        // invalid skills
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + EMAIL_DESC_ANDY + ADDRESS_DESC_ANDY + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY
                + INVALID_SKILLS_DESC, Specialisation.MESSAGE_CONSTRAINTS);
    }
}
