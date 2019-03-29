package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.CONDITION_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONDITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORGANIZATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SKILLS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODE_HEALTHWORKER;
import static seedu.address.logic.commands.CommandTestUtil.MODE_REQUEST;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SKILLS_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ANDY;
import static seedu.address.logic.parser.AddCommandParser.INVALID_COMMAND_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;

import org.junit.Test;

import seedu.address.logic.commands.AddHealthWorkerCommand;
import seedu.address.logic.commands.request.AddRequestCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.request.RequestDate;
import seedu.address.model.tag.Condition;
import seedu.address.model.tag.Specialisation;
import seedu.address.testutil.HealthWorkerBuilder;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_invalidCommandMode() {
        // missing command mode
        assertParseFailure(parser, NAME_DESC_ANDY + PHONE_DESC_ANDY + ORGANIZATION_DESC_ANDY + NRIC_DESC_ANDY
                + SKILLS_DESC_ANDY, String.format(MESSAGE_INVALID_COMMAND_FORMAT, INVALID_COMMAND_USAGE));

        // invalid command mode
        assertParseFailure(parser, INVALID_MODE + NAME_DESC_ANDY + PHONE_DESC_ANDY + ORGANIZATION_DESC_ANDY
                + NRIC_DESC_ANDY + SKILLS_DESC_ANDY, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                INVALID_COMMAND_USAGE));
    }

    @Test
    public void parse_addHealthWorker_validFields() {
        HealthWorker expectedWorker = new HealthWorkerBuilder(ANDY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODE_HEALTHWORKER
                        + NAME_DESC_ANDY + PHONE_DESC_ANDY + ORGANIZATION_DESC_ANDY + NRIC_DESC_ANDY + SKILLS_DESC_ANDY,
                new AddHealthWorkerCommand(expectedWorker));

        // multiple names - last name accepted
        assertParseSuccess(parser, MODE_HEALTHWORKER + NAME_DESC_BETTY
                        + NAME_DESC_ANDY + PHONE_DESC_ANDY + ORGANIZATION_DESC_ANDY + NRIC_DESC_ANDY + SKILLS_DESC_ANDY,
                new AddHealthWorkerCommand(expectedWorker));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_BETTY + PHONE_DESC_ANDY
                        + ORGANIZATION_DESC_ANDY + NRIC_DESC_ANDY + SKILLS_DESC_ANDY,
                new AddHealthWorkerCommand(expectedWorker));

        // multiple NRIC - last NRIC accepted
        assertParseSuccess(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                        + ORGANIZATION_DESC_ANDY + NRIC_DESC_BETTY + NRIC_DESC_ANDY + SKILLS_DESC_ANDY,
                new AddHealthWorkerCommand(expectedWorker));

        // multiple organizations - last organization accepted
        assertParseSuccess(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                        + ORGANIZATION_DESC_BETTY + ORGANIZATION_DESC_ANDY + NRIC_DESC_ANDY + SKILLS_DESC_ANDY,
                new AddHealthWorkerCommand(expectedWorker));
    }

    @Test
    public void parse_addHealthWorker_missingFields() {
        // Missing prefix tests
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHealthWorkerCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + " " + VALID_NAME_ANDY + PHONE_DESC_ANDY
                + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + VALID_PHONE_ANDY
                + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, expectedMessage);

        // missing organization prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + NRIC_DESC_ANDY + VALID_ORGANIZATION_ANDY + SKILLS_DESC_ANDY, expectedMessage);

        // missing nric prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + VALID_NRIC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, expectedMessage);

        // missing skills prefix
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY, expectedMessage);
    }

    @Test
    public void parse_addHealthWorker_invalidFields() {
        // invalid name
        assertParseFailure(parser, MODE_HEALTHWORKER + INVALID_NAME_DESC + PHONE_DESC_ANDY
                + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + INVALID_PHONE_DESC
                + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, Phone.MESSAGE_CONSTRAINTS);

        // invalid nric
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + INVALID_NRIC_DESC + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY, Nric.MESSAGE_CONSTRAINTS);

        // invalid organization
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + NRIC_DESC_ANDY + INVALID_ORGANIZATION_DESC + SKILLS_DESC_ANDY, Organization.MESSAGE_CONSTRAINTS);

        // invalid skills
        assertParseFailure(parser, MODE_HEALTHWORKER + NAME_DESC_ANDY + PHONE_DESC_ANDY
                + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY + INVALID_SKILLS_DESC, Specialisation.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_addRequest_validFields() {
        // whitespace preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODE_REQUEST + NAME_DESC_ALICE + PHONE_DESC_ALICE
                + ADDRESS_DESC_ALICE + CONDITION_DESC_ALICE + DATE_DESC_ALICE + NRIC_DESC_ALICE,
                new AddRequestCommand(ALICE_REQUEST));

        // multiple names, last accepted
        assertParseSuccess(parser, MODE_REQUEST + NAME_DESC_BENSON + NAME_DESC_ALICE + PHONE_DESC_ALICE
                        + ADDRESS_DESC_ALICE + CONDITION_DESC_ALICE + DATE_DESC_ALICE + NRIC_DESC_ALICE,
                new AddRequestCommand(ALICE_REQUEST));

        // multiple phones, last accepted
        assertParseSuccess(parser, MODE_REQUEST + NAME_DESC_ALICE + PHONE_DESC_BENSON + PHONE_DESC_ALICE
                        + ADDRESS_DESC_ALICE + CONDITION_DESC_ALICE + DATE_DESC_ALICE + NRIC_DESC_ALICE,
                new AddRequestCommand(ALICE_REQUEST));

        // multiple address, last accepted
        assertParseSuccess(parser, MODE_REQUEST + NAME_DESC_ALICE + PHONE_DESC_ALICE + ADDRESS_DESC_BENSON
                        + ADDRESS_DESC_ALICE + CONDITION_DESC_ALICE + DATE_DESC_ALICE + NRIC_DESC_ALICE,
                new AddRequestCommand(ALICE_REQUEST));

        // multiple dates, last accepted
        assertParseSuccess(parser, MODE_REQUEST + NAME_DESC_ALICE + PHONE_DESC_ALICE
                        + ADDRESS_DESC_ALICE + CONDITION_DESC_ALICE + DATE_DESC_BENSON + DATE_DESC_ALICE
                        + NRIC_DESC_ALICE, new AddRequestCommand(ALICE_REQUEST));
    }

    @Test
    public void parse_addRequest_missingFields() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRequestCommand.MESSAGE_USAGE);

        // missing name
        assertParseFailure(parser, MODE_REQUEST + PHONE_DESC_ALICE + ADDRESS_DESC_ALICE + CONDITION_DESC_ALICE
                        + DATE_DESC_ALICE + NRIC_DESC_ALICE, expectedMessage);

        // missing phone
        assertParseFailure(parser, MODE_REQUEST + NAME_DESC_ANDY + ADDRESS_DESC_ALICE + CONDITION_DESC_ALICE
                + DATE_DESC_ALICE + NRIC_DESC_ALICE, expectedMessage);

        // missing address
        assertParseFailure(parser, MODE_REQUEST + NAME_DESC_ANDY + PHONE_DESC_ALICE + CONDITION_DESC_ALICE
                + DATE_DESC_ALICE + NRIC_DESC_ALICE, expectedMessage);

        // missing condition
        assertParseFailure(parser, MODE_REQUEST + NAME_DESC_ANDY + PHONE_DESC_ALICE + ADDRESS_DESC_ALICE
                + DATE_DESC_ALICE + NRIC_DESC_ALICE, expectedMessage);

        // missing date
        assertParseFailure(parser, MODE_REQUEST + NAME_DESC_ANDY + PHONE_DESC_ALICE + ADDRESS_DESC_ALICE
                + CONDITION_DESC_ALICE + NRIC_DESC_ALICE, expectedMessage);

        // missing Nric
        assertParseFailure(parser, MODE_REQUEST + NAME_DESC_ANDY + PHONE_DESC_ALICE + ADDRESS_DESC_ALICE
                + CONDITION_DESC_ALICE + DATE_DESC_ALICE, expectedMessage);
    }

    @Test
    public void parse_addRequest_invalidFields() {
        // invalid name
        assertParseFailure(parser, MODE_REQUEST + INVALID_NAME_DESC + PHONE_DESC_ALICE + ADDRESS_DESC_ALICE
                + CONDITION_DESC_ALICE + DATE_DESC_ALICE + NRIC_DESC_ALICE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, MODE_REQUEST + NAME_DESC_ALICE + INVALID_PHONE_DESC + ADDRESS_DESC_ALICE
                + CONDITION_DESC_ALICE + DATE_DESC_ALICE + NRIC_DESC_ALICE, Phone.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, MODE_REQUEST + NAME_DESC_ALICE + PHONE_DESC_ALICE + INVALID_ADDRESS_DESC
                + CONDITION_DESC_ALICE + DATE_DESC_ALICE + NRIC_DESC_ALICE, Address.MESSAGE_CONSTRAINTS);

        // invalid condition
        assertParseFailure(parser, MODE_REQUEST + NAME_DESC_ALICE + PHONE_DESC_ALICE + ADDRESS_DESC_ALICE
                + INVALID_CONDITION_DESC + DATE_DESC_ALICE + NRIC_DESC_ALICE, Condition.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, MODE_REQUEST + NAME_DESC_ALICE + PHONE_DESC_ALICE + ADDRESS_DESC_ALICE
                + CONDITION_DESC_ALICE + INVALID_DATE_DESC + NRIC_DESC_ALICE, RequestDate.MESSAGE_CONSTRAINTS);

        // invalid nric
        assertParseFailure(parser, MODE_REQUEST + NAME_DESC_ALICE + PHONE_DESC_ALICE + ADDRESS_DESC_ALICE
                + CONDITION_DESC_ALICE + DATE_DESC_ALICE + INVALID_NRIC_DESC, Nric.MESSAGE_CONSTRAINTS);
    }
}
